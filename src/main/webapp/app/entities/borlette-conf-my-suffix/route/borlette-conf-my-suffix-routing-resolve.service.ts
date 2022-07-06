import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBorletteConfMySuffix, BorletteConfMySuffix } from '../borlette-conf-my-suffix.model';
import { BorletteConfMySuffixService } from '../service/borlette-conf-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class BorletteConfMySuffixRoutingResolveService implements Resolve<IBorletteConfMySuffix> {
  constructor(protected service: BorletteConfMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBorletteConfMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((borletteConf: HttpResponse<BorletteConfMySuffix>) => {
          if (borletteConf.body) {
            return of(borletteConf.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BorletteConfMySuffix());
  }
}
