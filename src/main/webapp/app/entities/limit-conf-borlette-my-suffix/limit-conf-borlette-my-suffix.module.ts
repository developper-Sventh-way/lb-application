import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LimitConfBorletteMySuffixComponent } from './list/limit-conf-borlette-my-suffix.component';
import { LimitConfBorletteMySuffixDetailComponent } from './detail/limit-conf-borlette-my-suffix-detail.component';
import { LimitConfBorletteMySuffixUpdateComponent } from './update/limit-conf-borlette-my-suffix-update.component';
import { LimitConfBorletteMySuffixDeleteDialogComponent } from './delete/limit-conf-borlette-my-suffix-delete-dialog.component';
import { LimitConfBorletteMySuffixRoutingModule } from './route/limit-conf-borlette-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, LimitConfBorletteMySuffixRoutingModule],
  declarations: [
    LimitConfBorletteMySuffixComponent,
    LimitConfBorletteMySuffixDetailComponent,
    LimitConfBorletteMySuffixUpdateComponent,
    LimitConfBorletteMySuffixDeleteDialogComponent,
  ],
  entryComponents: [LimitConfBorletteMySuffixDeleteDialogComponent],
})
export class LimitConfBorletteMySuffixModule {}
