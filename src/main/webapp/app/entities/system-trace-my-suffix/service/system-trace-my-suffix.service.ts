import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISystemTraceMySuffix, getSystemTraceMySuffixIdentifier } from '../system-trace-my-suffix.model';

export type EntityResponseType = HttpResponse<ISystemTraceMySuffix>;
export type EntityArrayResponseType = HttpResponse<ISystemTraceMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class SystemTraceMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/system-traces');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(systemTrace: ISystemTraceMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(systemTrace);
    return this.http
      .post<ISystemTraceMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(systemTrace: ISystemTraceMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(systemTrace);
    return this.http
      .put<ISystemTraceMySuffix>(`${this.resourceUrl}/${getSystemTraceMySuffixIdentifier(systemTrace) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(systemTrace: ISystemTraceMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(systemTrace);
    return this.http
      .patch<ISystemTraceMySuffix>(`${this.resourceUrl}/${getSystemTraceMySuffixIdentifier(systemTrace) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISystemTraceMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISystemTraceMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSystemTraceMySuffixToCollectionIfMissing(
    systemTraceCollection: ISystemTraceMySuffix[],
    ...systemTracesToCheck: (ISystemTraceMySuffix | null | undefined)[]
  ): ISystemTraceMySuffix[] {
    const systemTraces: ISystemTraceMySuffix[] = systemTracesToCheck.filter(isPresent);
    if (systemTraces.length > 0) {
      const systemTraceCollectionIdentifiers = systemTraceCollection.map(
        systemTraceItem => getSystemTraceMySuffixIdentifier(systemTraceItem)!
      );
      const systemTracesToAdd = systemTraces.filter(systemTraceItem => {
        const systemTraceIdentifier = getSystemTraceMySuffixIdentifier(systemTraceItem);
        if (systemTraceIdentifier == null || systemTraceCollectionIdentifiers.includes(systemTraceIdentifier)) {
          return false;
        }
        systemTraceCollectionIdentifiers.push(systemTraceIdentifier);
        return true;
      });
      return [...systemTracesToAdd, ...systemTraceCollection];
    }
    return systemTraceCollection;
  }

  protected convertDateFromClient(systemTrace: ISystemTraceMySuffix): ISystemTraceMySuffix {
    return Object.assign({}, systemTrace, {
      createdDate: systemTrace.createdDate?.isValid() ? systemTrace.createdDate.toJSON() : undefined,
      lastModifiedDate: systemTrace.lastModifiedDate?.isValid() ? systemTrace.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((systemTrace: ISystemTraceMySuffix) => {
        systemTrace.createdDate = systemTrace.createdDate ? dayjs(systemTrace.createdDate) : undefined;
        systemTrace.lastModifiedDate = systemTrace.lastModifiedDate ? dayjs(systemTrace.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
