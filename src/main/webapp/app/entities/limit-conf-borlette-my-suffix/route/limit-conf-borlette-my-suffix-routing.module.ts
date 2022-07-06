import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LimitConfBorletteMySuffixComponent } from '../list/limit-conf-borlette-my-suffix.component';
import { LimitConfBorletteMySuffixDetailComponent } from '../detail/limit-conf-borlette-my-suffix-detail.component';
import { LimitConfBorletteMySuffixUpdateComponent } from '../update/limit-conf-borlette-my-suffix-update.component';
import { LimitConfBorletteMySuffixRoutingResolveService } from './limit-conf-borlette-my-suffix-routing-resolve.service';

const limitConfBorletteRoute: Routes = [
  {
    path: '',
    component: LimitConfBorletteMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LimitConfBorletteMySuffixDetailComponent,
    resolve: {
      limitConfBorlette: LimitConfBorletteMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LimitConfBorletteMySuffixUpdateComponent,
    resolve: {
      limitConfBorlette: LimitConfBorletteMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LimitConfBorletteMySuffixUpdateComponent,
    resolve: {
      limitConfBorlette: LimitConfBorletteMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(limitConfBorletteRoute)],
  exports: [RouterModule],
})
export class LimitConfBorletteMySuffixRoutingModule {}
