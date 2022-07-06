import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITirageMySuffix } from '../tirage-my-suffix.model';

@Component({
  selector: 'jhi-tirage-my-suffix-detail',
  templateUrl: './tirage-my-suffix-detail.component.html',
})
export class TirageMySuffixDetailComponent implements OnInit {
  tirage: ITirageMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tirage }) => {
      this.tirage = tirage;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
