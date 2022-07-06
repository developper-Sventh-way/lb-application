import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UtilisateurMySuffixComponent } from './list/utilisateur-my-suffix.component';
import { UtilisateurMySuffixDetailComponent } from './detail/utilisateur-my-suffix-detail.component';
import { UtilisateurMySuffixUpdateComponent } from './update/utilisateur-my-suffix-update.component';
import { UtilisateurMySuffixDeleteDialogComponent } from './delete/utilisateur-my-suffix-delete-dialog.component';
import { UtilisateurMySuffixRoutingModule } from './route/utilisateur-my-suffix-routing.module';

@NgModule({
  imports: [SharedModule, UtilisateurMySuffixRoutingModule],
  declarations: [
    UtilisateurMySuffixComponent,
    UtilisateurMySuffixDetailComponent,
    UtilisateurMySuffixUpdateComponent,
    UtilisateurMySuffixDeleteDialogComponent,
  ],
  entryComponents: [UtilisateurMySuffixDeleteDialogComponent],
})
export class UtilisateurMySuffixModule {}
