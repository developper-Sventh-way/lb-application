import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITirageMySuffix, getTirageMySuffixIdentifier } from '../tirage-my-suffix.model';

export type EntityResponseType = HttpResponse<ITirageMySuffix>;
export type EntityArrayResponseType = HttpResponse<ITirageMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class TirageMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tirages');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tirage: ITirageMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tirage);
    return this.http
      .post<ITirageMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tirage: ITirageMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tirage);
    return this.http
      .put<ITirageMySuffix>(`${this.resourceUrl}/${getTirageMySuffixIdentifier(tirage) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tirage: ITirageMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tirage);
    return this.http
      .patch<ITirageMySuffix>(`${this.resourceUrl}/${getTirageMySuffixIdentifier(tirage) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITirageMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITirageMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTirageMySuffixToCollectionIfMissing(
    tirageCollection: ITirageMySuffix[],
    ...tiragesToCheck: (ITirageMySuffix | null | undefined)[]
  ): ITirageMySuffix[] {
    const tirages: ITirageMySuffix[] = tiragesToCheck.filter(isPresent);
    if (tirages.length > 0) {
      const tirageCollectionIdentifiers = tirageCollection.map(tirageItem => getTirageMySuffixIdentifier(tirageItem)!);
      const tiragesToAdd = tirages.filter(tirageItem => {
        const tirageIdentifier = getTirageMySuffixIdentifier(tirageItem);
        if (tirageIdentifier == null || tirageCollectionIdentifiers.includes(tirageIdentifier)) {
          return false;
        }
        tirageCollectionIdentifiers.push(tirageIdentifier);
        return true;
      });
      return [...tiragesToAdd, ...tirageCollection];
    }
    return tirageCollection;
  }

  protected convertDateFromClient(tirage: ITirageMySuffix): ITirageMySuffix {
    return Object.assign({}, tirage, {
      createdDate: tirage.createdDate?.isValid() ? tirage.createdDate.toJSON() : undefined,
      lastModifiedDate: tirage.lastModifiedDate?.isValid() ? tirage.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((tirage: ITirageMySuffix) => {
        tirage.createdDate = tirage.createdDate ? dayjs(tirage.createdDate) : undefined;
        tirage.lastModifiedDate = tirage.lastModifiedDate ? dayjs(tirage.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
