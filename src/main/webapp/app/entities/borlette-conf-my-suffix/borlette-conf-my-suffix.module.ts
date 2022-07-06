import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BorletteConfMySuffixComponent } from './list/borlette-conf-my-suffix.component';
import { BorletteConfMySuffixDetailComponent } from './detail/borlette-conf-my-suffix-detail.component';
import { BorletteConfMySuffixUpdateComponent } from './update/borlette-conf-my-suffix-update.component';
import { BorletteConfMySuffixDeleteDialogComponent } from './delete/borlette-conf-my-suffix-delete-dialog.component';
import { BorletteConfMySuffixRoutingModule } from './route/borlette-conf-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, BorletteConfMySuffixRoutingModule],
  declarations: [
    BorletteConfMySuffixComponent,
    BorletteConfMySuffixDetailComponent,
    BorletteConfMySuffixUpdateComponent,
    BorletteConfMySuffixDeleteDialogComponent,
  ],
  entryComponents: [BorletteConfMySuffixDeleteDialogComponent],
})
export class BorletteConfMySuffixModule {}
