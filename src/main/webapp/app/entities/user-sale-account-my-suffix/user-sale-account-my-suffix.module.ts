import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserSaleAccountMySuffixComponent } from './list/user-sale-account-my-suffix.component';
import { UserSaleAccountMySuffixDetailComponent } from './detail/user-sale-account-my-suffix-detail.component';
import { UserSaleAccountMySuffixUpdateComponent } from './update/user-sale-account-my-suffix-update.component';
import { UserSaleAccountMySuffixDeleteDialogComponent } from './delete/user-sale-account-my-suffix-delete-dialog.component';
import { UserSaleAccountMySuffixRoutingModule } from './route/user-sale-account-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, UserSaleAccountMySuffixRoutingModule],
  declarations: [
    UserSaleAccountMySuffixComponent,
    UserSaleAccountMySuffixDetailComponent,
    UserSaleAccountMySuffixUpdateComponent,
    UserSaleAccountMySuffixDeleteDialogComponent,
  ],
  entryComponents: [UserSaleAccountMySuffixDeleteDialogComponent],
})
export class UserSaleAccountMySuffixModule {}
