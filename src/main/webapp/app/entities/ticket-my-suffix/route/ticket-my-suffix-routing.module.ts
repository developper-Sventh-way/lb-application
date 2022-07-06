import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TicketMySuffixComponent } from '../list/ticket-my-suffix.component';
import { TicketMySuffixDetailComponent } from '../detail/ticket-my-suffix-detail.component';
import { TicketMySuffixUpdateComponent } from '../update/ticket-my-suffix-update.component';
import { TicketMySuffixRoutingResolveService } from './ticket-my-suffix-routing-resolve.service';

const ticketRoute: Routes = [
  {
    path: '',
    component: TicketMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TicketMySuffixDetailComponent,
    resolve: {
      ticket: TicketMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TicketMySuffixUpdateComponent,
    resolve: {
      ticket: TicketMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TicketMySuffixUpdateComponent,
    resolve: {
      ticket: TicketMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ticketRoute)],
  exports: [RouterModule],
})
export class TicketMySuffixRoutingModule {}
