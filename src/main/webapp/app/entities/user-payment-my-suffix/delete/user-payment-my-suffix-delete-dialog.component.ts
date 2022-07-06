import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserPaymentMySuffix } from '../user-payment-my-suffix.model';
import { UserPaymentMySuffixService } from '../service/user-payment-my-suffix.service';

@Component({
  templateUrl: './user-payment-my-suffix-delete-dialog.component.html',
})
export class UserPaymentMySuffixDeleteDialogComponent {
  userPayment?: IUserPaymentMySuffix;

  constructor(protected userPaymentService: UserPaymentMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userPaymentService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
