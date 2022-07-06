import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaiementBanqueMySuffix } from '../paiement-banque-my-suffix.model';

@Component({
  selector: 'jhi-paiement-banque-my-suffix-detail',
  templateUrl: './paiement-banque-my-suffix-detail.component.html',
})
export class PaiementBanqueMySuffixDetailComponent implements OnInit {
  paiementBanque: IPaiementBanqueMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paiementBanque }) => {
      this.paiementBanque = paiementBanque;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
