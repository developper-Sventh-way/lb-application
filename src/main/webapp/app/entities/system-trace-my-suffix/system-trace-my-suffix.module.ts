import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SystemTraceMySuffixComponent } from './list/system-trace-my-suffix.component';
import { SystemTraceMySuffixDetailComponent } from './detail/system-trace-my-suffix-detail.component';
import { SystemTraceMySuffixUpdateComponent } from './update/system-trace-my-suffix-update.component';
import { SystemTraceMySuffixDeleteDialogComponent } from './delete/system-trace-my-suffix-delete-dialog.component';
import { SystemTraceMySuffixRoutingModule } from './route/system-trace-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, SystemTraceMySuffixRoutingModule],
  declarations: [
    SystemTraceMySuffixComponent,
    SystemTraceMySuffixDetailComponent,
    SystemTraceMySuffixUpdateComponent,
    SystemTraceMySuffixDeleteDialogComponent,
  ],
  entryComponents: [SystemTraceMySuffixDeleteDialogComponent],
})
export class SystemTraceMySuffixModule {}
