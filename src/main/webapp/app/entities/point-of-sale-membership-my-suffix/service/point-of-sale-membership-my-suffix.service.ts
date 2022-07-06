import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPointOfSaleMembershipMySuffix, getPointOfSaleMembershipMySuffixIdentifier } from '../point-of-sale-membership-my-suffix.model';

export type EntityResponseType = HttpResponse<IPointOfSaleMembershipMySuffix>;
export type EntityArrayResponseType = HttpResponse<IPointOfSaleMembershipMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class PointOfSaleMembershipMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/point-of-sale-memberships');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pointOfSaleMembership: IPointOfSaleMembershipMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointOfSaleMembership);
    return this.http
      .post<IPointOfSaleMembershipMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pointOfSaleMembership: IPointOfSaleMembershipMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointOfSaleMembership);
    return this.http
      .put<IPointOfSaleMembershipMySuffix>(
        `${this.resourceUrl}/${getPointOfSaleMembershipMySuffixIdentifier(pointOfSaleMembership) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(pointOfSaleMembership: IPointOfSaleMembershipMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointOfSaleMembership);
    return this.http
      .patch<IPointOfSaleMembershipMySuffix>(
        `${this.resourceUrl}/${getPointOfSaleMembershipMySuffixIdentifier(pointOfSaleMembership) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPointOfSaleMembershipMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPointOfSaleMembershipMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPointOfSaleMembershipMySuffixToCollectionIfMissing(
    pointOfSaleMembershipCollection: IPointOfSaleMembershipMySuffix[],
    ...pointOfSaleMembershipsToCheck: (IPointOfSaleMembershipMySuffix | null | undefined)[]
  ): IPointOfSaleMembershipMySuffix[] {
    const pointOfSaleMemberships: IPointOfSaleMembershipMySuffix[] = pointOfSaleMembershipsToCheck.filter(isPresent);
    if (pointOfSaleMemberships.length > 0) {
      const pointOfSaleMembershipCollectionIdentifiers = pointOfSaleMembershipCollection.map(
        pointOfSaleMembershipItem => getPointOfSaleMembershipMySuffixIdentifier(pointOfSaleMembershipItem)!
      );
      const pointOfSaleMembershipsToAdd = pointOfSaleMemberships.filter(pointOfSaleMembershipItem => {
        const pointOfSaleMembershipIdentifier = getPointOfSaleMembershipMySuffixIdentifier(pointOfSaleMembershipItem);
        if (
          pointOfSaleMembershipIdentifier == null ||
          pointOfSaleMembershipCollectionIdentifiers.includes(pointOfSaleMembershipIdentifier)
        ) {
          return false;
        }
        pointOfSaleMembershipCollectionIdentifiers.push(pointOfSaleMembershipIdentifier);
        return true;
      });
      return [...pointOfSaleMembershipsToAdd, ...pointOfSaleMembershipCollection];
    }
    return pointOfSaleMembershipCollection;
  }

  protected convertDateFromClient(pointOfSaleMembership: IPointOfSaleMembershipMySuffix): IPointOfSaleMembershipMySuffix {
    return Object.assign({}, pointOfSaleMembership, {
      createdDate: pointOfSaleMembership.createdDate?.isValid() ? pointOfSaleMembership.createdDate.toJSON() : undefined,
      lastModifiedDate: pointOfSaleMembership.lastModifiedDate?.isValid() ? pointOfSaleMembership.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((pointOfSaleMembership: IPointOfSaleMembershipMySuffix) => {
        pointOfSaleMembership.createdDate = pointOfSaleMembership.createdDate ? dayjs(pointOfSaleMembership.createdDate) : undefined;
        pointOfSaleMembership.lastModifiedDate = pointOfSaleMembership.lastModifiedDate
          ? dayjs(pointOfSaleMembership.lastModifiedDate)
          : undefined;
      });
    }
    return res;
  }
}
