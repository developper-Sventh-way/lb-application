import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILimitConfBorletteMySuffix } from '../limit-conf-borlette-my-suffix.model';
import { LimitConfBorletteMySuffixService } from '../service/limit-conf-borlette-my-suffix.service';

@Component({
  templateUrl: './limit-conf-borlette-my-suffix-delete-dialog.component.html',
})
export class LimitConfBorletteMySuffixDeleteDialogComponent {
  limitConfBorlette?: ILimitConfBorletteMySuffix;

  constructor(protected limitConfBorletteService: LimitConfBorletteMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.limitConfBorletteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
