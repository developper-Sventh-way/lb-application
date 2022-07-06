import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserRoleMySuffixComponent } from '../list/user-role-my-suffix.component';
import { UserRoleMySuffixDetailComponent } from '../detail/user-role-my-suffix-detail.component';
import { UserRoleMySuffixUpdateComponent } from '../update/user-role-my-suffix-update.component';
import { UserRoleMySuffixRoutingResolveService } from './user-role-my-suffix-routing-resolve.service';

const userRoleRoute: Routes = [
  {
    path: '',
    component: UserRoleMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserRoleMySuffixDetailComponent,
    resolve: {
      userRole: UserRoleMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserRoleMySuffixUpdateComponent,
    resolve: {
      userRole: UserRoleMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserRoleMySuffixUpdateComponent,
    resolve: {
      userRole: UserRoleMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userRoleRoute)],
  exports: [RouterModule],
})
export class UserRoleMySuffixRoutingModule {}
