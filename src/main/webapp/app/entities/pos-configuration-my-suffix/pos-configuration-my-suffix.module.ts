import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { POSConfigurationMySuffixComponent } from './list/pos-configuration-my-suffix.component';
import { POSConfigurationMySuffixDetailComponent } from './detail/pos-configuration-my-suffix-detail.component';
import { POSConfigurationMySuffixUpdateComponent } from './update/pos-configuration-my-suffix-update.component';
import { POSConfigurationMySuffixDeleteDialogComponent } from './delete/pos-configuration-my-suffix-delete-dialog.component';
import { POSConfigurationMySuffixRoutingModule } from './route/pos-configuration-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, POSConfigurationMySuffixRoutingModule],
  declarations: [
    POSConfigurationMySuffixComponent,
    POSConfigurationMySuffixDetailComponent,
    POSConfigurationMySuffixUpdateComponent,
    POSConfigurationMySuffixDeleteDialogComponent,
  ],
  entryComponents: [POSConfigurationMySuffixDeleteDialogComponent],
})
export class POSConfigurationMySuffixModule {}
