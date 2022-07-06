import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CouponGratuitConfMySuffixComponent } from './list/coupon-gratuit-conf-my-suffix.component';
import { CouponGratuitConfMySuffixDetailComponent } from './detail/coupon-gratuit-conf-my-suffix-detail.component';
import { CouponGratuitConfMySuffixUpdateComponent } from './update/coupon-gratuit-conf-my-suffix-update.component';
import { CouponGratuitConfMySuffixDeleteDialogComponent } from './delete/coupon-gratuit-conf-my-suffix-delete-dialog.component';
import { CouponGratuitConfMySuffixRoutingModule } from './route/coupon-gratuit-conf-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, CouponGratuitConfMySuffixRoutingModule],
  declarations: [
    CouponGratuitConfMySuffixComponent,
    CouponGratuitConfMySuffixDetailComponent,
    CouponGratuitConfMySuffixUpdateComponent,
    CouponGratuitConfMySuffixDeleteDialogComponent,
  ],
  entryComponents: [CouponGratuitConfMySuffixDeleteDialogComponent],
})
export class CouponGratuitConfMySuffixModule {}
