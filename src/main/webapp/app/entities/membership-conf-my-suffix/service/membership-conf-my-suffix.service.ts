import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMembershipConfMySuffix, getMembershipConfMySuffixIdentifier } from '../membership-conf-my-suffix.model';

export type EntityResponseType = HttpResponse<IMembershipConfMySuffix>;
export type EntityArrayResponseType = HttpResponse<IMembershipConfMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class MembershipConfMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/membership-confs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(membershipConf: IMembershipConfMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(membershipConf);
    return this.http
      .post<IMembershipConfMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(membershipConf: IMembershipConfMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(membershipConf);
    return this.http
      .put<IMembershipConfMySuffix>(`${this.resourceUrl}/${getMembershipConfMySuffixIdentifier(membershipConf) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(membershipConf: IMembershipConfMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(membershipConf);
    return this.http
      .patch<IMembershipConfMySuffix>(`${this.resourceUrl}/${getMembershipConfMySuffixIdentifier(membershipConf) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMembershipConfMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMembershipConfMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMembershipConfMySuffixToCollectionIfMissing(
    membershipConfCollection: IMembershipConfMySuffix[],
    ...membershipConfsToCheck: (IMembershipConfMySuffix | null | undefined)[]
  ): IMembershipConfMySuffix[] {
    const membershipConfs: IMembershipConfMySuffix[] = membershipConfsToCheck.filter(isPresent);
    if (membershipConfs.length > 0) {
      const membershipConfCollectionIdentifiers = membershipConfCollection.map(
        membershipConfItem => getMembershipConfMySuffixIdentifier(membershipConfItem)!
      );
      const membershipConfsToAdd = membershipConfs.filter(membershipConfItem => {
        const membershipConfIdentifier = getMembershipConfMySuffixIdentifier(membershipConfItem);
        if (membershipConfIdentifier == null || membershipConfCollectionIdentifiers.includes(membershipConfIdentifier)) {
          return false;
        }
        membershipConfCollectionIdentifiers.push(membershipConfIdentifier);
        return true;
      });
      return [...membershipConfsToAdd, ...membershipConfCollection];
    }
    return membershipConfCollection;
  }

  protected convertDateFromClient(membershipConf: IMembershipConfMySuffix): IMembershipConfMySuffix {
    return Object.assign({}, membershipConf, {
      createdDate: membershipConf.createdDate?.isValid() ? membershipConf.createdDate.toJSON() : undefined,
      lastModifiedDate: membershipConf.lastModifiedDate?.isValid() ? membershipConf.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((membershipConf: IMembershipConfMySuffix) => {
        membershipConf.createdDate = membershipConf.createdDate ? dayjs(membershipConf.createdDate) : undefined;
        membershipConf.lastModifiedDate = membershipConf.lastModifiedDate ? dayjs(membershipConf.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
