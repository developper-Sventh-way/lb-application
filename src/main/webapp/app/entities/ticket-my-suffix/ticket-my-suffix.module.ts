import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TicketMySuffixComponent } from './list/ticket-my-suffix.component';
import { TicketMySuffixDetailComponent } from './detail/ticket-my-suffix-detail.component';
import { TicketMySuffixUpdateComponent } from './update/ticket-my-suffix-update.component';
import { TicketMySuffixDeleteDialogComponent } from './delete/ticket-my-suffix-delete-dialog.component';
import { TicketMySuffixRoutingModule } from './route/ticket-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, TicketMySuffixRoutingModule],
  declarations: [
    TicketMySuffixComponent,
    TicketMySuffixDetailComponent,
    TicketMySuffixUpdateComponent,
    TicketMySuffixDeleteDialogComponent,
  ],
  entryComponents: [TicketMySuffixDeleteDialogComponent],
})
export class TicketMySuffixModule {}
