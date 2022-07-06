import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICreditSoldeMySuffix } from '../credit-solde-my-suffix.model';

@Component({
  selector: 'jhi-credit-solde-my-suffix-detail',
  templateUrl: './credit-solde-my-suffix-detail.component.html',
})
export class CreditSoldeMySuffixDetailComponent implements OnInit {
  creditSolde: ICreditSoldeMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ creditSolde }) => {
      this.creditSolde = creditSolde;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
