import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TirageMySuffixComponent } from '../list/tirage-my-suffix.component';
import { TirageMySuffixDetailComponent } from '../detail/tirage-my-suffix-detail.component';
import { TirageMySuffixUpdateComponent } from '../update/tirage-my-suffix-update.component';
import { TirageMySuffixRoutingResolveService } from './tirage-my-suffix-routing-resolve.service';

const tirageRoute: Routes = [
  {
    path: '',
    component: TirageMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TirageMySuffixDetailComponent,
    resolve: {
      tirage: TirageMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TirageMySuffixUpdateComponent,
    resolve: {
      tirage: TirageMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TirageMySuffixUpdateComponent,
    resolve: {
      tirage: TirageMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tirageRoute)],
  exports: [RouterModule],
})
export class TirageMySuffixRoutingModule {}
