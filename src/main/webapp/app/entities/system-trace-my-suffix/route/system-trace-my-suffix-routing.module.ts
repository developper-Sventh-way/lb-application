import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SystemTraceMySuffixComponent } from '../list/system-trace-my-suffix.component';
import { SystemTraceMySuffixDetailComponent } from '../detail/system-trace-my-suffix-detail.component';
import { SystemTraceMySuffixUpdateComponent } from '../update/system-trace-my-suffix-update.component';
import { SystemTraceMySuffixRoutingResolveService } from './system-trace-my-suffix-routing-resolve.service';

const systemTraceRoute: Routes = [
  {
    path: '',
    component: SystemTraceMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SystemTraceMySuffixDetailComponent,
    resolve: {
      systemTrace: SystemTraceMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SystemTraceMySuffixUpdateComponent,
    resolve: {
      systemTrace: SystemTraceMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SystemTraceMySuffixUpdateComponent,
    resolve: {
      systemTrace: SystemTraceMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(systemTraceRoute)],
  exports: [RouterModule],
})
export class SystemTraceMySuffixRoutingModule {}
