import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICouponGratuitConfMySuffix } from '../coupon-gratuit-conf-my-suffix.model';

@Component({
  selector: 'jhi-coupon-gratuit-conf-my-suffix-detail',
  templateUrl: './coupon-gratuit-conf-my-suffix-detail.component.html',
})
export class CouponGratuitConfMySuffixDetailComponent implements OnInit {
  couponGratuitConf: ICouponGratuitConfMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ couponGratuitConf }) => {
      this.couponGratuitConf = couponGratuitConf;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
