import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILimitConfManagerMySuffix } from '../limit-conf-manager-my-suffix.model';
import { LimitConfManagerMySuffixService } from '../service/limit-conf-manager-my-suffix.service';

@Component({
  templateUrl: './limit-conf-manager-my-suffix-delete-dialog.component.html',
})
export class LimitConfManagerMySuffixDeleteDialogComponent {
  limitConfManager?: ILimitConfManagerMySuffix;

  constructor(protected limitConfManagerService: LimitConfManagerMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.limitConfManagerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
