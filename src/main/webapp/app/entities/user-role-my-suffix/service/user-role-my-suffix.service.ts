import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserRoleMySuffix, getUserRoleMySuffixIdentifier } from '../user-role-my-suffix.model';

export type EntityResponseType = HttpResponse<IUserRoleMySuffix>;
export type EntityArrayResponseType = HttpResponse<IUserRoleMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class UserRoleMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-roles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userRole: IUserRoleMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userRole);
    return this.http
      .post<IUserRoleMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userRole: IUserRoleMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userRole);
    return this.http
      .put<IUserRoleMySuffix>(`${this.resourceUrl}/${getUserRoleMySuffixIdentifier(userRole) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(userRole: IUserRoleMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userRole);
    return this.http
      .patch<IUserRoleMySuffix>(`${this.resourceUrl}/${getUserRoleMySuffixIdentifier(userRole) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserRoleMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserRoleMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUserRoleMySuffixToCollectionIfMissing(
    userRoleCollection: IUserRoleMySuffix[],
    ...userRolesToCheck: (IUserRoleMySuffix | null | undefined)[]
  ): IUserRoleMySuffix[] {
    const userRoles: IUserRoleMySuffix[] = userRolesToCheck.filter(isPresent);
    if (userRoles.length > 0) {
      const userRoleCollectionIdentifiers = userRoleCollection.map(userRoleItem => getUserRoleMySuffixIdentifier(userRoleItem)!);
      const userRolesToAdd = userRoles.filter(userRoleItem => {
        const userRoleIdentifier = getUserRoleMySuffixIdentifier(userRoleItem);
        if (userRoleIdentifier == null || userRoleCollectionIdentifiers.includes(userRoleIdentifier)) {
          return false;
        }
        userRoleCollectionIdentifiers.push(userRoleIdentifier);
        return true;
      });
      return [...userRolesToAdd, ...userRoleCollection];
    }
    return userRoleCollection;
  }

  protected convertDateFromClient(userRole: IUserRoleMySuffix): IUserRoleMySuffix {
    return Object.assign({}, userRole, {
      createdDate: userRole.createdDate?.isValid() ? userRole.createdDate.toJSON() : undefined,
      lastModifiedDate: userRole.lastModifiedDate?.isValid() ? userRole.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((userRole: IUserRoleMySuffix) => {
        userRole.createdDate = userRole.createdDate ? dayjs(userRole.createdDate) : undefined;
        userRole.lastModifiedDate = userRole.lastModifiedDate ? dayjs(userRole.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
