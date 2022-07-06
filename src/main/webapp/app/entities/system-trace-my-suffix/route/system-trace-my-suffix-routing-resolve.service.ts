import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISystemTraceMySuffix, SystemTraceMySuffix } from '../system-trace-my-suffix.model';
import { SystemTraceMySuffixService } from '../service/system-trace-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class SystemTraceMySuffixRoutingResolveService implements Resolve<ISystemTraceMySuffix> {
  constructor(protected service: SystemTraceMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISystemTraceMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((systemTrace: HttpResponse<SystemTraceMySuffix>) => {
          if (systemTrace.body) {
            return of(systemTrace.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SystemTraceMySuffix());
  }
}
