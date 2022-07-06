import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserPaymentMySuffixComponent } from './list/user-payment-my-suffix.component';
import { UserPaymentMySuffixDetailComponent } from './detail/user-payment-my-suffix-detail.component';
import { UserPaymentMySuffixUpdateComponent } from './update/user-payment-my-suffix-update.component';
import { UserPaymentMySuffixDeleteDialogComponent } from './delete/user-payment-my-suffix-delete-dialog.component';
import { UserPaymentMySuffixRoutingModule } from './route/user-payment-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, UserPaymentMySuffixRoutingModule],
  declarations: [
    UserPaymentMySuffixComponent,
    UserPaymentMySuffixDetailComponent,
    UserPaymentMySuffixUpdateComponent,
    UserPaymentMySuffixDeleteDialogComponent,
  ],
  entryComponents: [UserPaymentMySuffixDeleteDialogComponent],
})
export class UserPaymentMySuffixModule {}
