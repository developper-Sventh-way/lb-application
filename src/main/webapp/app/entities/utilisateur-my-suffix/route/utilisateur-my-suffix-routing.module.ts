import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UtilisateurMySuffixComponent } from '../list/utilisateur-my-suffix.component';
import { UtilisateurMySuffixDetailComponent } from '../detail/utilisateur-my-suffix-detail.component';
import { UtilisateurMySuffixUpdateComponent } from '../update/utilisateur-my-suffix-update.component';
import { UtilisateurMySuffixRoutingResolveService } from './utilisateur-my-suffix-routing-resolve.service';

const utilisateurRoute: Routes = [
  {
    path: '',
    component: UtilisateurMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UtilisateurMySuffixDetailComponent,
    resolve: {
      utilisateur: UtilisateurMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UtilisateurMySuffixUpdateComponent,
    resolve: {
      utilisateur: UtilisateurMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UtilisateurMySuffixUpdateComponent,
    resolve: {
      utilisateur: UtilisateurMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(utilisateurRoute)],
  exports: [RouterModule],
})
export class UtilisateurMySuffixRoutingModule {}
