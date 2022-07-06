import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CouponGratuitConfMySuffixComponent } from '../list/coupon-gratuit-conf-my-suffix.component';
import { CouponGratuitConfMySuffixDetailComponent } from '../detail/coupon-gratuit-conf-my-suffix-detail.component';
import { CouponGratuitConfMySuffixUpdateComponent } from '../update/coupon-gratuit-conf-my-suffix-update.component';
import { CouponGratuitConfMySuffixRoutingResolveService } from './coupon-gratuit-conf-my-suffix-routing-resolve.service';

const couponGratuitConfRoute: Routes = [
  {
    path: '',
    component: CouponGratuitConfMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CouponGratuitConfMySuffixDetailComponent,
    resolve: {
      couponGratuitConf: CouponGratuitConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CouponGratuitConfMySuffixUpdateComponent,
    resolve: {
      couponGratuitConf: CouponGratuitConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CouponGratuitConfMySuffixUpdateComponent,
    resolve: {
      couponGratuitConf: CouponGratuitConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(couponGratuitConfRoute)],
  exports: [RouterModule],
})
export class CouponGratuitConfMySuffixRoutingModule {}
