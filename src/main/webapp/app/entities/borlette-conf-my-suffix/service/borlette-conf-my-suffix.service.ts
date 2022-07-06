import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBorletteConfMySuffix, getBorletteConfMySuffixIdentifier } from '../borlette-conf-my-suffix.model';

export type EntityResponseType = HttpResponse<IBorletteConfMySuffix>;
export type EntityArrayResponseType = HttpResponse<IBorletteConfMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class BorletteConfMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/borlette-confs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(borletteConf: IBorletteConfMySuffix): Observable<EntityResponseType> {
    return this.http.post<IBorletteConfMySuffix>(this.resourceUrl, borletteConf, { observe: 'response' });
  }

  update(borletteConf: IBorletteConfMySuffix): Observable<EntityResponseType> {
    return this.http.put<IBorletteConfMySuffix>(
      `${this.resourceUrl}/${getBorletteConfMySuffixIdentifier(borletteConf) as number}`,
      borletteConf,
      { observe: 'response' }
    );
  }

  partialUpdate(borletteConf: IBorletteConfMySuffix): Observable<EntityResponseType> {
    return this.http.patch<IBorletteConfMySuffix>(
      `${this.resourceUrl}/${getBorletteConfMySuffixIdentifier(borletteConf) as number}`,
      borletteConf,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBorletteConfMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBorletteConfMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBorletteConfMySuffixToCollectionIfMissing(
    borletteConfCollection: IBorletteConfMySuffix[],
    ...borletteConfsToCheck: (IBorletteConfMySuffix | null | undefined)[]
  ): IBorletteConfMySuffix[] {
    const borletteConfs: IBorletteConfMySuffix[] = borletteConfsToCheck.filter(isPresent);
    if (borletteConfs.length > 0) {
      const borletteConfCollectionIdentifiers = borletteConfCollection.map(
        borletteConfItem => getBorletteConfMySuffixIdentifier(borletteConfItem)!
      );
      const borletteConfsToAdd = borletteConfs.filter(borletteConfItem => {
        const borletteConfIdentifier = getBorletteConfMySuffixIdentifier(borletteConfItem);
        if (borletteConfIdentifier == null || borletteConfCollectionIdentifiers.includes(borletteConfIdentifier)) {
          return false;
        }
        borletteConfCollectionIdentifiers.push(borletteConfIdentifier);
        return true;
      });
      return [...borletteConfsToAdd, ...borletteConfCollection];
    }
    return borletteConfCollection;
  }
}
