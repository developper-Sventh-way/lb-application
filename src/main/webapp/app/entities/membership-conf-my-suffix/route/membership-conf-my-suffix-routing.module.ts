import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MembershipConfMySuffixComponent } from '../list/membership-conf-my-suffix.component';
import { MembershipConfMySuffixDetailComponent } from '../detail/membership-conf-my-suffix-detail.component';
import { MembershipConfMySuffixUpdateComponent } from '../update/membership-conf-my-suffix-update.component';
import { MembershipConfMySuffixRoutingResolveService } from './membership-conf-my-suffix-routing-resolve.service';

const membershipConfRoute: Routes = [
  {
    path: '',
    component: MembershipConfMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MembershipConfMySuffixDetailComponent,
    resolve: {
      membershipConf: MembershipConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MembershipConfMySuffixUpdateComponent,
    resolve: {
      membershipConf: MembershipConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MembershipConfMySuffixUpdateComponent,
    resolve: {
      membershipConf: MembershipConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(membershipConfRoute)],
  exports: [RouterModule],
})
export class MembershipConfMySuffixRoutingModule {}
