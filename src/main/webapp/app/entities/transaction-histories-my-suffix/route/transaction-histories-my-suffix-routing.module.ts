import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TransactionHistoriesMySuffixComponent } from '../list/transaction-histories-my-suffix.component';
import { TransactionHistoriesMySuffixDetailComponent } from '../detail/transaction-histories-my-suffix-detail.component';
import { TransactionHistoriesMySuffixUpdateComponent } from '../update/transaction-histories-my-suffix-update.component';
import { TransactionHistoriesMySuffixRoutingResolveService } from './transaction-histories-my-suffix-routing-resolve.service';

const transactionHistoriesRoute: Routes = [
  {
    path: '',
    component: TransactionHistoriesMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransactionHistoriesMySuffixDetailComponent,
    resolve: {
      transactionHistories: TransactionHistoriesMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransactionHistoriesMySuffixUpdateComponent,
    resolve: {
      transactionHistories: TransactionHistoriesMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransactionHistoriesMySuffixUpdateComponent,
    resolve: {
      transactionHistories: TransactionHistoriesMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(transactionHistoriesRoute)],
  exports: [RouterModule],
})
export class TransactionHistoriesMySuffixRoutingModule {}
