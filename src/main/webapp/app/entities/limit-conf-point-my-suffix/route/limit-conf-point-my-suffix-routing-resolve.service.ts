import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILimitConfPointMySuffix, LimitConfPointMySuffix } from '../limit-conf-point-my-suffix.model';
import { LimitConfPointMySuffixService } from '../service/limit-conf-point-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class LimitConfPointMySuffixRoutingResolveService implements Resolve<ILimitConfPointMySuffix> {
  constructor(protected service: LimitConfPointMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILimitConfPointMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((limitConfPoint: HttpResponse<LimitConfPointMySuffix>) => {
          if (limitConfPoint.body) {
            return of(limitConfPoint.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LimitConfPointMySuffix());
  }
}
