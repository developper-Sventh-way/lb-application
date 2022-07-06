import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICouponGratuitConfMySuffix, CouponGratuitConfMySuffix } from '../coupon-gratuit-conf-my-suffix.model';
import { CouponGratuitConfMySuffixService } from '../service/coupon-gratuit-conf-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class CouponGratuitConfMySuffixRoutingResolveService implements Resolve<ICouponGratuitConfMySuffix> {
  constructor(protected service: CouponGratuitConfMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICouponGratuitConfMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((couponGratuitConf: HttpResponse<CouponGratuitConfMySuffix>) => {
          if (couponGratuitConf.body) {
            return of(couponGratuitConf.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CouponGratuitConfMySuffix());
  }
}
