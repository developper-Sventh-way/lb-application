import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserPaymentConfMySuffix, UserPaymentConfMySuffix } from '../user-payment-conf-my-suffix.model';
import { UserPaymentConfMySuffixService } from '../service/user-payment-conf-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class UserPaymentConfMySuffixRoutingResolveService implements Resolve<IUserPaymentConfMySuffix> {
  constructor(protected service: UserPaymentConfMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserPaymentConfMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userPaymentConf: HttpResponse<UserPaymentConfMySuffix>) => {
          if (userPaymentConf.body) {
            return of(userPaymentConf.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserPaymentConfMySuffix());
  }
}
