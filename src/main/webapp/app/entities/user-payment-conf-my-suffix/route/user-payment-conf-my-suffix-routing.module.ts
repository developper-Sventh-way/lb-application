import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserPaymentConfMySuffixComponent } from '../list/user-payment-conf-my-suffix.component';
import { UserPaymentConfMySuffixDetailComponent } from '../detail/user-payment-conf-my-suffix-detail.component';
import { UserPaymentConfMySuffixUpdateComponent } from '../update/user-payment-conf-my-suffix-update.component';
import { UserPaymentConfMySuffixRoutingResolveService } from './user-payment-conf-my-suffix-routing-resolve.service';

const userPaymentConfRoute: Routes = [
  {
    path: '',
    component: UserPaymentConfMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserPaymentConfMySuffixDetailComponent,
    resolve: {
      userPaymentConf: UserPaymentConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserPaymentConfMySuffixUpdateComponent,
    resolve: {
      userPaymentConf: UserPaymentConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserPaymentConfMySuffixUpdateComponent,
    resolve: {
      userPaymentConf: UserPaymentConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userPaymentConfRoute)],
  exports: [RouterModule],
})
export class UserPaymentConfMySuffixRoutingModule {}
