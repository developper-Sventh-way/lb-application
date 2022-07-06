import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { POSConfigurationMySuffixComponent } from '../list/pos-configuration-my-suffix.component';
import { POSConfigurationMySuffixDetailComponent } from '../detail/pos-configuration-my-suffix-detail.component';
import { POSConfigurationMySuffixUpdateComponent } from '../update/pos-configuration-my-suffix-update.component';
import { POSConfigurationMySuffixRoutingResolveService } from './pos-configuration-my-suffix-routing-resolve.service';

const pOSConfigurationRoute: Routes = [
  {
    path: '',
    component: POSConfigurationMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: POSConfigurationMySuffixDetailComponent,
    resolve: {
      pOSConfiguration: POSConfigurationMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: POSConfigurationMySuffixUpdateComponent,
    resolve: {
      pOSConfiguration: POSConfigurationMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: POSConfigurationMySuffixUpdateComponent,
    resolve: {
      pOSConfiguration: POSConfigurationMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pOSConfigurationRoute)],
  exports: [RouterModule],
})
export class POSConfigurationMySuffixRoutingModule {}
