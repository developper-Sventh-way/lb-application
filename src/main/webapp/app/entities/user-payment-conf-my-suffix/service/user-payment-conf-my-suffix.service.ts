import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserPaymentConfMySuffix, getUserPaymentConfMySuffixIdentifier } from '../user-payment-conf-my-suffix.model';

export type EntityResponseType = HttpResponse<IUserPaymentConfMySuffix>;
export type EntityArrayResponseType = HttpResponse<IUserPaymentConfMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class UserPaymentConfMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-payment-confs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userPaymentConf: IUserPaymentConfMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userPaymentConf);
    return this.http
      .post<IUserPaymentConfMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userPaymentConf: IUserPaymentConfMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userPaymentConf);
    return this.http
      .put<IUserPaymentConfMySuffix>(`${this.resourceUrl}/${getUserPaymentConfMySuffixIdentifier(userPaymentConf) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(userPaymentConf: IUserPaymentConfMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userPaymentConf);
    return this.http
      .patch<IUserPaymentConfMySuffix>(`${this.resourceUrl}/${getUserPaymentConfMySuffixIdentifier(userPaymentConf) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserPaymentConfMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserPaymentConfMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUserPaymentConfMySuffixToCollectionIfMissing(
    userPaymentConfCollection: IUserPaymentConfMySuffix[],
    ...userPaymentConfsToCheck: (IUserPaymentConfMySuffix | null | undefined)[]
  ): IUserPaymentConfMySuffix[] {
    const userPaymentConfs: IUserPaymentConfMySuffix[] = userPaymentConfsToCheck.filter(isPresent);
    if (userPaymentConfs.length > 0) {
      const userPaymentConfCollectionIdentifiers = userPaymentConfCollection.map(
        userPaymentConfItem => getUserPaymentConfMySuffixIdentifier(userPaymentConfItem)!
      );
      const userPaymentConfsToAdd = userPaymentConfs.filter(userPaymentConfItem => {
        const userPaymentConfIdentifier = getUserPaymentConfMySuffixIdentifier(userPaymentConfItem);
        if (userPaymentConfIdentifier == null || userPaymentConfCollectionIdentifiers.includes(userPaymentConfIdentifier)) {
          return false;
        }
        userPaymentConfCollectionIdentifiers.push(userPaymentConfIdentifier);
        return true;
      });
      return [...userPaymentConfsToAdd, ...userPaymentConfCollection];
    }
    return userPaymentConfCollection;
  }

  protected convertDateFromClient(userPaymentConf: IUserPaymentConfMySuffix): IUserPaymentConfMySuffix {
    return Object.assign({}, userPaymentConf, {
      createdDate: userPaymentConf.createdDate?.isValid() ? userPaymentConf.createdDate.toJSON() : undefined,
      lastModifiedDate: userPaymentConf.lastModifiedDate?.isValid() ? userPaymentConf.lastModifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? dayjs(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userPaymentConf: IUserPaymentConfMySuffix) => {
        userPaymentConf.createdDate = userPaymentConf.createdDate ? dayjs(userPaymentConf.createdDate) : undefined;
        userPaymentConf.lastModifiedDate = userPaymentConf.lastModifiedDate ? dayjs(userPaymentConf.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
