import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICreditSoldeMySuffix, CreditSoldeMySuffix } from '../credit-solde-my-suffix.model';
import { CreditSoldeMySuffixService } from '../service/credit-solde-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class CreditSoldeMySuffixRoutingResolveService implements Resolve<ICreditSoldeMySuffix> {
  constructor(protected service: CreditSoldeMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICreditSoldeMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((creditSolde: HttpResponse<CreditSoldeMySuffix>) => {
          if (creditSolde.body) {
            return of(creditSolde.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CreditSoldeMySuffix());
  }
}
