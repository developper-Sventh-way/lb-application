import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILimitConfManagerMySuffix, getLimitConfManagerMySuffixIdentifier } from '../limit-conf-manager-my-suffix.model';

export type EntityResponseType = HttpResponse<ILimitConfManagerMySuffix>;
export type EntityArrayResponseType = HttpResponse<ILimitConfManagerMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class LimitConfManagerMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/limit-conf-managers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(limitConfManager: ILimitConfManagerMySuffix): Observable<EntityResponseType> {
    return this.http.post<ILimitConfManagerMySuffix>(this.resourceUrl, limitConfManager, { observe: 'response' });
  }

  update(limitConfManager: ILimitConfManagerMySuffix): Observable<EntityResponseType> {
    return this.http.put<ILimitConfManagerMySuffix>(
      `${this.resourceUrl}/${getLimitConfManagerMySuffixIdentifier(limitConfManager) as number}`,
      limitConfManager,
      { observe: 'response' }
    );
  }

  partialUpdate(limitConfManager: ILimitConfManagerMySuffix): Observable<EntityResponseType> {
    return this.http.patch<ILimitConfManagerMySuffix>(
      `${this.resourceUrl}/${getLimitConfManagerMySuffixIdentifier(limitConfManager) as number}`,
      limitConfManager,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILimitConfManagerMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILimitConfManagerMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLimitConfManagerMySuffixToCollectionIfMissing(
    limitConfManagerCollection: ILimitConfManagerMySuffix[],
    ...limitConfManagersToCheck: (ILimitConfManagerMySuffix | null | undefined)[]
  ): ILimitConfManagerMySuffix[] {
    const limitConfManagers: ILimitConfManagerMySuffix[] = limitConfManagersToCheck.filter(isPresent);
    if (limitConfManagers.length > 0) {
      const limitConfManagerCollectionIdentifiers = limitConfManagerCollection.map(
        limitConfManagerItem => getLimitConfManagerMySuffixIdentifier(limitConfManagerItem)!
      );
      const limitConfManagersToAdd = limitConfManagers.filter(limitConfManagerItem => {
        const limitConfManagerIdentifier = getLimitConfManagerMySuffixIdentifier(limitConfManagerItem);
        if (limitConfManagerIdentifier == null || limitConfManagerCollectionIdentifiers.includes(limitConfManagerIdentifier)) {
          return false;
        }
        limitConfManagerCollectionIdentifiers.push(limitConfManagerIdentifier);
        return true;
      });
      return [...limitConfManagersToAdd, ...limitConfManagerCollection];
    }
    return limitConfManagerCollection;
  }
}
