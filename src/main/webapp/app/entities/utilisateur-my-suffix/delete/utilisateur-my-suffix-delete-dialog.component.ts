import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUtilisateurMySuffix } from '../utilisateur-my-suffix.model';
import { UtilisateurMySuffixService } from '../service/utilisateur-my-suffix.service';

@Component({
  templateUrl: './utilisateur-my-suffix-delete-dialog.component.html',
})
export class UtilisateurMySuffixDeleteDialogComponent {
  utilisateur?: IUtilisateurMySuffix;

  constructor(protected utilisateurService: UtilisateurMySuffixService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.utilisateurService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
