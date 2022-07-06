import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserRoleMySuffix, UserRoleMySuffix } from '../user-role-my-suffix.model';
import { UserRoleMySuffixService } from '../service/user-role-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class UserRoleMySuffixRoutingResolveService implements Resolve<IUserRoleMySuffix> {
  constructor(protected service: UserRoleMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserRoleMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userRole: HttpResponse<UserRoleMySuffix>) => {
          if (userRole.body) {
            return of(userRole.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserRoleMySuffix());
  }
}
