import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPointOfSaleMembershipMySuffix, PointOfSaleMembershipMySuffix } from '../point-of-sale-membership-my-suffix.model';
import { PointOfSaleMembershipMySuffixService } from '../service/point-of-sale-membership-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class PointOfSaleMembershipMySuffixRoutingResolveService implements Resolve<IPointOfSaleMembershipMySuffix> {
  constructor(protected service: PointOfSaleMembershipMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPointOfSaleMembershipMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pointOfSaleMembership: HttpResponse<PointOfSaleMembershipMySuffix>) => {
          if (pointOfSaleMembership.body) {
            return of(pointOfSaleMembership.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PointOfSaleMembershipMySuffix());
  }
}
