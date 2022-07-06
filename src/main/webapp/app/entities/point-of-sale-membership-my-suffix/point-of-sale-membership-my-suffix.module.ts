import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PointOfSaleMembershipMySuffixComponent } from './list/point-of-sale-membership-my-suffix.component';
import { PointOfSaleMembershipMySuffixDetailComponent } from './detail/point-of-sale-membership-my-suffix-detail.component';
import { PointOfSaleMembershipMySuffixUpdateComponent } from './update/point-of-sale-membership-my-suffix-update.component';
import { PointOfSaleMembershipMySuffixDeleteDialogComponent } from './delete/point-of-sale-membership-my-suffix-delete-dialog.component';
import { PointOfSaleMembershipMySuffixRoutingModule } from './route/point-of-sale-membership-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, PointOfSaleMembershipMySuffixRoutingModule],
  declarations: [
    PointOfSaleMembershipMySuffixComponent,
    PointOfSaleMembershipMySuffixDetailComponent,
    PointOfSaleMembershipMySuffixUpdateComponent,
    PointOfSaleMembershipMySuffixDeleteDialogComponent,
  ],
  entryComponents: [PointOfSaleMembershipMySuffixDeleteDialogComponent],
})
export class PointOfSaleMembershipMySuffixModule {}
