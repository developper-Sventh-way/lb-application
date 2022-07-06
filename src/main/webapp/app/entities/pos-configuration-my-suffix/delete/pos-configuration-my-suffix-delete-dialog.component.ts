import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPOSConfigurationMySuffix } from '../pos-configuration-my-suffix.model';
import { POSConfigurationMySuffixService } from '../service/pos-configuration-my-suffix.service';

@Component({
  templateUrl: './pos-configuration-my-suffix-delete-dialog.component.html',
})
export class POSConfigurationMySuffixDeleteDialogComponent {
  pOSConfiguration?: IPOSConfigurationMySuffix;

  constructor(protected pOSConfigurationService: POSConfigurationMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pOSConfigurationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
