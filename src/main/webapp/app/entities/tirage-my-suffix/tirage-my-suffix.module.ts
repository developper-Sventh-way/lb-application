import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TirageMySuffixComponent } from './list/tirage-my-suffix.component';
import { TirageMySuffixDetailComponent } from './detail/tirage-my-suffix-detail.component';
import { TirageMySuffixUpdateComponent } from './update/tirage-my-suffix-update.component';
import { TirageMySuffixDeleteDialogComponent } from './delete/tirage-my-suffix-delete-dialog.component';
import { TirageMySuffixRoutingModule } from './route/tirage-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, TirageMySuffixRoutingModule],
  declarations: [
    TirageMySuffixComponent,
    TirageMySuffixDetailComponent,
    TirageMySuffixUpdateComponent,
    TirageMySuffixDeleteDialogComponent,
  ],
  entryComponents: [TirageMySuffixDeleteDialogComponent],
})
export class TirageMySuffixModule {}
