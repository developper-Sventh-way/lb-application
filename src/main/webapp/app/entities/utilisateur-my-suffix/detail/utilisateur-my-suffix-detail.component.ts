import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUtilisateurMySuffix } from '../utilisateur-my-suffix.model';

@Component({
  selector: 'jhi-utilisateur-my-suffix-detail',
  templateUrl: './utilisateur-my-suffix-detail.component.html',
})
export class UtilisateurMySuffixDetailComponent implements OnInit {
  utilisateur: IUtilisateurMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ utilisateur }) => {
      this.utilisateur = utilisateur;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
