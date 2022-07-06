import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILimitConfBorletteMySuffix, getLimitConfBorletteMySuffixIdentifier } from '../limit-conf-borlette-my-suffix.model';

export type EntityResponseType = HttpResponse<ILimitConfBorletteMySuffix>;
export type EntityArrayResponseType = HttpResponse<ILimitConfBorletteMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class LimitConfBorletteMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/limit-conf-borlettes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(limitConfBorlette: ILimitConfBorletteMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(limitConfBorlette);
    return this.http
      .post<ILimitConfBorletteMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(limitConfBorlette: ILimitConfBorletteMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(limitConfBorlette);
    return this.http
      .put<ILimitConfBorletteMySuffix>(`${this.resourceUrl}/${getLimitConfBorletteMySuffixIdentifier(limitConfBorlette) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(limitConfBorlette: ILimitConfBorletteMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(limitConfBorlette);
    return this.http
      .patch<ILimitConfBorletteMySuffix>(
        `${this.resourceUrl}/${getLimitConfBorletteMySuffixIdentifier(limitConfBorlette) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILimitConfBorletteMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILimitConfBorletteMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLimitConfBorletteMySuffixToCollectionIfMissing(
    limitConfBorletteCollection: ILimitConfBorletteMySuffix[],
    ...limitConfBorlettesToCheck: (ILimitConfBorletteMySuffix | null | undefined)[]
  ): ILimitConfBorletteMySuffix[] {
    const limitConfBorlettes: ILimitConfBorletteMySuffix[] = limitConfBorlettesToCheck.filter(isPresent);
    if (limitConfBorlettes.length > 0) {
      const limitConfBorletteCollectionIdentifiers = limitConfBorletteCollection.map(
        limitConfBorletteItem => getLimitConfBorletteMySuffixIdentifier(limitConfBorletteItem)!
      );
      const limitConfBorlettesToAdd = limitConfBorlettes.filter(limitConfBorletteItem => {
        const limitConfBorletteIdentifier = getLimitConfBorletteMySuffixIdentifier(limitConfBorletteItem);
        if (limitConfBorletteIdentifier == null || limitConfBorletteCollectionIdentifiers.includes(limitConfBorletteIdentifier)) {
          return false;
        }
        limitConfBorletteCollectionIdentifiers.push(limitConfBorletteIdentifier);
        return true;
      });
      return [...limitConfBorlettesToAdd, ...limitConfBorletteCollection];
    }
    return limitConfBorletteCollection;
  }

  protected convertDateFromClient(limitConfBorlette: ILimitConfBorletteMySuffix): ILimitConfBorletteMySuffix {
    return Object.assign({}, limitConfBorlette, {
      createdDate: limitConfBorlette.createdDate?.isValid() ? limitConfBorlette.createdDate.toJSON() : undefined,
      lastModifiedDate: limitConfBorlette.lastModifiedDate?.isValid() ? limitConfBorlette.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((limitConfBorlette: ILimitConfBorletteMySuffix) => {
        limitConfBorlette.createdDate = limitConfBorlette.createdDate ? dayjs(limitConfBorlette.createdDate) : undefined;
        limitConfBorlette.lastModifiedDate = limitConfBorlette.lastModifiedDate ? dayjs(limitConfBorlette.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
