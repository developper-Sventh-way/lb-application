import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PointOfSaleMembershipMySuffixComponent } from '../list/point-of-sale-membership-my-suffix.component';
import { PointOfSaleMembershipMySuffixDetailComponent } from '../detail/point-of-sale-membership-my-suffix-detail.component';
import { PointOfSaleMembershipMySuffixUpdateComponent } from '../update/point-of-sale-membership-my-suffix-update.component';
import { PointOfSaleMembershipMySuffixRoutingResolveService } from './point-of-sale-membership-my-suffix-routing-resolve.service';

const pointOfSaleMembershipRoute: Routes = [
  {
    path: '',
    component: PointOfSaleMembershipMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PointOfSaleMembershipMySuffixDetailComponent,
    resolve: {
      pointOfSaleMembership: PointOfSaleMembershipMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PointOfSaleMembershipMySuffixUpdateComponent,
    resolve: {
      pointOfSaleMembership: PointOfSaleMembershipMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PointOfSaleMembershipMySuffixUpdateComponent,
    resolve: {
      pointOfSaleMembership: PointOfSaleMembershipMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pointOfSaleMembershipRoute)],
  exports: [RouterModule],
})
export class PointOfSaleMembershipMySuffixRoutingModule {}
