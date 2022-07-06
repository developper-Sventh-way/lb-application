import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserSaleAccountMySuffix, getUserSaleAccountMySuffixIdentifier } from '../user-sale-account-my-suffix.model';

export type EntityResponseType = HttpResponse<IUserSaleAccountMySuffix>;
export type EntityArrayResponseType = HttpResponse<IUserSaleAccountMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class UserSaleAccountMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-sale-accounts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userSaleAccount: IUserSaleAccountMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userSaleAccount);
    return this.http
      .post<IUserSaleAccountMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userSaleAccount: IUserSaleAccountMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userSaleAccount);
    return this.http
      .put<IUserSaleAccountMySuffix>(`${this.resourceUrl}/${getUserSaleAccountMySuffixIdentifier(userSaleAccount) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(userSaleAccount: IUserSaleAccountMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userSaleAccount);
    return this.http
      .patch<IUserSaleAccountMySuffix>(`${this.resourceUrl}/${getUserSaleAccountMySuffixIdentifier(userSaleAccount) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserSaleAccountMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserSaleAccountMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUserSaleAccountMySuffixToCollectionIfMissing(
    userSaleAccountCollection: IUserSaleAccountMySuffix[],
    ...userSaleAccountsToCheck: (IUserSaleAccountMySuffix | null | undefined)[]
  ): IUserSaleAccountMySuffix[] {
    const userSaleAccounts: IUserSaleAccountMySuffix[] = userSaleAccountsToCheck.filter(isPresent);
    if (userSaleAccounts.length > 0) {
      const userSaleAccountCollectionIdentifiers = userSaleAccountCollection.map(
        userSaleAccountItem => getUserSaleAccountMySuffixIdentifier(userSaleAccountItem)!
      );
      const userSaleAccountsToAdd = userSaleAccounts.filter(userSaleAccountItem => {
        const userSaleAccountIdentifier = getUserSaleAccountMySuffixIdentifier(userSaleAccountItem);
        if (userSaleAccountIdentifier == null || userSaleAccountCollectionIdentifiers.includes(userSaleAccountIdentifier)) {
          return false;
        }
        userSaleAccountCollectionIdentifiers.push(userSaleAccountIdentifier);
        return true;
      });
      return [...userSaleAccountsToAdd, ...userSaleAccountCollection];
    }
    return userSaleAccountCollection;
  }

  protected convertDateFromClient(userSaleAccount: IUserSaleAccountMySuffix): IUserSaleAccountMySuffix {
    return Object.assign({}, userSaleAccount, {
      startDate: userSaleAccount.startDate?.isValid() ? userSaleAccount.startDate.toJSON() : undefined,
      endDate: userSaleAccount.endDate?.isValid() ? userSaleAccount.endDate.toJSON() : undefined,
      createdDate: userSaleAccount.createdDate?.isValid() ? userSaleAccount.createdDate.toJSON() : undefined,
      lastModifiedDate: userSaleAccount.lastModifiedDate?.isValid() ? userSaleAccount.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((userSaleAccount: IUserSaleAccountMySuffix) => {
        userSaleAccount.startDate = userSaleAccount.startDate ? dayjs(userSaleAccount.startDate) : undefined;
        userSaleAccount.endDate = userSaleAccount.endDate ? dayjs(userSaleAccount.endDate) : undefined;
        userSaleAccount.createdDate = userSaleAccount.createdDate ? dayjs(userSaleAccount.createdDate) : undefined;
        userSaleAccount.lastModifiedDate = userSaleAccount.lastModifiedDate ? dayjs(userSaleAccount.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
