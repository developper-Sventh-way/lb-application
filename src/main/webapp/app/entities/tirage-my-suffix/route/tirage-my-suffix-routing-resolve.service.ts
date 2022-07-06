import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITirageMySuffix, TirageMySuffix } from '../tirage-my-suffix.model';
import { TirageMySuffixService } from '../service/tirage-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class TirageMySuffixRoutingResolveService implements Resolve<ITirageMySuffix> {
  constructor(protected service: TirageMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITirageMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tirage: HttpResponse<TirageMySuffix>) => {
          if (tirage.body) {
            return of(tirage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TirageMySuffix());
  }
}
