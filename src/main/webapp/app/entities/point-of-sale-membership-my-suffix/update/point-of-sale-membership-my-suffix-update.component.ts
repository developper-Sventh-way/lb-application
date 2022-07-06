import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPointOfSaleMembershipMySuffix, PointOfSaleMembershipMySuffix } from '../point-of-sale-membership-my-suffix.model';
import { PointOfSaleMembershipMySuffixService } from '../service/point-of-sale-membership-my-suffix.service';
import { IMembershipConfMySuffix } from 'app/entities/membership-conf-my-suffix/membership-conf-my-suffix.model';
import { MembershipConfMySuffixService } from 'app/entities/membership-conf-my-suffix/service/membership-conf-my-suffix.service';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { UtilisateurMySuffixService } from 'app/entities/utilisateur-my-suffix/service/utilisateur-my-suffix.service';
import { TypeBanque } from 'app/entities/enumerations/type-banque.model';

@Component({
  selector: 'jhi-point-of-sale-membership-my-suffix-update',
  templateUrl: './point-of-sale-membership-my-suffix-update.component.html',
})
export class PointOfSaleMembershipMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  typeBanqueValues = Object.keys(TypeBanque);

  membershipConfsSharedCollection: IMembershipConfMySuffix[] = [];
  utilisateursSharedCollection: IUtilisateurMySuffix[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    typePoint: [null, [Validators.required]],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
    membershipConf: [],
    utilisateur: [],
  });

  constructor(
    protected pointOfSaleMembershipService: PointOfSaleMembershipMySuffixService,
    protected membershipConfService: MembershipConfMySuffixService,
    protected utilisateurService: UtilisateurMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pointOfSaleMembership }) => {
      if (pointOfSaleMembership.id === undefined) {
        const today = dayjs().startOf('day');
        pointOfSaleMembership.createdDate = today;
        pointOfSaleMembership.lastModifiedDate = today;
      }

      this.updateForm(pointOfSaleMembership);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pointOfSaleMembership = this.createFromForm();
    if (pointOfSaleMembership.id !== undefined) {
      this.subscribeToSaveResponse(this.pointOfSaleMembershipService.update(pointOfSaleMembership));
    } else {
      this.subscribeToSaveResponse(this.pointOfSaleMembershipService.create(pointOfSaleMembership));
    }
  }

  trackMembershipConfMySuffixById(_index: number, item: IMembershipConfMySuffix): number {
    return item.id!;
  }

  trackUtilisateurMySuffixById(_index: number, item: IUtilisateurMySuffix): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPointOfSaleMembershipMySuffix>>): void {
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

  protected updateForm(pointOfSaleMembership: IPointOfSaleMembershipMySuffix): void {
    this.editForm.patchValue({
      id: pointOfSaleMembership.id,
      typePoint: pointOfSaleMembership.typePoint,
      createdBy: pointOfSaleMembership.createdBy,
      createdDate: pointOfSaleMembership.createdDate ? pointOfSaleMembership.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: pointOfSaleMembership.lastModifiedBy,
      lastModifiedDate: pointOfSaleMembership.lastModifiedDate ? pointOfSaleMembership.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      membershipConf: pointOfSaleMembership.membershipConf,
      utilisateur: pointOfSaleMembership.utilisateur,
    });

    this.membershipConfsSharedCollection = this.membershipConfService.addMembershipConfMySuffixToCollectionIfMissing(
      this.membershipConfsSharedCollection,
      pointOfSaleMembership.membershipConf
    );
    this.utilisateursSharedCollection = this.utilisateurService.addUtilisateurMySuffixToCollectionIfMissing(
      this.utilisateursSharedCollection,
      pointOfSaleMembership.utilisateur
    );
  }

  protected loadRelationshipsOptions(): void {
    this.membershipConfService
      .query()
      .pipe(map((res: HttpResponse<IMembershipConfMySuffix[]>) => res.body ?? []))
      .pipe(
        map((membershipConfs: IMembershipConfMySuffix[]) =>
          this.membershipConfService.addMembershipConfMySuffixToCollectionIfMissing(
            membershipConfs,
            this.editForm.get('membershipConf')!.value
          )
        )
      )
      .subscribe((membershipConfs: IMembershipConfMySuffix[]) => (this.membershipConfsSharedCollection = membershipConfs));

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

  protected createFromForm(): IPointOfSaleMembershipMySuffix {
    return {
      ...new PointOfSaleMembershipMySuffix(),
      id: this.editForm.get(['id'])!.value,
      typePoint: this.editForm.get(['typePoint'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? dayjs(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      membershipConf: this.editForm.get(['membershipConf'])!.value,
      utilisateur: this.editForm.get(['utilisateur'])!.value,
    };
  }
}
