import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CreditSoldeMySuffixComponent } from './list/credit-solde-my-suffix.component';
import { CreditSoldeMySuffixDetailComponent } from './detail/credit-solde-my-suffix-detail.component';
import { CreditSoldeMySuffixUpdateComponent } from './update/credit-solde-my-suffix-update.component';
import { CreditSoldeMySuffixDeleteDialogComponent } from './delete/credit-solde-my-suffix-delete-dialog.component';
import { CreditSoldeMySuffixRoutingModule } from './route/credit-solde-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, CreditSoldeMySuffixRoutingModule],
  declarations: [
    CreditSoldeMySuffixComponent,
    CreditSoldeMySuffixDetailComponent,
    CreditSoldeMySuffixUpdateComponent,
    CreditSoldeMySuffixDeleteDialogComponent,
  ],
  entryComponents: [CreditSoldeMySuffixDeleteDialogComponent],
})
export class CreditSoldeMySuffixModule {}
