import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionHistoriesMySuffix } from '../transaction-histories-my-suffix.model';

@Component({
  selector: 'jhi-transaction-histories-my-suffix-detail',
  templateUrl: './transaction-histories-my-suffix-detail.component.html',
})
export class TransactionHistoriesMySuffixDetailComponent implements OnInit {
  transactionHistories: ITransactionHistoriesMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionHistories }) => {
      this.transactionHistories = transactionHistories;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
