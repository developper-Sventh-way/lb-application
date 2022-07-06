import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITicketMySuffix, getTicketMySuffixIdentifier } from '../ticket-my-suffix.model';

export type EntityResponseType = HttpResponse<ITicketMySuffix>;
export type EntityArrayResponseType = HttpResponse<ITicketMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class TicketMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tickets');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ticket: ITicketMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ticket);
    return this.http
      .post<ITicketMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ticket: ITicketMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ticket);
    return this.http
      .put<ITicketMySuffix>(`${this.resourceUrl}/${getTicketMySuffixIdentifier(ticket) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(ticket: ITicketMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ticket);
    return this.http
      .patch<ITicketMySuffix>(`${this.resourceUrl}/${getTicketMySuffixIdentifier(ticket) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITicketMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITicketMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTicketMySuffixToCollectionIfMissing(
    ticketCollection: ITicketMySuffix[],
    ...ticketsToCheck: (ITicketMySuffix | null | undefined)[]
  ): ITicketMySuffix[] {
    const tickets: ITicketMySuffix[] = ticketsToCheck.filter(isPresent);
    if (tickets.length > 0) {
      const ticketCollectionIdentifiers = ticketCollection.map(ticketItem => getTicketMySuffixIdentifier(ticketItem)!);
      const ticketsToAdd = tickets.filter(ticketItem => {
        const ticketIdentifier = getTicketMySuffixIdentifier(ticketItem);
        if (ticketIdentifier == null || ticketCollectionIdentifiers.includes(ticketIdentifier)) {
          return false;
        }
        ticketCollectionIdentifiers.push(ticketIdentifier);
        return true;
      });
      return [...ticketsToAdd, ...ticketCollection];
    }
    return ticketCollection;
  }

  protected convertDateFromClient(ticket: ITicketMySuffix): ITicketMySuffix {
    return Object.assign({}, ticket, {
      closeDate: ticket.closeDate?.isValid() ? ticket.closeDate.toJSON() : undefined,
      payDate: ticket.payDate?.isValid() ? ticket.payDate.toJSON() : undefined,
      createdDate: ticket.createdDate?.isValid() ? ticket.createdDate.toJSON() : undefined,
      lastModifiedDate: ticket.lastModifiedDate?.isValid() ? ticket.lastModifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.closeDate = res.body.closeDate ? dayjs(res.body.closeDate) : undefined;
      res.body.payDate = res.body.payDate ? dayjs(res.body.payDate) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? dayjs(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ticket: ITicketMySuffix) => {
        ticket.closeDate = ticket.closeDate ? dayjs(ticket.closeDate) : undefined;
        ticket.payDate = ticket.payDate ? dayjs(ticket.payDate) : undefined;
        ticket.createdDate = ticket.createdDate ? dayjs(ticket.createdDate) : undefined;
        ticket.lastModifiedDate = ticket.lastModifiedDate ? dayjs(ticket.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
