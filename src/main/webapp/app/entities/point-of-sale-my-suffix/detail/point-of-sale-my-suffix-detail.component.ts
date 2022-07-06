import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPointOfSaleMySuffix } from '../point-of-sale-my-suffix.model';

@Component({
  selector: 'jhi-point-of-sale-my-suffix-detail',
  templateUrl: './point-of-sale-my-suffix-detail.component.html',
})
export class PointOfSaleMySuffixDetailComponent implements OnInit {
  pointOfSale: IPointOfSaleMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pointOfSale }) => {
      this.pointOfSale = pointOfSale;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
