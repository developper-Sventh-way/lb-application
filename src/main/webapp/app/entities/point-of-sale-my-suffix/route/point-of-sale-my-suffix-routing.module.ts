import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PointOfSaleMySuffixComponent } from '../list/point-of-sale-my-suffix.component';
import { PointOfSaleMySuffixDetailComponent } from '../detail/point-of-sale-my-suffix-detail.component';
import { PointOfSaleMySuffixUpdateComponent } from '../update/point-of-sale-my-suffix-update.component';
import { PointOfSaleMySuffixRoutingResolveService } from './point-of-sale-my-suffix-routing-resolve.service';

const pointOfSaleRoute: Routes = [
  {
    path: '',
    component: PointOfSaleMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PointOfSaleMySuffixDetailComponent,
    resolve: {
      pointOfSale: PointOfSaleMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PointOfSaleMySuffixUpdateComponent,
    resolve: {
      pointOfSale: PointOfSaleMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PointOfSaleMySuffixUpdateComponent,
    resolve: {
      pointOfSale: PointOfSaleMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pointOfSaleRoute)],
  exports: [RouterModule],
})
export class PointOfSaleMySuffixRoutingModule {}
