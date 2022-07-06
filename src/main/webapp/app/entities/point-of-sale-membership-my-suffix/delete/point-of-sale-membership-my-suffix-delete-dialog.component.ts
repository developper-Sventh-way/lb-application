import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPointOfSaleMembershipMySuffix } from '../point-of-sale-membership-my-suffix.model';
import { PointOfSaleMembershipMySuffixService } from '../service/point-of-sale-membership-my-suffix.service';

@Component({
  templateUrl: './point-of-sale-membership-my-suffix-delete-dialog.component.html',
})
export class PointOfSaleMembershipMySuffixDeleteDialogComponent {
  pointOfSaleMembership?: IPointOfSaleMembershipMySuffix;

  constructor(protected pointOfSaleMembershipService: PointOfSaleMembershipMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pointOfSaleMembershipService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
