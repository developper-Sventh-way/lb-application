import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBorletteConfMySuffix } from '../borlette-conf-my-suffix.model';

@Component({
  selector: 'jhi-borlette-conf-my-suffix-detail',
  templateUrl: './borlette-conf-my-suffix-detail.component.html',
})
export class BorletteConfMySuffixDetailComponent implements OnInit {
  borletteConf: IBorletteConfMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ borletteConf }) => {
      this.borletteConf = borletteConf;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
