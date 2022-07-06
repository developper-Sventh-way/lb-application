import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPointOfSaleConfMySuffix } from '../point-of-sale-conf-my-suffix.model';
import { PointOfSaleConfMySuffixService } from '../service/point-of-sale-conf-my-suffix.service';

@Component({
  templateUrl: './point-of-sale-conf-my-suffix-delete-dialog.component.html',
})
export class PointOfSaleConfMySuffixDeleteDialogComponent {
  pointOfSaleConf?: IPointOfSaleConfMySuffix;

  constructor(protected pointOfSaleConfService: PointOfSaleConfMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pointOfSaleConfService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
