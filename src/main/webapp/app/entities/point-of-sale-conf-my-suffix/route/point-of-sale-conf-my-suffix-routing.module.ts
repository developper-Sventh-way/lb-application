import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PointOfSaleConfMySuffixComponent } from '../list/point-of-sale-conf-my-suffix.component';
import { PointOfSaleConfMySuffixDetailComponent } from '../detail/point-of-sale-conf-my-suffix-detail.component';
import { PointOfSaleConfMySuffixUpdateComponent } from '../update/point-of-sale-conf-my-suffix-update.component';
import { PointOfSaleConfMySuffixRoutingResolveService } from './point-of-sale-conf-my-suffix-routing-resolve.service';

const pointOfSaleConfRoute: Routes = [
  {
    path: '',
    component: PointOfSaleConfMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PointOfSaleConfMySuffixDetailComponent,
    resolve: {
      pointOfSaleConf: PointOfSaleConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PointOfSaleConfMySuffixUpdateComponent,
    resolve: {
      pointOfSaleConf: PointOfSaleConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PointOfSaleConfMySuffixUpdateComponent,
    resolve: {
      pointOfSaleConf: PointOfSaleConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pointOfSaleConfRoute)],
  exports: [RouterModule],
})
export class PointOfSaleConfMySuffixRoutingModule {}
