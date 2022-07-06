import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserSaleAccountMySuffixComponent } from '../list/user-sale-account-my-suffix.component';
import { UserSaleAccountMySuffixDetailComponent } from '../detail/user-sale-account-my-suffix-detail.component';
import { UserSaleAccountMySuffixUpdateComponent } from '../update/user-sale-account-my-suffix-update.component';
import { UserSaleAccountMySuffixRoutingResolveService } from './user-sale-account-my-suffix-routing-resolve.service';

const userSaleAccountRoute: Routes = [
  {
    path: '',
    component: UserSaleAccountMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserSaleAccountMySuffixDetailComponent,
    resolve: {
      userSaleAccount: UserSaleAccountMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserSaleAccountMySuffixUpdateComponent,
    resolve: {
      userSaleAccount: UserSaleAccountMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserSaleAccountMySuffixUpdateComponent,
    resolve: {
      userSaleAccount: UserSaleAccountMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userSaleAccountRoute)],
  exports: [RouterModule],
})
export class UserSaleAccountMySuffixRoutingModule {}
