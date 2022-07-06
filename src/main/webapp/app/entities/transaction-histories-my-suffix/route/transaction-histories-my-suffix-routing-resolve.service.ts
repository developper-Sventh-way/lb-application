import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITransactionHistoriesMySuffix, TransactionHistoriesMySuffix } from '../transaction-histories-my-suffix.model';
import { TransactionHistoriesMySuffixService } from '../service/transaction-histories-my-suffix.service';

@Injectable({ providedIn: 'root' })
export class TransactionHistoriesMySuffixRoutingResolveService implements Resolve<ITransactionHistoriesMySuffix> {
  constructor(protected service: TransactionHistoriesMySuffixService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransactionHistoriesMySuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((transactionHistories: HttpResponse<TransactionHistoriesMySuffix>) => {
          if (transactionHistories.body) {
            return of(transactionHistories.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TransactionHistoriesMySuffix());
  }
}
