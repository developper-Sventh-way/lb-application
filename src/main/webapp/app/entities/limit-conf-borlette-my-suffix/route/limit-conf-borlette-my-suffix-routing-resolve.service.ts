import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILimitConfBorletteMySuffix, LimitConfBorletteMySuffix } from '../limit-conf-borlette-my-suffix.model';
import { LimitConfBorletteMySuffixService } from '../service/limit-conf-borlette-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class LimitConfBorletteMySuffixRoutingResolveService implements Resolve<ILimitConfBorletteMySuffix> {
  constructor(protected service: LimitConfBorletteMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILimitConfBorletteMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((limitConfBorlette: HttpResponse<LimitConfBorletteMySuffix>) => {
          if (limitConfBorlette.body) {
            return of(limitConfBorlette.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LimitConfBorletteMySuffix());
  }
}
