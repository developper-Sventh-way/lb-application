import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserRoleMySuffix } from '../user-role-my-suffix.model';
import { UserRoleMySuffixService } from '../service/user-role-my-suffix.service';

@Component({
  templateUrl: './user-role-my-suffix-delete-dialog.component.html',
})
export class UserRoleMySuffixDeleteDialogComponent {
  userRole?: IUserRoleMySuffix;

  constructor(protected userRoleService: UserRoleMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userRoleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
