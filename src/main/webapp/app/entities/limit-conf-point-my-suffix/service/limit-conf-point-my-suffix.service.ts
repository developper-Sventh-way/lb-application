import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILimitConfPointMySuffix, getLimitConfPointMySuffixIdentifier } from '../limit-conf-point-my-suffix.model';

export type EntityResponseType = HttpResponse<ILimitConfPointMySuffix>;
export type EntityArrayResponseType = HttpResponse<ILimitConfPointMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class LimitConfPointMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/limit-conf-points');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(limitConfPoint: ILimitConfPointMySuffix): Observable<EntityResponseType> {
    return this.http.post<ILimitConfPointMySuffix>(this.resourceUrl, limitConfPoint, { observe: 'response' });
  }

  update(limitConfPoint: ILimitConfPointMySuffix): Observable<EntityResponseType> {
    return this.http.put<ILimitConfPointMySuffix>(
      `${this.resourceUrl}/${getLimitConfPointMySuffixIdentifier(limitConfPoint) as number}`,
      limitConfPoint,
      { observe: 'response' }
    );
  }

  partialUpdate(limitConfPoint: ILimitConfPointMySuffix): Observable<EntityResponseType> {
    return this.http.patch<ILimitConfPointMySuffix>(
      `${this.resourceUrl}/${getLimitConfPointMySuffixIdentifier(limitConfPoint) as number}`,
      limitConfPoint,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILimitConfPointMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILimitConfPointMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLimitConfPointMySuffixToCollectionIfMissing(
    limitConfPointCollection: ILimitConfPointMySuffix[],
    ...limitConfPointsToCheck: (ILimitConfPointMySuffix | null | undefined)[]
  ): ILimitConfPointMySuffix[] {
    const limitConfPoints: ILimitConfPointMySuffix[] = limitConfPointsToCheck.filter(isPresent);
    if (limitConfPoints.length > 0) {
      const limitConfPointCollectionIdentifiers = limitConfPointCollection.map(
        limitConfPointItem => getLimitConfPointMySuffixIdentifier(limitConfPointItem)!
      );
      const limitConfPointsToAdd = limitConfPoints.filter(limitConfPointItem => {
        const limitConfPointIdentifier = getLimitConfPointMySuffixIdentifier(limitConfPointItem);
        if (limitConfPointIdentifier == null || limitConfPointCollectionIdentifiers.includes(limitConfPointIdentifier)) {
          return false;
        }
        limitConfPointCollectionIdentifiers.push(limitConfPointIdentifier);
        return true;
      });
      return [...limitConfPointsToAdd, ...limitConfPointCollection];
    }
    return limitConfPointCollection;
  }
}
