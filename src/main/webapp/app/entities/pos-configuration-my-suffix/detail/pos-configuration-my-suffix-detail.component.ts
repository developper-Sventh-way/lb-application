import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPOSConfigurationMySuffix } from '../pos-configuration-my-suffix.model';

@Component({
  selector: 'jhi-pos-configuration-my-suffix-detail',
  templateUrl: './pos-configuration-my-suffix-detail.component.html',
})
export class POSConfigurationMySuffixDetailComponent implements OnInit {
  pOSConfiguration: IPOSConfigurationMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pOSConfiguration }) => {
      this.pOSConfiguration = pOSConfiguration;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
