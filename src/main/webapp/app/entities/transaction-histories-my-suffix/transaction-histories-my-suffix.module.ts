import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TransactionHistoriesMySuffixComponent } from './list/transaction-histories-my-suffix.component';
import { TransactionHistoriesMySuffixDetailComponent } from './detail/transaction-histories-my-suffix-detail.component';
import { TransactionHistoriesMySuffixUpdateComponent } from './update/transaction-histories-my-suffix-update.component';
import { TransactionHistoriesMySuffixDeleteDialogComponent } from './delete/transaction-histories-my-suffix-delete-dialog.component';
import { TransactionHistoriesMySuffixRoutingModule } from './route/transaction-histories-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, TransactionHistoriesMySuffixRoutingModule],
  declarations: [
    TransactionHistoriesMySuffixComponent,
    TransactionHistoriesMySuffixDetailComponent,
    TransactionHistoriesMySuffixUpdateComponent,
    TransactionHistoriesMySuffixDeleteDialogComponent,
  ],
  entryComponents: [TransactionHistoriesMySuffixDeleteDialogComponent],
})
export class TransactionHistoriesMySuffixModule {}
