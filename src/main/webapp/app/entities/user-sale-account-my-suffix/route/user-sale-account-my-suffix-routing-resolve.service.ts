import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserSaleAccountMySuffix, UserSaleAccountMySuffix } from '../user-sale-account-my-suffix.model';
import { UserSaleAccountMySuffixService } from '../service/user-sale-account-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class UserSaleAccountMySuffixRoutingResolveService implements Resolve<IUserSaleAccountMySuffix> {
  constructor(protected service: UserSaleAccountMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserSaleAccountMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userSaleAccount: HttpResponse<UserSaleAccountMySuffix>) => {
          if (userSaleAccount.body) {
            return of(userSaleAccount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserSaleAccountMySuffix());
  }
}
