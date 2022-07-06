import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILimitConfPointMySuffix } from '../limit-conf-point-my-suffix.model';

@Component({
  selector: 'jhi-limit-conf-point-my-suffix-detail',
  templateUrl: './limit-conf-point-my-suffix-detail.component.html',
})
export class LimitConfPointMySuffixDetailComponent implements OnInit {
  limitConfPoint: ILimitConfPointMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ limitConfPoint }) => {
      this.limitConfPoint = limitConfPoint;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
