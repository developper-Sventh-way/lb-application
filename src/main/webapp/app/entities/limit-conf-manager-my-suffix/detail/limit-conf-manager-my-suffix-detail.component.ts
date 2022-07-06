import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILimitConfManagerMySuffix } from '../limit-conf-manager-my-suffix.model';

@Component({
  selector: 'jhi-limit-conf-manager-my-suffix-detail',
  templateUrl: './limit-conf-manager-my-suffix-detail.component.html',
})
export class LimitConfManagerMySuffixDetailComponent implements OnInit {
  limitConfManager: ILimitConfManagerMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ limitConfManager }) => {
      this.limitConfManager = limitConfManager;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
