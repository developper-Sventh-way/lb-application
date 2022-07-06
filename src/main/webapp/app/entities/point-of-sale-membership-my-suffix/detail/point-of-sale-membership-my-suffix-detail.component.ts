import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPointOfSaleMembershipMySuffix } from '../point-of-sale-membership-my-suffix.model';

@Component({
  selector: 'jhi-point-of-sale-membership-my-suffix-detail',
  templateUrl: './point-of-sale-membership-my-suffix-detail.component.html',
})
export class PointOfSaleMembershipMySuffixDetailComponent implements OnInit {
  pointOfSaleMembership: IPointOfSaleMembershipMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pointOfSaleMembership }) => {
      this.pointOfSaleMembership = pointOfSaleMembership;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
