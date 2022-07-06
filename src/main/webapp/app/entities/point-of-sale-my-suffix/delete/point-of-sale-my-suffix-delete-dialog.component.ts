import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPointOfSaleMySuffix } from '../point-of-sale-my-suffix.model';
import { PointOfSaleMySuffixService } from '../service/point-of-sale-my-suffix.service';

@Component({
  templateUrl: './point-of-sale-my-suffix-delete-dialog.component.html',
})
export class PointOfSaleMySuffixDeleteDialogComponent {
  pointOfSale?: IPointOfSaleMySuffix;

  constructor(protected pointOfSaleService: PointOfSaleMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pointOfSaleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
