import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICreditSoldeMySuffix } from '../credit-solde-my-suffix.model';
import { CreditSoldeMySuffixService } from '../service/credit-solde-my-suffix.service';

@Component({
  templateUrl: './credit-solde-my-suffix-delete-dialog.component.html',
})
export class CreditSoldeMySuffixDeleteDialogComponent {
  creditSolde?: ICreditSoldeMySuffix;

  constructor(protected creditSoldeService: CreditSoldeMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.creditSoldeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
