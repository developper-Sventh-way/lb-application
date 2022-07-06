import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TicketOptionMySuffixComponent } from '../list/ticket-option-my-suffix.component';
import { TicketOptionMySuffixDetailComponent } from '../detail/ticket-option-my-suffix-detail.component';
import { TicketOptionMySuffixUpdateComponent } from '../update/ticket-option-my-suffix-update.component';
import { TicketOptionMySuffixRoutingResolveService } from './ticket-option-my-suffix-routing-resolve.service';

const ticketOptionRoute: Routes = [
  {
    path: '',
    component: TicketOptionMySuffixComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TicketOptionMySuffixDetailComponent,
    resolve: {
      ticketOption: TicketOptionMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TicketOptionMySuffixUpdateComponent,
    resolve: {
      ticketOption: TicketOptionMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TicketOptionMySuffixUpdateComponent,
    resolve: {
      ticketOption: TicketOptionMySuffixRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ticketOptionRoute)],
  exports: [RouterModule],
})
export class TicketOptionMySuffixRoutingModule {}
