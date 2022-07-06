import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LimitConfPointMySuffixComponent } from '../list/limit-conf-point-my-suffix.component';
import { LimitConfPointMySuffixDetailComponent } from '../detail/limit-conf-point-my-suffix-detail.component';
import { LimitConfPointMySuffixUpdateComponent } from '../update/limit-conf-point-my-suffix-update.component';
import { LimitConfPointMySuffixRoutingResolveService } from './limit-conf-point-my-suffix-routing-resolve.service';

const limitConfPointRoute: Routes = [
  {
    path: '',
    component: LimitConfPointMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LimitConfPointMySuffixDetailComponent,
    resolve: {
      limitConfPoint: LimitConfPointMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LimitConfPointMySuffixUpdateComponent,
    resolve: {
      limitConfPoint: LimitConfPointMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LimitConfPointMySuffixUpdateComponent,
    resolve: {
      limitConfPoint: LimitConfPointMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(limitConfPointRoute)],
  exports: [RouterModule],
})
export class LimitConfPointMySuffixRoutingModule {}
