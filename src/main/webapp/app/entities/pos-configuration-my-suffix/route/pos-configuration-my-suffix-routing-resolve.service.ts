import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPOSConfigurationMySuffix, POSConfigurationMySuffix } from '../pos-configuration-my-suffix.model';
import { POSConfigurationMySuffixService } from '../service/pos-configuration-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class POSConfigurationMySuffixRoutingResolveService implements Resolve<IPOSConfigurationMySuffix> {
  constructor(protected service: POSConfigurationMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPOSConfigurationMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pOSConfiguration: HttpResponse<POSConfigurationMySuffix>) => {
          if (pOSConfiguration.body) {
            return of(pOSConfiguration.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new POSConfigurationMySuffix());
  }
}
