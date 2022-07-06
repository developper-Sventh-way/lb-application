import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICreditSoldeMySuffix, getCreditSoldeMySuffixIdentifier } from '../credit-solde-my-suffix.model';

export type EntityResponseType = HttpResponse<ICreditSoldeMySuffix>;
export type EntityArrayResponseType = HttpResponse<ICreditSoldeMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class CreditSoldeMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/credit-soldes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(creditSolde: ICreditSoldeMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(creditSolde);
    return this.http
      .post<ICreditSoldeMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(creditSolde: ICreditSoldeMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(creditSolde);
    return this.http
      .put<ICreditSoldeMySuffix>(`${this.resourceUrl}/${getCreditSoldeMySuffixIdentifier(creditSolde) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(creditSolde: ICreditSoldeMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(creditSolde);
    return this.http
      .patch<ICreditSoldeMySuffix>(`${this.resourceUrl}/${getCreditSoldeMySuffixIdentifier(creditSolde) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICreditSoldeMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICreditSoldeMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCreditSoldeMySuffixToCollectionIfMissing(
    creditSoldeCollection: ICreditSoldeMySuffix[],
    ...creditSoldesToCheck: (ICreditSoldeMySuffix | null | undefined)[]
  ): ICreditSoldeMySuffix[] {
    const creditSoldes: ICreditSoldeMySuffix[] = creditSoldesToCheck.filter(isPresent);
    if (creditSoldes.length > 0) {
      const creditSoldeCollectionIdentifiers = creditSoldeCollection.map(
        creditSoldeItem => getCreditSoldeMySuffixIdentifier(creditSoldeItem)!
      );
      const creditSoldesToAdd = creditSoldes.filter(creditSoldeItem => {
        const creditSoldeIdentifier = getCreditSoldeMySuffixIdentifier(creditSoldeItem);
        if (creditSoldeIdentifier == null || creditSoldeCollectionIdentifiers.includes(creditSoldeIdentifier)) {
          return false;
        }
        creditSoldeCollectionIdentifiers.push(creditSoldeIdentifier);
        return true;
      });
      return [...creditSoldesToAdd, ...creditSoldeCollection];
    }
    return creditSoldeCollection;
  }

  protected convertDateFromClient(creditSolde: ICreditSoldeMySuffix): ICreditSoldeMySuffix {
    return Object.assign({}, creditSolde, {
      createdDate: creditSolde.createdDate?.isValid() ? creditSolde.createdDate.toJSON() : undefined,
      lastModifiedDate: creditSolde.lastModifiedDate?.isValid() ? creditSolde.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((creditSolde: ICreditSoldeMySuffix) => {
        creditSolde.createdDate = creditSolde.createdDate ? dayjs(creditSolde.createdDate) : undefined;
        creditSolde.lastModifiedDate = creditSolde.lastModifiedDate ? dayjs(creditSolde.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
