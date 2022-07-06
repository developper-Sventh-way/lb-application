import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BorletteConfMySuffixComponent } from '../list/borlette-conf-my-suffix.component';
import { BorletteConfMySuffixDetailComponent } from '../detail/borlette-conf-my-suffix-detail.component';
import { BorletteConfMySuffixUpdateComponent } from '../update/borlette-conf-my-suffix-update.component';
import { BorletteConfMySuffixRoutingResolveService } from './borlette-conf-my-suffix-routing-resolve.service';

const borletteConfRoute: Routes = [
  {
    path: '',
    component: BorletteConfMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BorletteConfMySuffixDetailComponent,
    resolve: {
      borletteConf: BorletteConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BorletteConfMySuffixUpdateComponent,
    resolve: {
      borletteConf: BorletteConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BorletteConfMySuffixUpdateComponent,
    resolve: {
      borletteConf: BorletteConfMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(borletteConfRoute)],
  exports: [RouterModule],
})
export class BorletteConfMySuffixRoutingModule {}
