import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CreditSoldeMySuffixComponent } from '../list/credit-solde-my-suffix.component';
import { CreditSoldeMySuffixDetailComponent } from '../detail/credit-solde-my-suffix-detail.component';
import { CreditSoldeMySuffixUpdateComponent } from '../update/credit-solde-my-suffix-update.component';
import { CreditSoldeMySuffixRoutingResolveService } from './credit-solde-my-suffix-routing-resolve.service';

const creditSoldeRoute: Routes = [
  {
    path: '',
    component: CreditSoldeMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CreditSoldeMySuffixDetailComponent,
    resolve: {
      creditSolde: CreditSoldeMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CreditSoldeMySuffixUpdateComponent,
    resolve: {
      creditSolde: CreditSoldeMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CreditSoldeMySuffixUpdateComponent,
    resolve: {
      creditSolde: CreditSoldeMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(creditSoldeRoute)],
  exports: [RouterModule],
})
export class CreditSoldeMySuffixRoutingModule {}
