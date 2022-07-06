import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBorletteConfMySuffix } from '../borlette-conf-my-suffix.model';
import { BorletteConfMySuffixService } from '../service/borlette-conf-my-suffix.service';

@Component({
  templateUrl: './borlette-conf-my-suffix-delete-dialog.component.html',
})
export class BorletteConfMySuffixDeleteDialogComponent {
  borletteConf?: IBorletteConfMySuffix;

  constructor(protected borletteConfService: BorletteConfMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.borletteConfService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
