import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILimitConfManagerMySuffix, LimitConfManagerMySuffix } from '../limit-conf-manager-my-suffix.model';
import { LimitConfManagerMySuffixService } from '../service/limit-conf-manager-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class LimitConfManagerMySuffixRoutingResolveService implements Resolve<ILimitConfManagerMySuffix> {
  constructor(protected service: LimitConfManagerMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILimitConfManagerMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((limitConfManager: HttpResponse<LimitConfManagerMySuffix>) => {
          if (limitConfManager.body) {
            return of(limitConfManager.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LimitConfManagerMySuffix());
  }
}
