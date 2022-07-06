import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILimitConfPointMySuffix } from '../limit-conf-point-my-suffix.model';
import { LimitConfPointMySuffixService } from '../service/limit-conf-point-my-suffix.service';

@Component({
  templateUrl: './limit-conf-point-my-suffix-delete-dialog.component.html',
})
export class LimitConfPointMySuffixDeleteDialogComponent {
  limitConfPoint?: ILimitConfPointMySuffix;

  constructor(protected limitConfPointService: LimitConfPointMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.limitConfPointService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
