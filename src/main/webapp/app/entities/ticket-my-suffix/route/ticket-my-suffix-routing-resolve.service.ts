import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITicketMySuffix, TicketMySuffix } from '../ticket-my-suffix.model';
import { TicketMySuffixService } from '../service/ticket-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class TicketMySuffixRoutingResolveService implements Resolve<ITicketMySuffix> {
  constructor(protected service: TicketMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITicketMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ticket: HttpResponse<TicketMySuffix>) => {
          if (ticket.body) {
            return of(ticket.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TicketMySuffix());
  }
}
