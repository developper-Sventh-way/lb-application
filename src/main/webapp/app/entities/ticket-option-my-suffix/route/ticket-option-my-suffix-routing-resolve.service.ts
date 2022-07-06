import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITicketOptionMySuffix, TicketOptionMySuffix } from '../ticket-option-my-suffix.model';
import { TicketOptionMySuffixService } from '../service/ticket-option-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class TicketOptionMySuffixRoutingResolveService implements Resolve<ITicketOptionMySuffix> {
  constructor(protected service: TicketOptionMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITicketOptionMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ticketOption: HttpResponse<TicketOptionMySuffix>) => {
          if (ticketOption.body) {
            return of(ticketOption.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TicketOptionMySuffix());
  }
}
