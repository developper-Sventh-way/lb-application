import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PointOfSaleMySuffixComponent } from './list/point-of-sale-my-suffix.component';
import { PointOfSaleMySuffixDetailComponent } from './detail/point-of-sale-my-suffix-detail.component';
import { PointOfSaleMySuffixUpdateComponent } from './update/point-of-sale-my-suffix-update.component';
import { PointOfSaleMySuffixDeleteDialogComponent } from './delete/point-of-sale-my-suffix-delete-dialog.component';
import { PointOfSaleMySuffixRoutingModule } from './route/point-of-sale-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, PointOfSaleMySuffixRoutingModule],
  declarations: [
    PointOfSaleMySuffixComponent,
    PointOfSaleMySuffixDetailComponent,
    PointOfSaleMySuffixUpdateComponent,
    PointOfSaleMySuffixDeleteDialogComponent,
  ],
  entryComponents: [PointOfSaleMySuffixDeleteDialogComponent],
})
export class PointOfSaleMySuffixModule {}
