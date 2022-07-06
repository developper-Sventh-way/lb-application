import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TicketOptionMySuffixComponent } from './list/ticket-option-my-suffix.component';
import { TicketOptionMySuffixDetailComponent } from './detail/ticket-option-my-suffix-detail.component';
import { TicketOptionMySuffixUpdateComponent } from './update/ticket-option-my-suffix-update.component';
import { TicketOptionMySuffixDeleteDialogComponent } from './delete/ticket-option-my-suffix-delete-dialog.component';
import { TicketOptionMySuffixRoutingModule } from './route/ticket-option-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, TicketOptionMySuffixRoutingModule],
  declarations: [
    TicketOptionMySuffixComponent,
    TicketOptionMySuffixDetailComponent,
    TicketOptionMySuffixUpdateComponent,
    TicketOptionMySuffixDeleteDialogComponent,
  ],
  entryComponents: [TicketOptionMySuffixDeleteDialogComponent],
})
export class TicketOptionMySuffixModule {}
