import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUserPaymentMySuffix, UserPaymentMySuffix } from '../user-payment-my-suffix.model';
import { UserPaymentMySuffixService } from '../service/user-payment-my-suffix.service';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { UtilisateurMySuffixService } from 'app/entities/utilisateur-my-suffix/service/utilisateur-my-suffix.service';

@Component({
  selector: 'jhi-user-payment-my-suffix-update',
  templateUrl: './user-payment-my-suffix-update.component.html',
})
export class UserPaymentMySuffixUpdateComponent implements OnInit {
  isSaving = false;

  utilisateursSharedCollection: IUtilisateurMySuffix[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    payAmount: [null, [Validators.required]],
    balance: [null, [Validators.required]],
    startDate: [],
    endDate: [],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
    utilisateur: [],
  });

  constructor(
    protected userPaymentService: UserPaymentMySuffixService,
    protected utilisateurService: UtilisateurMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userPayment }) => {
      if (userPayment.id === undefined) {
        const today = dayjs().startOf('day');
        userPayment.createdDate = today;
        userPayment.lastModifiedDate = today;
      }

      this.updateForm(userPayment);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userPayment = this.createFromForm();
    if (userPayment.id !== undefined) {
      this.subscribeToSaveResponse(this.userPaymentService.update(userPayment));
    } else {
      this.subscribeToSaveResponse(this.userPaymentService.create(userPayment));
    }
  }

  trackUtilisateurMySuffixById(_index: number, item: IUtilisateurMySuffix): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserPaymentMySuffix>>): void {
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

  protected updateForm(userPayment: IUserPaymentMySuffix): void {
    this.editForm.patchValue({
      id: userPayment.id,
      payAmount: userPayment.payAmount,
      balance: userPayment.balance,
      startDate: userPayment.startDate,
      endDate: userPayment.endDate,
      createdBy: userPayment.createdBy,
      createdDate: userPayment.createdDate ? userPayment.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: userPayment.lastModifiedBy,
      lastModifiedDate: userPayment.lastModifiedDate ? userPayment.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      utilisateur: userPayment.utilisateur,
    });

    this.utilisateursSharedCollection = this.utilisateurService.addUtilisateurMySuffixToCollectionIfMissing(
      this.utilisateursSharedCollection,
      userPayment.utilisateur
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

  protected createFromForm(): IUserPaymentMySuffix {
    return {
      ...new UserPaymentMySuffix(),
      id: this.editForm.get(['id'])!.value,
      payAmount: this.editForm.get(['payAmount'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
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
