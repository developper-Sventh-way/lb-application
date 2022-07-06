import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserPaymentConfMySuffix } from '../user-payment-conf-my-suffix.model';

@Component({
  selector: 'jhi-user-payment-conf-my-suffix-detail',
  templateUrl: './user-payment-conf-my-suffix-detail.component.html',
})
export class UserPaymentConfMySuffixDetailComponent implements OnInit {
  userPaymentConf: IUserPaymentConfMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userPaymentConf }) => {
      this.userPaymentConf = userPaymentConf;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
