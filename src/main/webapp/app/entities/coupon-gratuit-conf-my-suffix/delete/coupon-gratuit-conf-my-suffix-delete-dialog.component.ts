import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICouponGratuitConfMySuffix } from '../coupon-gratuit-conf-my-suffix.model';
import { CouponGratuitConfMySuffixService } from '../service/coupon-gratuit-conf-my-suffix.service';

@Component({
  templateUrl: './coupon-gratuit-conf-my-suffix-delete-dialog.component.html',
})
export class CouponGratuitConfMySuffixDeleteDialogComponent {
  couponGratuitConf?: ICouponGratuitConfMySuffix;

  constructor(protected couponGratuitConfService: CouponGratuitConfMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.couponGratuitConfService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
