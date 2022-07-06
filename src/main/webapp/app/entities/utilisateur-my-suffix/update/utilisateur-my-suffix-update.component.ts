import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUtilisateurMySuffix, UtilisateurMySuffix } from '../utilisateur-my-suffix.model';
import { UtilisateurMySuffixService } from '../service/utilisateur-my-suffix.service';
import { ICreditSoldeMySuffix } from 'app/entities/credit-solde-my-suffix/credit-solde-my-suffix.model';
import { CreditSoldeMySuffixService } from 'app/entities/credit-solde-my-suffix/service/credit-solde-my-suffix.service';
import { IUserPaymentConfMySuffix } from 'app/entities/user-payment-conf-my-suffix/user-payment-conf-my-suffix.model';
import { UserPaymentConfMySuffixService } from 'app/entities/user-payment-conf-my-suffix/service/user-payment-conf-my-suffix.service';
import { IPointOfSaleMySuffix } from 'app/entities/point-of-sale-my-suffix/point-of-sale-my-suffix.model';
import { PointOfSaleMySuffixService } from 'app/entities/point-of-sale-my-suffix/service/point-of-sale-my-suffix.service';
import { TypeUser } from 'app/entities/enumerations/type-user.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { UserStatut } from 'app/entities/enumerations/user-statut.model';

@Component({
  selector: 'jhi-utilisateur-my-suffix-update',
  templateUrl: './utilisateur-my-suffix-update.component.html',
})
export class UtilisateurMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  typeUserValues = Object.keys(TypeUser);
  sexeValues = Object.keys(Sexe);
  userStatutValues = Object.keys(UserStatut);

  creditSoldesCollection: ICreditSoldeMySuffix[] = [];
  userPaymentConfsCollection: IUserPaymentConfMySuffix[] = [];
  pointOfSalesSharedCollection: IPointOfSaleMySuffix[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    userName: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    password: [null, [Validators.required, Validators.maxLength(100)]],
    typeUser: [null, [Validators.required]],
    nom: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(40)]],
    prenom: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(40)]],
    sexe: [null, [Validators.required]],
    nifOuCin: [null, [Validators.maxLength(20)]],
    statut: [null, [Validators.required]],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
    creditSolde: [],
    userPaymentConf: [],
    pointOfSale: [],
  });

  constructor(
    protected utilisateurService: UtilisateurMySuffixService,
    protected creditSoldeService: CreditSoldeMySuffixService,
    protected userPaymentConfService: UserPaymentConfMySuffixService,
    protected pointOfSaleService: PointOfSaleMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ utilisateur }) => {
      if (utilisateur.id === undefined) {
        const today = dayjs().startOf('day');
        utilisateur.createdDate = today;
        utilisateur.lastModifiedDate = today;
      }

      this.updateForm(utilisateur);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const utilisateur = this.createFromForm();
    if (utilisateur.id !== undefined) {
      this.subscribeToSaveResponse(this.utilisateurService.update(utilisateur));
    } else {
      this.subscribeToSaveResponse(this.utilisateurService.create(utilisateur));
    }
  }

  trackCreditSoldeMySuffixById(_index: number, item: ICreditSoldeMySuffix): number {
    return item.id!;
  }

  trackUserPaymentConfMySuffixById(_index: number, item: IUserPaymentConfMySuffix): number {
    return item.id!;
  }

  trackPointOfSaleMySuffixById(_index: number, item: IPointOfSaleMySuffix): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUtilisateurMySuffix>>): void {
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

  protected updateForm(utilisateur: IUtilisateurMySuffix): void {
    this.editForm.patchValue({
      id: utilisateur.id,
      userName: utilisateur.userName,
      password: utilisateur.password,
      typeUser: utilisateur.typeUser,
      nom: utilisateur.nom,
      prenom: utilisateur.prenom,
      sexe: utilisateur.sexe,
      nifOuCin: utilisateur.nifOuCin,
      statut: utilisateur.statut,
      createdBy: utilisateur.createdBy,
      createdDate: utilisateur.createdDate ? utilisateur.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: utilisateur.lastModifiedBy,
      lastModifiedDate: utilisateur.lastModifiedDate ? utilisateur.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      creditSolde: utilisateur.creditSolde,
      userPaymentConf: utilisateur.userPaymentConf,
      pointOfSale: utilisateur.pointOfSale,
    });

    this.creditSoldesCollection = this.creditSoldeService.addCreditSoldeMySuffixToCollectionIfMissing(
      this.creditSoldesCollection,
      utilisateur.creditSolde
    );
    this.userPaymentConfsCollection = this.userPaymentConfService.addUserPaymentConfMySuffixToCollectionIfMissing(
      this.userPaymentConfsCollection,
      utilisateur.userPaymentConf
    );
    this.pointOfSalesSharedCollection = this.pointOfSaleService.addPointOfSaleMySuffixToCollectionIfMissing(
      this.pointOfSalesSharedCollection,
      utilisateur.pointOfSale
    );
  }

  protected loadRelationshipsOptions(): void {
    this.creditSoldeService
      .query({ filter: 'utilisateur-is-null' })
      .pipe(map((res: HttpResponse<ICreditSoldeMySuffix[]>) => res.body ?? []))
      .pipe(
        map((creditSoldes: ICreditSoldeMySuffix[]) =>
          this.creditSoldeService.addCreditSoldeMySuffixToCollectionIfMissing(creditSoldes, this.editForm.get('creditSolde')!.value)
        )
      )
      .subscribe((creditSoldes: ICreditSoldeMySuffix[]) => (this.creditSoldesCollection = creditSoldes));

    this.userPaymentConfService
      .query({ filter: 'utilisateur-is-null' })
      .pipe(map((res: HttpResponse<IUserPaymentConfMySuffix[]>) => res.body ?? []))
      .pipe(
        map((userPaymentConfs: IUserPaymentConfMySuffix[]) =>
          this.userPaymentConfService.addUserPaymentConfMySuffixToCollectionIfMissing(
            userPaymentConfs,
            this.editForm.get('userPaymentConf')!.value
          )
        )
      )
      .subscribe((userPaymentConfs: IUserPaymentConfMySuffix[]) => (this.userPaymentConfsCollection = userPaymentConfs));

    this.pointOfSaleService
      .query()
      .pipe(map((res: HttpResponse<IPointOfSaleMySuffix[]>) => res.body ?? []))
      .pipe(
        map((pointOfSales: IPointOfSaleMySuffix[]) =>
          this.pointOfSaleService.addPointOfSaleMySuffixToCollectionIfMissing(pointOfSales, this.editForm.get('pointOfSale')!.value)
        )
      )
      .subscribe((pointOfSales: IPointOfSaleMySuffix[]) => (this.pointOfSalesSharedCollection = pointOfSales));
  }

  protected createFromForm(): IUtilisateurMySuffix {
    return {
      ...new UtilisateurMySuffix(),
      id: this.editForm.get(['id'])!.value,
      userName: this.editForm.get(['userName'])!.value,
      password: this.editForm.get(['password'])!.value,
      typeUser: this.editForm.get(['typeUser'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      nifOuCin: this.editForm.get(['nifOuCin'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? dayjs(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      creditSolde: this.editForm.get(['creditSolde'])!.value,
      userPaymentConf: this.editForm.get(['userPaymentConf'])!.value,
      pointOfSale: this.editForm.get(['pointOfSale'])!.value,
    };
  }
}
