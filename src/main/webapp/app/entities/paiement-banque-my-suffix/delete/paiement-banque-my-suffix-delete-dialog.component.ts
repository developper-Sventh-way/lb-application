import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaiementBanqueMySuffix } from '../paiement-banque-my-suffix.model';
import { PaiementBanqueMySuffixService } from '../service/paiement-banque-my-suffix.service';

@Component({
  templateUrl: './paiement-banque-my-suffix-delete-dialog.component.html',
})
export class PaiementBanqueMySuffixDeleteDialogComponent {
  paiementBanque?: IPaiementBanqueMySuffix;

  constructor(protected paiementBanqueService: PaiementBanqueMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paiementBanqueService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
