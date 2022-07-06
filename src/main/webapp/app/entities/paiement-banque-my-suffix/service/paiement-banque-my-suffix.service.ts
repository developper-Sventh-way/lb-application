import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaiementBanqueMySuffix, getPaiementBanqueMySuffixIdentifier } from '../paiement-banque-my-suffix.model';

export type EntityResponseType = HttpResponse<IPaiementBanqueMySuffix>;
export type EntityArrayResponseType = HttpResponse<IPaiementBanqueMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class PaiementBanqueMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/paiement-banques');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paiementBanque: IPaiementBanqueMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paiementBanque);
    return this.http
      .post<IPaiementBanqueMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paiementBanque: IPaiementBanqueMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paiementBanque);
    return this.http
      .put<IPaiementBanqueMySuffix>(`${this.resourceUrl}/${getPaiementBanqueMySuffixIdentifier(paiementBanque) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(paiementBanque: IPaiementBanqueMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paiementBanque);
    return this.http
      .patch<IPaiementBanqueMySuffix>(`${this.resourceUrl}/${getPaiementBanqueMySuffixIdentifier(paiementBanque) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPaiementBanqueMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaiementBanqueMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPaiementBanqueMySuffixToCollectionIfMissing(
    paiementBanqueCollection: IPaiementBanqueMySuffix[],
    ...paiementBanquesToCheck: (IPaiementBanqueMySuffix | null | undefined)[]
  ): IPaiementBanqueMySuffix[] {
    const paiementBanques: IPaiementBanqueMySuffix[] = paiementBanquesToCheck.filter(isPresent);
    if (paiementBanques.length > 0) {
      const paiementBanqueCollectionIdentifiers = paiementBanqueCollection.map(
        paiementBanqueItem => getPaiementBanqueMySuffixIdentifier(paiementBanqueItem)!
      );
      const paiementBanquesToAdd = paiementBanques.filter(paiementBanqueItem => {
        const paiementBanqueIdentifier = getPaiementBanqueMySuffixIdentifier(paiementBanqueItem);
        if (paiementBanqueIdentifier == null || paiementBanqueCollectionIdentifiers.includes(paiementBanqueIdentifier)) {
          return false;
        }
        paiementBanqueCollectionIdentifiers.push(paiementBanqueIdentifier);
        return true;
      });
      return [...paiementBanquesToAdd, ...paiementBanqueCollection];
    }
    return paiementBanqueCollection;
  }

  protected convertDateFromClient(paiementBanque: IPaiementBanqueMySuffix): IPaiementBanqueMySuffix {
    return Object.assign({}, paiementBanque, {
      startDate: paiementBanque.startDate?.isValid() ? paiementBanque.startDate.format(DATE_FORMAT) : undefined,
      endDate: paiementBanque.endDate?.isValid() ? paiementBanque.endDate.format(DATE_FORMAT) : undefined,
      createdDate: paiementBanque.createdDate?.isValid() ? paiementBanque.createdDate.toJSON() : undefined,
      lastModifiedDate: paiementBanque.lastModifiedDate?.isValid() ? paiementBanque.lastModifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? dayjs(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? dayjs(res.body.endDate) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? dayjs(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((paiementBanque: IPaiementBanqueMySuffix) => {
        paiementBanque.startDate = paiementBanque.startDate ? dayjs(paiementBanque.startDate) : undefined;
        paiementBanque.endDate = paiementBanque.endDate ? dayjs(paiementBanque.endDate) : undefined;
        paiementBanque.createdDate = paiementBanque.createdDate ? dayjs(paiementBanque.createdDate) : undefined;
        paiementBanque.lastModifiedDate = paiementBanque.lastModifiedDate ? dayjs(paiementBanque.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
