import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITransactionHistoriesMySuffix, getTransactionHistoriesMySuffixIdentifier } from '../transaction-histories-my-suffix.model';

export type EntityResponseType = HttpResponse<ITransactionHistoriesMySuffix>;
export type EntityArrayResponseType = HttpResponse<ITransactionHistoriesMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class TransactionHistoriesMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/transaction-histories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(transactionHistories: ITransactionHistoriesMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionHistories);
    return this.http
      .post<ITransactionHistoriesMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transactionHistories: ITransactionHistoriesMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionHistories);
    return this.http
      .put<ITransactionHistoriesMySuffix>(
        `${this.resourceUrl}/${getTransactionHistoriesMySuffixIdentifier(transactionHistories) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(transactionHistories: ITransactionHistoriesMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionHistories);
    return this.http
      .patch<ITransactionHistoriesMySuffix>(
        `${this.resourceUrl}/${getTransactionHistoriesMySuffixIdentifier(transactionHistories) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransactionHistoriesMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactionHistoriesMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTransactionHistoriesMySuffixToCollectionIfMissing(
    transactionHistoriesCollection: ITransactionHistoriesMySuffix[],
    ...transactionHistoriesToCheck: (ITransactionHistoriesMySuffix | null | undefined)[]
  ): ITransactionHistoriesMySuffix[] {
    const transactionHistories: ITransactionHistoriesMySuffix[] = transactionHistoriesToCheck.filter(isPresent);
    if (transactionHistories.length > 0) {
      const transactionHistoriesCollectionIdentifiers = transactionHistoriesCollection.map(
        transactionHistoriesItem => getTransactionHistoriesMySuffixIdentifier(transactionHistoriesItem)!
      );
      const transactionHistoriesToAdd = transactionHistories.filter(transactionHistoriesItem => {
        const transactionHistoriesIdentifier = getTransactionHistoriesMySuffixIdentifier(transactionHistoriesItem);
        if (transactionHistoriesIdentifier == null || transactionHistoriesCollectionIdentifiers.includes(transactionHistoriesIdentifier)) {
          return false;
        }
        transactionHistoriesCollectionIdentifiers.push(transactionHistoriesIdentifier);
        return true;
      });
      return [...transactionHistoriesToAdd, ...transactionHistoriesCollection];
    }
    return transactionHistoriesCollection;
  }

  protected convertDateFromClient(transactionHistories: ITransactionHistoriesMySuffix): ITransactionHistoriesMySuffix {
    return Object.assign({}, transactionHistories, {
      createdDate: transactionHistories.createdDate?.isValid() ? transactionHistories.createdDate.toJSON() : undefined,
      lastModifiedDate: transactionHistories.lastModifiedDate?.isValid() ? transactionHistories.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((transactionHistories: ITransactionHistoriesMySuffix) => {
        transactionHistories.createdDate = transactionHistories.createdDate ? dayjs(transactionHistories.createdDate) : undefined;
        transactionHistories.lastModifiedDate = transactionHistories.lastModifiedDate
          ? dayjs(transactionHistories.lastModifiedDate)
          : undefined;
      });
    }
    return res;
  }
}
