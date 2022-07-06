import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserSaleAccountMySuffix } from '../user-sale-account-my-suffix.model';

@Component({
  selector: 'jhi-user-sale-account-my-suffix-detail',
  templateUrl: './user-sale-account-my-suffix-detail.component.html',
})
export class UserSaleAccountMySuffixDetailComponent implements OnInit {
  userSaleAccount: IUserSaleAccountMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userSaleAccount }) => {
      this.userSaleAccount = userSaleAccount;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
