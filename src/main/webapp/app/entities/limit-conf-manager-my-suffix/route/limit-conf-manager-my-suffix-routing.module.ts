import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LimitConfManagerMySuffixComponent } from '../list/limit-conf-manager-my-suffix.component';
import { LimitConfManagerMySuffixDetailComponent } from '../detail/limit-conf-manager-my-suffix-detail.component';
import { LimitConfManagerMySuffixUpdateComponent } from '../update/limit-conf-manager-my-suffix-update.component';
import { LimitConfManagerMySuffixRoutingResolveService } from './limit-conf-manager-my-suffix-routing-resolve.service';

const limitConfManagerRoute: Routes = [
  {
    path: '',
    component: LimitConfManagerMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LimitConfManagerMySuffixDetailComponent,
    resolve: {
      limitConfManager: LimitConfManagerMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LimitConfManagerMySuffixUpdateComponent,
    resolve: {
      limitConfManager: LimitConfManagerMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LimitConfManagerMySuffixUpdateComponent,
    resolve: {
      limitConfManager: LimitConfManagerMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(limitConfManagerRoute)],
  exports: [RouterModule],
})
export class LimitConfManagerMySuffixRoutingModule {}
