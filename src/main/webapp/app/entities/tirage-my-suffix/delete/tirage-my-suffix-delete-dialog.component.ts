import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITirageMySuffix } from '../tirage-my-suffix.model';
import { TirageMySuffixService } from '../service/tirage-my-suffix.service';

@Component({
  templateUrl: './tirage-my-suffix-delete-dialog.component.html',
})
export class TirageMySuffixDeleteDialogComponent {
  tirage?: ITirageMySuffix;

  constructor(protected tirageService: TirageMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tirageService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
