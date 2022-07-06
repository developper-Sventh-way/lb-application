import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITicketOptionMySuffix } from '../ticket-option-my-suffix.model';
import { TicketOptionMySuffixService } from '../service/ticket-option-my-suffix.service';

@Component({
  templateUrl: './ticket-option-my-suffix-delete-dialog.component.html',
})
export class TicketOptionMySuffixDeleteDialogComponent {
  ticketOption?: ITicketOptionMySuffix;

  constructor(protected ticketOptionService: TicketOptionMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ticketOptionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
