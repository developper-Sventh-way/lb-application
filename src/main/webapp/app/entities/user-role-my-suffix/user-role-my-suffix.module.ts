import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserRoleMySuffixComponent } from './list/user-role-my-suffix.component';
import { UserRoleMySuffixDetailComponent } from './detail/user-role-my-suffix-detail.component';
import { UserRoleMySuffixUpdateComponent } from './update/user-role-my-suffix-update.component';
import { UserRoleMySuffixDeleteDialogComponent } from './delete/user-role-my-suffix-delete-dialog.component';
import { UserRoleMySuffixRoutingModule } from './route/user-role-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, UserRoleMySuffixRoutingModule],
  declarations: [
    UserRoleMySuffixComponent,
    UserRoleMySuffixDetailComponent,
    UserRoleMySuffixUpdateComponent,
    UserRoleMySuffixDeleteDialogComponent,
  ],
  entryComponents: [UserRoleMySuffixDeleteDialogComponent],
})
export class UserRoleMySuffixModule {}
