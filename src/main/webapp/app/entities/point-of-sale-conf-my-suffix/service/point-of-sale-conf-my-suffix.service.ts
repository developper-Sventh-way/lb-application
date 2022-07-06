import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPointOfSaleConfMySuffix, getPointOfSaleConfMySuffixIdentifier } from '../point-of-sale-conf-my-suffix.model';

export type EntityResponseType = HttpResponse<IPointOfSaleConfMySuffix>;
export type EntityArrayResponseType = HttpResponse<IPointOfSaleConfMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class PointOfSaleConfMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/point-of-sale-confs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pointOfSaleConf: IPointOfSaleConfMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointOfSaleConf);
    return this.http
      .post<IPointOfSaleConfMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pointOfSaleConf: IPointOfSaleConfMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointOfSaleConf);
    return this.http
      .put<IPointOfSaleConfMySuffix>(`${this.resourceUrl}/${getPointOfSaleConfMySuffixIdentifier(pointOfSaleConf) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(pointOfSaleConf: IPointOfSaleConfMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointOfSaleConf);
    return this.http
      .patch<IPointOfSaleConfMySuffix>(`${this.resourceUrl}/${getPointOfSaleConfMySuffixIdentifier(pointOfSaleConf) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPointOfSaleConfMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPointOfSaleConfMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPointOfSaleConfMySuffixToCollectionIfMissing(
    pointOfSaleConfCollection: IPointOfSaleConfMySuffix[],
    ...pointOfSaleConfsToCheck: (IPointOfSaleConfMySuffix | null | undefined)[]
  ): IPointOfSaleConfMySuffix[] {
    const pointOfSaleConfs: IPointOfSaleConfMySuffix[] = pointOfSaleConfsToCheck.filter(isPresent);
    if (pointOfSaleConfs.length > 0) {
      const pointOfSaleConfCollectionIdentifiers = pointOfSaleConfCollection.map(
        pointOfSaleConfItem => getPointOfSaleConfMySuffixIdentifier(pointOfSaleConfItem)!
      );
      const pointOfSaleConfsToAdd = pointOfSaleConfs.filter(pointOfSaleConfItem => {
        const pointOfSaleConfIdentifier = getPointOfSaleConfMySuffixIdentifier(pointOfSaleConfItem);
        if (pointOfSaleConfIdentifier == null || pointOfSaleConfCollectionIdentifiers.includes(pointOfSaleConfIdentifier)) {
          return false;
        }
        pointOfSaleConfCollectionIdentifiers.push(pointOfSaleConfIdentifier);
        return true;
      });
      return [...pointOfSaleConfsToAdd, ...pointOfSaleConfCollection];
    }
    return pointOfSaleConfCollection;
  }

  protected convertDateFromClient(pointOfSaleConf: IPointOfSaleConfMySuffix): IPointOfSaleConfMySuffix {
    return Object.assign({}, pointOfSaleConf, {
      createdDate: pointOfSaleConf.createdDate?.isValid() ? pointOfSaleConf.createdDate.toJSON() : undefined,
      lastModifiedDate: pointOfSaleConf.lastModifiedDate?.isValid() ? pointOfSaleConf.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((pointOfSaleConf: IPointOfSaleConfMySuffix) => {
        pointOfSaleConf.createdDate = pointOfSaleConf.createdDate ? dayjs(pointOfSaleConf.createdDate) : undefined;
        pointOfSaleConf.lastModifiedDate = pointOfSaleConf.lastModifiedDate ? dayjs(pointOfSaleConf.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
