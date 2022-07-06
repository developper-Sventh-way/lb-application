import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICouponGratuitConfMySuffix, getCouponGratuitConfMySuffixIdentifier } from '../coupon-gratuit-conf-my-suffix.model';

export type EntityResponseType = HttpResponse<ICouponGratuitConfMySuffix>;
export type EntityArrayResponseType = HttpResponse<ICouponGratuitConfMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class CouponGratuitConfMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/coupon-gratuit-confs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(couponGratuitConf: ICouponGratuitConfMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(couponGratuitConf);
    return this.http
      .post<ICouponGratuitConfMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(couponGratuitConf: ICouponGratuitConfMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(couponGratuitConf);
    return this.http
      .put<ICouponGratuitConfMySuffix>(`${this.resourceUrl}/${getCouponGratuitConfMySuffixIdentifier(couponGratuitConf) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(couponGratuitConf: ICouponGratuitConfMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(couponGratuitConf);
    return this.http
      .patch<ICouponGratuitConfMySuffix>(
        `${this.resourceUrl}/${getCouponGratuitConfMySuffixIdentifier(couponGratuitConf) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICouponGratuitConfMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICouponGratuitConfMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCouponGratuitConfMySuffixToCollectionIfMissing(
    couponGratuitConfCollection: ICouponGratuitConfMySuffix[],
    ...couponGratuitConfsToCheck: (ICouponGratuitConfMySuffix | null | undefined)[]
  ): ICouponGratuitConfMySuffix[] {
    const couponGratuitConfs: ICouponGratuitConfMySuffix[] = couponGratuitConfsToCheck.filter(isPresent);
    if (couponGratuitConfs.length > 0) {
      const couponGratuitConfCollectionIdentifiers = couponGratuitConfCollection.map(
        couponGratuitConfItem => getCouponGratuitConfMySuffixIdentifier(couponGratuitConfItem)!
      );
      const couponGratuitConfsToAdd = couponGratuitConfs.filter(couponGratuitConfItem => {
        const couponGratuitConfIdentifier = getCouponGratuitConfMySuffixIdentifier(couponGratuitConfItem);
        if (couponGratuitConfIdentifier == null || couponGratuitConfCollectionIdentifiers.includes(couponGratuitConfIdentifier)) {
          return false;
        }
        couponGratuitConfCollectionIdentifiers.push(couponGratuitConfIdentifier);
        return true;
      });
      return [...couponGratuitConfsToAdd, ...couponGratuitConfCollection];
    }
    return couponGratuitConfCollection;
  }

  protected convertDateFromClient(couponGratuitConf: ICouponGratuitConfMySuffix): ICouponGratuitConfMySuffix {
    return Object.assign({}, couponGratuitConf, {
      createdDate: couponGratuitConf.createdDate?.isValid() ? couponGratuitConf.createdDate.toJSON() : undefined,
      lastModifiedDate: couponGratuitConf.lastModifiedDate?.isValid() ? couponGratuitConf.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((couponGratuitConf: ICouponGratuitConfMySuffix) => {
        couponGratuitConf.createdDate = couponGratuitConf.createdDate ? dayjs(couponGratuitConf.createdDate) : undefined;
        couponGratuitConf.lastModifiedDate = couponGratuitConf.lastModifiedDate ? dayjs(couponGratuitConf.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
