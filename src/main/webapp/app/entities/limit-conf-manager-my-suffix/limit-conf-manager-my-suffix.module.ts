import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LimitConfManagerMySuffixComponent } from './list/limit-conf-manager-my-suffix.component';
import { LimitConfManagerMySuffixDetailComponent } from './detail/limit-conf-manager-my-suffix-detail.component';
import { LimitConfManagerMySuffixUpdateComponent } from './update/limit-conf-manager-my-suffix-update.component';
import { LimitConfManagerMySuffixDeleteDialogComponent } from './delete/limit-conf-manager-my-suffix-delete-dialog.component';
import { LimitConfManagerMySuffixRoutingModule } from './route/limit-conf-manager-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, LimitConfManagerMySuffixRoutingModule],
  declarations: [
    LimitConfManagerMySuffixComponent,
    LimitConfManagerMySuffixDetailComponent,
    LimitConfManagerMySuffixUpdateComponent,
    LimitConfManagerMySuffixDeleteDialogComponent,
  ],
  entryComponents: [LimitConfManagerMySuffixDeleteDialogComponent],
})
export class LimitConfManagerMySuffixModule {}
