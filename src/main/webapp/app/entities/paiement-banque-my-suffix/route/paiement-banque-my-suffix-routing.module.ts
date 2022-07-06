import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaiementBanqueMySuffixComponent } from '../list/paiement-banque-my-suffix.component';
import { PaiementBanqueMySuffixDetailComponent } from '../detail/paiement-banque-my-suffix-detail.component';
import { PaiementBanqueMySuffixUpdateComponent } from '../update/paiement-banque-my-suffix-update.component';
import { PaiementBanqueMySuffixRoutingResolveService } from './paiement-banque-my-suffix-routing-resolve.service';

const paiementBanqueRoute: Routes = [
  {
    path: '',
    component: PaiementBanqueMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaiementBanqueMySuffixDetailComponent,
    resolve: {
      paiementBanque: PaiementBanqueMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaiementBanqueMySuffixUpdateComponent,
    resolve: {
      paiementBanque: PaiementBanqueMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaiementBanqueMySuffixUpdateComponent,
    resolve: {
      paiementBanque: PaiementBanqueMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paiementBanqueRoute)],
  exports: [RouterModule],
})
export class PaiementBanqueMySuffixRoutingModule {}
