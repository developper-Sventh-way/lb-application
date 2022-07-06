import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PointOfSaleConfMySuffixComponent } from './list/point-of-sale-conf-my-suffix.component';
import { PointOfSaleConfMySuffixDetailComponent } from './detail/point-of-sale-conf-my-suffix-detail.component';
import { PointOfSaleConfMySuffixUpdateComponent } from './update/point-of-sale-conf-my-suffix-update.component';
import { PointOfSaleConfMySuffixDeleteDialogComponent } from './delete/point-of-sale-conf-my-suffix-delete-dialog.component';
import { PointOfSaleConfMySuffixRoutingModule } from './route/point-of-sale-conf-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, PointOfSaleConfMySuffixRoutingModule],
  declarations: [
    PointOfSaleConfMySuffixComponent,
    PointOfSaleConfMySuffixDetailComponent,
    PointOfSaleConfMySuffixUpdateComponent,
    PointOfSaleConfMySuffixDeleteDialogComponent,
  ],
  entryComponents: [PointOfSaleConfMySuffixDeleteDialogComponent],
})
export class PointOfSaleConfMySuffixModule {}
