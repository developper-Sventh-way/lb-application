import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPointOfSaleMySuffix, PointOfSaleMySuffix } from '../point-of-sale-my-suffix.model';
import { PointOfSaleMySuffixService } from '../service/point-of-sale-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class PointOfSaleMySuffixRoutingResolveService implements Resolve<IPointOfSaleMySuffix> {
  constructor(protected service: PointOfSaleMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPointOfSaleMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pointOfSale: HttpResponse<PointOfSaleMySuffix>) => {
          if (pointOfSale.body) {
            return of(pointOfSale.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PointOfSaleMySuffix());
  }
}
