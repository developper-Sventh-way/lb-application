import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITicketMySuffix } from '../ticket-my-suffix.model';
import { TicketMySuffixService } from '../service/ticket-my-suffix.service';

@Component({
  templateUrl: './ticket-my-suffix-delete-dialog.component.html',
})
export class TicketMySuffixDeleteDialogComponent {
  ticket?: ITicketMySuffix;

  constructor(protected ticketService: TicketMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ticketService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
