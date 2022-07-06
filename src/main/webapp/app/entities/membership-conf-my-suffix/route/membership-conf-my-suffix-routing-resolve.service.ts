import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMembershipConfMySuffix, MembershipConfMySuffix } from '../membership-conf-my-suffix.model';
import { MembershipConfMySuffixService } from '../service/membership-conf-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class MembershipConfMySuffixRoutingResolveService implements Resolve<IMembershipConfMySuffix> {
  constructor(protected service: MembershipConfMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMembershipConfMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((membershipConf: HttpResponse<MembershipConfMySuffix>) => {
          if (membershipConf.body) {
            return of(membershipConf.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MembershipConfMySuffix());
  }
}
