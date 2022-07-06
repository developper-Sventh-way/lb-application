import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPointOfSaleConfMySuffix } from '../point-of-sale-conf-my-suffix.model';

@Component({
  selector: 'jhi-point-of-sale-conf-my-suffix-detail',
  templateUrl: './point-of-sale-conf-my-suffix-detail.component.html',
})
export class PointOfSaleConfMySuffixDetailComponent implements OnInit {
  pointOfSaleConf: IPointOfSaleConfMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pointOfSaleConf }) => {
      this.pointOfSaleConf = pointOfSaleConf;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
