import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserPaymentMySuffixComponent } from '../list/user-payment-my-suffix.component';
import { UserPaymentMySuffixDetailComponent } from '../detail/user-payment-my-suffix-detail.component';
import { UserPaymentMySuffixUpdateComponent } from '../update/user-payment-my-suffix-update.component';
import { UserPaymentMySuffixRoutingResolveService } from './user-payment-my-suffix-routing-resolve.service';

const userPaymentRoute: Routes = [
  {
    path: '',
    component: UserPaymentMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserPaymentMySuffixDetailComponent,
    resolve: {
      userPayment: UserPaymentMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserPaymentMySuffixUpdateComponent,
    resolve: {
      userPayment: UserPaymentMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserPaymentMySuffixUpdateComponent,
    resolve: {
      userPayment: UserPaymentMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userPaymentRoute)],
  exports: [RouterModule],
})
export class UserPaymentMySuffixRoutingModule {}
