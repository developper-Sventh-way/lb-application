import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserPaymentMySuffix, UserPaymentMySuffix } from '../user-payment-my-suffix.model';
import { UserPaymentMySuffixService } from '../service/user-payment-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class UserPaymentMySuffixRoutingResolveService implements Resolve<IUserPaymentMySuffix> {
  constructor(protected service: UserPaymentMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserPaymentMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userPayment: HttpResponse<UserPaymentMySuffix>) => {
          if (userPayment.body) {
            return of(userPayment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserPaymentMySuffix());
  }
}
