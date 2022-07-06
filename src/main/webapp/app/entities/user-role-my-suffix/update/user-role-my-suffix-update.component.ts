import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUserRoleMySuffix, UserRoleMySuffix } from '../user-role-my-suffix.model';
import { UserRoleMySuffixService } from '../service/user-role-my-suffix.service';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { UtilisateurMySuffixService } from 'app/entities/utilisateur-my-suffix/service/utilisateur-my-suffix.service';
import { UserRoleName } from 'app/entities/enumerations/user-role-name.model';

@Component({
  selector: 'jhi-user-role-my-suffix-update',
  templateUrl: './user-role-my-suffix-update.component.html',
})
export class UserRoleMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  userRoleNameValues = Object.keys(UserRoleName);

  utilisateursSharedCollection: IUtilisateurMySuffix[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    role: [null, [Validators.required]],
    description: [null, [Validators.maxLength(60)]],
    exceptions: [null, [Validators.maxLength(60)]],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
    utilisateur: [],
  });

  constructor(
    protected userRoleService: UserRoleMySuffixService,
    protected utilisateurService: UtilisateurMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userRole }) => {
      if (userRole.id === undefined) {
        const today = dayjs().startOf('day');
        userRole.createdDate = today;
        userRole.lastModifiedDate = today;
      }

      this.updateForm(userRole);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userRole = this.createFromForm();
    if (userRole.id !== undefined) {
      this.subscribeToSaveResponse(this.userRoleService.update(userRole));
    } else {
      this.subscribeToSaveResponse(this.userRoleService.create(userRole));
    }
  }

  trackUtilisateurMySuffixById(_index: number, item: IUtilisateurMySuffix): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserRoleMySuffix>>): void {
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

  protected updateForm(userRole: IUserRoleMySuffix): void {
    this.editForm.patchValue({
      id: userRole.id,
      role: userRole.role,
      description: userRole.description,
      exceptions: userRole.exceptions,
      createdBy: userRole.createdBy,
      createdDate: userRole.createdDate ? userRole.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: userRole.lastModifiedBy,
      lastModifiedDate: userRole.lastModifiedDate ? userRole.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      utilisateur: userRole.utilisateur,
    });

    this.utilisateursSharedCollection = this.utilisateurService.addUtilisateurMySuffixToCollectionIfMissing(
      this.utilisateursSharedCollection,
      userRole.utilisateur
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

  protected createFromForm(): IUserRoleMySuffix {
    return {
      ...new UserRoleMySuffix(),
      id: this.editForm.get(['id'])!.value,
      role: this.editForm.get(['role'])!.value,
      description: this.editForm.get(['description'])!.value,
      exceptions: this.editForm.get(['exceptions'])!.value,
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
