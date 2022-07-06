import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPOSConfigurationMySuffix, getPOSConfigurationMySuffixIdentifier } from '../pos-configuration-my-suffix.model';

export type EntityResponseType = HttpResponse<IPOSConfigurationMySuffix>;
export type EntityArrayResponseType = HttpResponse<IPOSConfigurationMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class POSConfigurationMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pos-configurations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pOSConfiguration: IPOSConfigurationMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pOSConfiguration);
    return this.http
      .post<IPOSConfigurationMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pOSConfiguration: IPOSConfigurationMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pOSConfiguration);
    return this.http
      .put<IPOSConfigurationMySuffix>(`${this.resourceUrl}/${getPOSConfigurationMySuffixIdentifier(pOSConfiguration) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(pOSConfiguration: IPOSConfigurationMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pOSConfiguration);
    return this.http
      .patch<IPOSConfigurationMySuffix>(`${this.resourceUrl}/${getPOSConfigurationMySuffixIdentifier(pOSConfiguration) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPOSConfigurationMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPOSConfigurationMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPOSConfigurationMySuffixToCollectionIfMissing(
    pOSConfigurationCollection: IPOSConfigurationMySuffix[],
    ...pOSConfigurationsToCheck: (IPOSConfigurationMySuffix | null | undefined)[]
  ): IPOSConfigurationMySuffix[] {
    const pOSConfigurations: IPOSConfigurationMySuffix[] = pOSConfigurationsToCheck.filter(isPresent);
    if (pOSConfigurations.length > 0) {
      const pOSConfigurationCollectionIdentifiers = pOSConfigurationCollection.map(
        pOSConfigurationItem => getPOSConfigurationMySuffixIdentifier(pOSConfigurationItem)!
      );
      const pOSConfigurationsToAdd = pOSConfigurations.filter(pOSConfigurationItem => {
        const pOSConfigurationIdentifier = getPOSConfigurationMySuffixIdentifier(pOSConfigurationItem);
        if (pOSConfigurationIdentifier == null || pOSConfigurationCollectionIdentifiers.includes(pOSConfigurationIdentifier)) {
          return false;
        }
        pOSConfigurationCollectionIdentifiers.push(pOSConfigurationIdentifier);
        return true;
      });
      return [...pOSConfigurationsToAdd, ...pOSConfigurationCollection];
    }
    return pOSConfigurationCollection;
  }

  protected convertDateFromClient(pOSConfiguration: IPOSConfigurationMySuffix): IPOSConfigurationMySuffix {
    return Object.assign({}, pOSConfiguration, {
      createdDate: pOSConfiguration.createdDate?.isValid() ? pOSConfiguration.createdDate.toJSON() : undefined,
      lastModifiedDate: pOSConfiguration.lastModifiedDate?.isValid() ? pOSConfiguration.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((pOSConfiguration: IPOSConfigurationMySuffix) => {
        pOSConfiguration.createdDate = pOSConfiguration.createdDate ? dayjs(pOSConfiguration.createdDate) : undefined;
        pOSConfiguration.lastModifiedDate = pOSConfiguration.lastModifiedDate ? dayjs(pOSConfiguration.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
