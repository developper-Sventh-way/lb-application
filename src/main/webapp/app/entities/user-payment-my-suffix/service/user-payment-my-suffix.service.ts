import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserPaymentMySuffix, getUserPaymentMySuffixIdentifier } from '../user-payment-my-suffix.model';

export type EntityResponseType = HttpResponse<IUserPaymentMySuffix>;
export type EntityArrayResponseType = HttpResponse<IUserPaymentMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class UserPaymentMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-payments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userPayment: IUserPaymentMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userPayment);
    return this.http
      .post<IUserPaymentMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userPayment: IUserPaymentMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userPayment);
    return this.http
      .put<IUserPaymentMySuffix>(`${this.resourceUrl}/${getUserPaymentMySuffixIdentifier(userPayment) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(userPayment: IUserPaymentMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userPayment);
    return this.http
      .patch<IUserPaymentMySuffix>(`${this.resourceUrl}/${getUserPaymentMySuffixIdentifier(userPayment) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserPaymentMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserPaymentMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUserPaymentMySuffixToCollectionIfMissing(
    userPaymentCollection: IUserPaymentMySuffix[],
    ...userPaymentsToCheck: (IUserPaymentMySuffix | null | undefined)[]
  ): IUserPaymentMySuffix[] {
    const userPayments: IUserPaymentMySuffix[] = userPaymentsToCheck.filter(isPresent);
    if (userPayments.length > 0) {
      const userPaymentCollectionIdentifiers = userPaymentCollection.map(
        userPaymentItem => getUserPaymentMySuffixIdentifier(userPaymentItem)!
      );
      const userPaymentsToAdd = userPayments.filter(userPaymentItem => {
        const userPaymentIdentifier = getUserPaymentMySuffixIdentifier(userPaymentItem);
        if (userPaymentIdentifier == null || userPaymentCollectionIdentifiers.includes(userPaymentIdentifier)) {
          return false;
        }
        userPaymentCollectionIdentifiers.push(userPaymentIdentifier);
        return true;
      });
      return [...userPaymentsToAdd, ...userPaymentCollection];
    }
    return userPaymentCollection;
  }

  protected convertDateFromClient(userPayment: IUserPaymentMySuffix): IUserPaymentMySuffix {
    return Object.assign({}, userPayment, {
      startDate: userPayment.startDate?.isValid() ? userPayment.startDate.format(DATE_FORMAT) : undefined,
      endDate: userPayment.endDate?.isValid() ? userPayment.endDate.format(DATE_FORMAT) : undefined,
      createdDate: userPayment.createdDate?.isValid() ? userPayment.createdDate.toJSON() : undefined,
      lastModifiedDate: userPayment.lastModifiedDate?.isValid() ? userPayment.lastModifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? dayjs(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? dayjs(res.body.endDate) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? dayjs(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userPayment: IUserPaymentMySuffix) => {
        userPayment.startDate = userPayment.startDate ? dayjs(userPayment.startDate) : undefined;
        userPayment.endDate = userPayment.endDate ? dayjs(userPayment.endDate) : undefined;
        userPayment.createdDate = userPayment.createdDate ? dayjs(userPayment.createdDate) : undefined;
        userPayment.lastModifiedDate = userPayment.lastModifiedDate ? dayjs(userPayment.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
