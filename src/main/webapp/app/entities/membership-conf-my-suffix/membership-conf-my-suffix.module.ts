import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MembershipConfMySuffixComponent } from './list/membership-conf-my-suffix.component';
import { MembershipConfMySuffixDetailComponent } from './detail/membership-conf-my-suffix-detail.component';
import { MembershipConfMySuffixUpdateComponent } from './update/membership-conf-my-suffix-update.component';
import { MembershipConfMySuffixDeleteDialogComponent } from './delete/membership-conf-my-suffix-delete-dialog.component';
import { MembershipConfMySuffixRoutingModule } from './route/membership-conf-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, MembershipConfMySuffixRoutingModule],
  declarations: [
    MembershipConfMySuffixComponent,
    MembershipConfMySuffixDetailComponent,
    MembershipConfMySuffixUpdateComponent,
    MembershipConfMySuffixDeleteDialogComponent,
  ],
  entryComponents: [MembershipConfMySuffixDeleteDialogComponent],
})
export class MembershipConfMySuffixModule {}
