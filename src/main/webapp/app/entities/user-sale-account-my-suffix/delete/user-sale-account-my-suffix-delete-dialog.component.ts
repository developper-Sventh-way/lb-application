import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserSaleAccountMySuffix } from '../user-sale-account-my-suffix.model';
import { UserSaleAccountMySuffixService } from '../service/user-sale-account-my-suffix.service';

@Component({
  templateUrl: './user-sale-account-my-suffix-delete-dialog.component.html',
})
export class UserSaleAccountMySuffixDeleteDialogComponent {
  userSaleAccount?: IUserSaleAccountMySuffix;

  constructor(protected userSaleAccountService: UserSaleAccountMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userSaleAccountService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
