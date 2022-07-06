import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LimitConfPointMySuffixComponent } from './list/limit-conf-point-my-suffix.component';
import { LimitConfPointMySuffixDetailComponent } from './detail/limit-conf-point-my-suffix-detail.component';
import { LimitConfPointMySuffixUpdateComponent } from './update/limit-conf-point-my-suffix-update.component';
import { LimitConfPointMySuffixDeleteDialogComponent } from './delete/limit-conf-point-my-suffix-delete-dialog.component';
import { LimitConfPointMySuffixRoutingModule } from './route/limit-conf-point-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, LimitConfPointMySuffixRoutingModule],
  declarations: [
    LimitConfPointMySuffixComponent,
    LimitConfPointMySuffixDetailComponent,
    LimitConfPointMySuffixUpdateComponent,
    LimitConfPointMySuffixDeleteDialogComponent,
  ],
  entryComponents: [LimitConfPointMySuffixDeleteDialogComponent],
})
export class LimitConfPointMySuffixModule {}
