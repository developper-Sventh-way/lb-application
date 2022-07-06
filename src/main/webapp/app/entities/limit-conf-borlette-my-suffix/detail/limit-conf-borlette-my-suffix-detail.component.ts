import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILimitConfBorletteMySuffix } from '../limit-conf-borlette-my-suffix.model';

@Component({
  selector: 'jhi-limit-conf-borlette-my-suffix-detail',
  templateUrl: './limit-conf-borlette-my-suffix-detail.component.html',
})
export class LimitConfBorletteMySuffixDetailComponent implements OnInit {
  limitConfBorlette: ILimitConfBorletteMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ limitConfBorlette }) => {
      this.limitConfBorlette = limitConfBorlette;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
