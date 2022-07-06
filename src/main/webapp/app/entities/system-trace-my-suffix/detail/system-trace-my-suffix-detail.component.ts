import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISystemTraceMySuffix } from '../system-trace-my-suffix.model';

@Component({
  selector: 'jhi-system-trace-my-suffix-detail',
  templateUrl: './system-trace-my-suffix-detail.component.html',
})
export class SystemTraceMySuffixDetailComponent implements OnInit {
  systemTrace: ISystemTraceMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ systemTrace }) => {
      this.systemTrace = systemTrace;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
