import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPointOfSaleMySuffix, getPointOfSaleMySuffixIdentifier } from '../point-of-sale-my-suffix.model';

export type EntityResponseType = HttpResponse<IPointOfSaleMySuffix>;
export type EntityArrayResponseType = HttpResponse<IPointOfSaleMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class PointOfSaleMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/point-of-sales');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pointOfSale: IPointOfSaleMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointOfSale);
    return this.http
      .post<IPointOfSaleMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pointOfSale: IPointOfSaleMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointOfSale);
    return this.http
      .put<IPointOfSaleMySuffix>(`${this.resourceUrl}/${getPointOfSaleMySuffixIdentifier(pointOfSale) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(pointOfSale: IPointOfSaleMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointOfSale);
    return this.http
      .patch<IPointOfSaleMySuffix>(`${this.resourceUrl}/${getPointOfSaleMySuffixIdentifier(pointOfSale) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPointOfSaleMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPointOfSaleMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPointOfSaleMySuffixToCollectionIfMissing(
    pointOfSaleCollection: IPointOfSaleMySuffix[],
    ...pointOfSalesToCheck: (IPointOfSaleMySuffix | null | undefined)[]
  ): IPointOfSaleMySuffix[] {
    const pointOfSales: IPointOfSaleMySuffix[] = pointOfSalesToCheck.filter(isPresent);
    if (pointOfSales.length > 0) {
      const pointOfSaleCollectionIdentifiers = pointOfSaleCollection.map(
        pointOfSaleItem => getPointOfSaleMySuffixIdentifier(pointOfSaleItem)!
      );
      const pointOfSalesToAdd = pointOfSales.filter(pointOfSaleItem => {
        const pointOfSaleIdentifier = getPointOfSaleMySuffixIdentifier(pointOfSaleItem);
        if (pointOfSaleIdentifier == null || pointOfSaleCollectionIdentifiers.includes(pointOfSaleIdentifier)) {
          return false;
        }
        pointOfSaleCollectionIdentifiers.push(pointOfSaleIdentifier);
        return true;
      });
      return [...pointOfSalesToAdd, ...pointOfSaleCollection];
    }
    return pointOfSaleCollection;
  }

  protected convertDateFromClient(pointOfSale: IPointOfSaleMySuffix): IPointOfSaleMySuffix {
    return Object.assign({}, pointOfSale, {
      createdDate: pointOfSale.createdDate?.isValid() ? pointOfSale.createdDate.toJSON() : undefined,
      lastModifiedDate: pointOfSale.lastModifiedDate?.isValid() ? pointOfSale.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((pointOfSale: IPointOfSaleMySuffix) => {
        pointOfSale.createdDate = pointOfSale.createdDate ? dayjs(pointOfSale.createdDate) : undefined;
        pointOfSale.lastModifiedDate = pointOfSale.lastModifiedDate ? dayjs(pointOfSale.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
