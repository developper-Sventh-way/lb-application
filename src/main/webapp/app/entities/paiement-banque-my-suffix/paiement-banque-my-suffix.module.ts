import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PaiementBanqueMySuffixComponent } from './list/paiement-banque-my-suffix.component';
import { PaiementBanqueMySuffixDetailComponent } from './detail/paiement-banque-my-suffix-detail.component';
import { PaiementBanqueMySuffixUpdateComponent } from './update/paiement-banque-my-suffix-update.component';
import { PaiementBanqueMySuffixDeleteDialogComponent } from './delete/paiement-banque-my-suffix-delete-dialog.component';
import { PaiementBanqueMySuffixRoutingModule } from './route/paiement-banque-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, PaiementBanqueMySuffixRoutingModule],
  declarations: [
    PaiementBanqueMySuffixComponent,
    PaiementBanqueMySuffixDetailComponent,
    PaiementBanqueMySuffixUpdateComponent,
    PaiementBanqueMySuffixDeleteDialogComponent,
  ],
  entryComponents: [PaiementBanqueMySuffixDeleteDialogComponent],
})
export class PaiementBanqueMySuffixModule {}
