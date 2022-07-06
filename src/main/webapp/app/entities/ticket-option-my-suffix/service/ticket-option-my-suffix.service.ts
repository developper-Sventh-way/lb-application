import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITicketOptionMySuffix, getTicketOptionMySuffixIdentifier } from '../ticket-option-my-suffix.model';

export type EntityResponseType = HttpResponse<ITicketOptionMySuffix>;
export type EntityArrayResponseType = HttpResponse<ITicketOptionMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class TicketOptionMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ticket-options');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ticketOption: ITicketOptionMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ticketOption);
    return this.http
      .post<ITicketOptionMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ticketOption: ITicketOptionMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ticketOption);
    return this.http
      .put<ITicketOptionMySuffix>(`${this.resourceUrl}/${getTicketOptionMySuffixIdentifier(ticketOption) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(ticketOption: ITicketOptionMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ticketOption);
    return this.http
      .patch<ITicketOptionMySuffix>(`${this.resourceUrl}/${getTicketOptionMySuffixIdentifier(ticketOption) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITicketOptionMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITicketOptionMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTicketOptionMySuffixToCollectionIfMissing(
    ticketOptionCollection: ITicketOptionMySuffix[],
    ...ticketOptionsToCheck: (ITicketOptionMySuffix | null | undefined)[]
  ): ITicketOptionMySuffix[] {
    const ticketOptions: ITicketOptionMySuffix[] = ticketOptionsToCheck.filter(isPresent);
    if (ticketOptions.length > 0) {
      const ticketOptionCollectionIdentifiers = ticketOptionCollection.map(
        ticketOptionItem => getTicketOptionMySuffixIdentifier(ticketOptionItem)!
      );
      const ticketOptionsToAdd = ticketOptions.filter(ticketOptionItem => {
        const ticketOptionIdentifier = getTicketOptionMySuffixIdentifier(ticketOptionItem);
        if (ticketOptionIdentifier == null || ticketOptionCollectionIdentifiers.includes(ticketOptionIdentifier)) {
          return false;
        }
        ticketOptionCollectionIdentifiers.push(ticketOptionIdentifier);
        return true;
      });
      return [...ticketOptionsToAdd, ...ticketOptionCollection];
    }
    return ticketOptionCollection;
  }

  protected convertDateFromClient(ticketOption: ITicketOptionMySuffix): ITicketOptionMySuffix {
    return Object.assign({}, ticketOption, {
      createdDate: ticketOption.createdDate?.isValid() ? ticketOption.createdDate.toJSON() : undefined,
      lastModifiedDate: ticketOption.lastModifiedDate?.isValid() ? ticketOption.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((ticketOption: ITicketOptionMySuffix) => {
        ticketOption.createdDate = ticketOption.createdDate ? dayjs(ticketOption.createdDate) : undefined;
        ticketOption.lastModifiedDate = ticketOption.lastModifiedDate ? dayjs(ticketOption.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
