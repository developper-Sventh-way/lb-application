import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUserSaleAccountMySuffix, UserSaleAccountMySuffix } from '../user-sale-account-my-suffix.model';
import { UserSaleAccountMySuffixService } from '../service/user-sale-account-my-suffix.service';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { UtilisateurMySuffixService } from 'app/entities/utilisateur-my-suffix/service/utilisateur-my-suffix.service';
import { UserSaleAccountStatut } from 'app/entities/enumerations/user-sale-account-statut.model';

@Component({
  selector: 'jhi-user-sale-account-my-suffix-update',
  templateUrl: './user-sale-account-my-suffix-update.component.html',
})
export class UserSaleAccountMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  userSaleAccountStatutValues = Object.keys(UserSaleAccountStatut);

  utilisateursSharedCollection: IUtilisateurMySuffix[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    balance: [null, [Validators.required]],
    lastPayment: [null, [Validators.required]],
    statut: [],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
    utilisateur: [],
  });

  constructor(
    protected userSaleAccountService: UserSaleAccountMySuffixService,
    protected utilisateurService: UtilisateurMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userSaleAccount }) => {
      if (userSaleAccount.id === undefined) {
        const today = dayjs().startOf('day');
        userSaleAccount.startDate = today;
        userSaleAccount.endDate = today;
        userSaleAccount.createdDate = today;
        userSaleAccount.lastModifiedDate = today;
      }

      this.updateForm(userSaleAccount);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userSaleAccount = this.createFromForm();
    if (userSaleAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.userSaleAccountService.update(userSaleAccount));
    } else {
      this.subscribeToSaveResponse(this.userSaleAccountService.create(userSaleAccount));
    }
  }

  trackUtilisateurMySuffixById(_index: number, item: IUtilisateurMySuffix): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserSaleAccountMySuffix>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(userSaleAccount: IUserSaleAccountMySuffix): void {
    this.editForm.patchValue({
      id: userSaleAccount.id,
      balance: userSaleAccount.balance,
      lastPayment: userSaleAccount.lastPayment,
      statut: userSaleAccount.statut,
      startDate: userSaleAccount.startDate ? userSaleAccount.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: userSaleAccount.endDate ? userSaleAccount.endDate.format(DATE_TIME_FORMAT) : null,
      createdBy: userSaleAccount.createdBy,
      createdDate: userSaleAccount.createdDate ? userSaleAccount.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: userSaleAccount.lastModifiedBy,
      lastModifiedDate: userSaleAccount.lastModifiedDate ? userSaleAccount.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      utilisateur: userSaleAccount.utilisateur,
    });

    this.utilisateursSharedCollection = this.utilisateurService.addUtilisateurMySuffixToCollectionIfMissing(
      this.utilisateursSharedCollection,
      userSaleAccount.utilisateur
    );
  }

  protected loadRelationshipsOptions(): void {
    this.utilisateurService
      .query()
      .pipe(map((res: HttpResponse<IUtilisateurMySuffix[]>) => res.body ?? []))
      .pipe(
        map((utilisateurs: IUtilisateurMySuffix[]) =>
          this.utilisateurService.addUtilisateurMySuffixToCollectionIfMissing(utilisateurs, this.editForm.get('utilisateur')!.value)
        )
      )
      .subscribe((utilisateurs: IUtilisateurMySuffix[]) => (this.utilisateursSharedCollection = utilisateurs));
  }

  protected createFromForm(): IUserSaleAccountMySuffix {
    return {
      ...new UserSaleAccountMySuffix(),
      id: this.editForm.get(['id'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      lastPayment: this.editForm.get(['lastPayment'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? dayjs(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value ? dayjs(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? dayjs(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      utilisateur: this.editForm.get(['utilisateur'])!.value,
    };
  }
}
