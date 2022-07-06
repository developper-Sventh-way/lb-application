import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserPaymentMySuffix } from '../user-payment-my-suffix.model';

@Component({
  selector: 'jhi-user-payment-my-suffix-detail',
  templateUrl: './user-payment-my-suffix-detail.component.html',
})
export class UserPaymentMySuffixDetailComponent implements OnInit {
  userPayment: IUserPaymentMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userPayment }) => {
      this.userPayment = userPayment;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
