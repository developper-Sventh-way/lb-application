import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaiementBanqueMySuffix, PaiementBanqueMySuffix } from '../paiement-banque-my-suffix.model';
import { PaiementBanqueMySuffixService } from '../service/paiement-banque-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class PaiementBanqueMySuffixRoutingResolveService implements Resolve<IPaiementBanqueMySuffix> {
  constructor(protected service: PaiementBanqueMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaiementBanqueMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paiementBanque: HttpResponse<PaiementBanqueMySuffix>) => {
          if (paiementBanque.body) {
            return of(paiementBanque.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaiementBanqueMySuffix());
  }
}
