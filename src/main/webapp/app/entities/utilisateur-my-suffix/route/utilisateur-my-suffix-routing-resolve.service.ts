import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUtilisateurMySuffix, UtilisateurMySuffix } from '../utilisateur-my-suffix.model';
import { UtilisateurMySuffixService } from '../service/utilisateur-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class UtilisateurMySuffixRoutingResolveService implements Resolve<IUtilisateurMySuffix> {
  constructor(protected service: UtilisateurMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUtilisateurMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((utilisateur: HttpResponse<UtilisateurMySuffix>) => {
          if (utilisateur.body) {
            return of(utilisateur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UtilisateurMySuffix());
  }
}
