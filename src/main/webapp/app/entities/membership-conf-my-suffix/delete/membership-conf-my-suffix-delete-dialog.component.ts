import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMembershipConfMySuffix } from '../membership-conf-my-suffix.model';
import { MembershipConfMySuffixService } from '../service/membership-conf-my-suffix.service';

@Component({
  templateUrl: './membership-conf-my-suffix-delete-dialog.component.html',
})
export class MembershipConfMySuffixDeleteDialogComponent {
  membershipConf?: IMembershipConfMySuffix;

  constructor(protected membershipConfService: MembershipConfMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.membershipConfService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
