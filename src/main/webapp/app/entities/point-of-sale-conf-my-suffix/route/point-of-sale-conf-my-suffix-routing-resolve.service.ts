import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPointOfSaleConfMySuffix, PointOfSaleConfMySuffix } from '../point-of-sale-conf-my-suffix.model';
import { PointOfSaleConfMySuffixService } from '../service/point-of-sale-conf-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class PointOfSaleConfMySuffixRoutingResolveService implements Resolve<IPointOfSaleConfMySuffix> {
  constructor(protected service: PointOfSaleConfMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPointOfSaleConfMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pointOfSaleConf: HttpResponse<PointOfSaleConfMySuffix>) => {
          if (pointOfSaleConf.body) {
            return of(pointOfSaleConf.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PointOfSaleConfMySuffix());
  }
}
