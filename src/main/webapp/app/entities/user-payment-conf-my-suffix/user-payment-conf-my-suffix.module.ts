import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserPaymentConfMySuffixComponent } from './list/user-payment-conf-my-suffix.component';
import { UserPaymentConfMySuffixDetailComponent } from './detail/user-payment-conf-my-suffix-detail.component';
import { UserPaymentConfMySuffixUpdateComponent } from './update/user-payment-conf-my-suffix-update.component';
import { UserPaymentConfMySuffixDeleteDialogComponent } from './delete/user-payment-conf-my-suffix-delete-dialog.component';
import { UserPaymentConfMySuffixRoutingModule } from './route/user-payment-conf-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, UserPaymentConfMySuffixRoutingModule],
  declarations: [
    UserPaymentConfMySuffixComponent,
    UserPaymentConfMySuffixDetailComponent,
    UserPaymentConfMySuffixUpdateComponent,
    UserPaymentConfMySuffixDeleteDialogComponent,
  ],
  entryComponents: [UserPaymentConfMySuffixDeleteDialogComponent],
})
export class UserPaymentConfMySuffixModule {}
