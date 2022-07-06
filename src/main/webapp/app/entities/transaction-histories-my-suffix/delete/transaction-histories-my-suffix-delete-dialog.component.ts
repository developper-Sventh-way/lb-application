import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransactionHistoriesMySuffix } from '../transaction-histories-my-suffix.model';
import { TransactionHistoriesMySuffixService } from '../service/transaction-histories-my-suffix.service';

@Component({
  templateUrl: './transaction-histories-my-suffix-delete-dialog.component.html',
})
export class TransactionHistoriesMySuffixDeleteDialogComponent {
  transactionHistories?: ITransactionHistoriesMySuffix;

  constructor(protected transactionHistoriesService: TransactionHistoriesMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transactionHistoriesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
