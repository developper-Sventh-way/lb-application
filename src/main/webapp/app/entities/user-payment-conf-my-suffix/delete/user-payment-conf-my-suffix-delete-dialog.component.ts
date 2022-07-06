import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserPaymentConfMySuffix } from '../user-payment-conf-my-suffix.model';
import { UserPaymentConfMySuffixService } from '../service/user-payment-conf-my-suffix.service';

@Component({
  templateUrl: './user-payment-conf-my-suffix-delete-dialog.component.html',
})
export class UserPaymentConfMySuffixDeleteDialogComponent {
  userPaymentConf?: IUserPaymentConfMySuffix;

  constructor(protected userPaymentConfService: UserPaymentConfMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userPaymentConfService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
