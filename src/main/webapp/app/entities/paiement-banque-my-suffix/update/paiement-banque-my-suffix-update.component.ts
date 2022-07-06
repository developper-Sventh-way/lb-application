import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPaiementBanqueMySuffix, PaiementBanqueMySuffix } from '../paiement-banque-my-suffix.model';
import { PaiementBanqueMySuffixService } from '../service/paiement-banque-my-suffix.service';
import { IPointOfSaleMySuffix } from 'app/entities/point-of-sale-my-suffix/point-of-sale-my-suffix.model';
import { PointOfSaleMySuffixService } from 'app/entities/point-of-sale-my-suffix/service/point-of-sale-my-suffix.service';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { UtilisateurMySuffixService } from 'app/entities/utilisateur-my-suffix/service/utilisateur-my-suffix.service';

@Component({
  selector: 'jhi-paiement-banque-my-suffix-update',
  templateUrl: './paiement-banque-my-suffix-update.component.html',
})
export class PaiementBanqueMySuffixUpdateComponent implements OnInit {
  isSaving = false;

  pointOfSalesSharedCollection: IPointOfSaleMySuffix[] = [];
  utilisateursSharedCollection: IUtilisateurMySuffix[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    montant: [null, [Validators.required]],
    balance: [null, [Validators.required]],
    description: [null, [Validators.maxLength(120)]],
    startDate: [],
    endDate: [],
    createdBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
    pointOfSale: [],
    utilisateur: [],
  });

  constructor(
    protected paiementBanqueService: PaiementBanqueMySuffixService,
    protected pointOfSaleService: PointOfSaleMySuffixService,
    protected utilisateurService: UtilisateurMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paiementBanque }) => {
      if (paiementBanque.id === undefined) {
        const today = dayjs().startOf('day');
        paiementBanque.createdDate = today;
        paiementBanque.lastModifiedDate = today;
      }

      this.updateForm(paiementBanque);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paiementBanque = this.createFromForm();
    if (paiementBanque.id !== undefined) {
      this.subscribeToSaveResponse(this.paiementBanqueService.update(paiementBanque));
    } else {
      this.subscribeToSaveResponse(this.paiementBanqueService.create(paiementBanque));
    }
  }

  trackPointOfSaleMySuffixById(_index: number, item: IPointOfSaleMySuffix): number {
    return item.id!;
  }

  trackUtilisateurMySuffixById(_index: number, item: IUtilisateurMySuffix): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaiementBanqueMySuffix>>): void {
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

  protected updateForm(paiementBanque: IPaiementBanqueMySuffix): void {
    this.editForm.patchValue({
      id: paiementBanque.id,
      montant: paiementBanque.montant,
      balance: paiementBanque.balance,
      description: paiementBanque.description,
      startDate: paiementBanque.startDate,
      endDate: paiementBanque.endDate,
      createdBy: paiementBanque.createdBy,
      createdDate: paiementBanque.createdDate ? paiementBanque.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: paiementBanque.lastModifiedBy,
      lastModifiedDate: paiementBanque.lastModifiedDate ? paiementBanque.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      pointOfSale: paiementBanque.pointOfSale,
      utilisateur: paiementBanque.utilisateur,
    });

    this.pointOfSalesSharedCollection = this.pointOfSaleService.addPointOfSaleMySuffixToCollectionIfMissing(
      this.pointOfSalesSharedCollection,
      paiementBanque.pointOfSale
    );
    this.utilisateursSharedCollection = this.utilisateurService.addUtilisateurMySuffixToCollectionIfMissing(
      this.utilisateursSharedCollection,
      paiementBanque.utilisateur
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pointOfSaleService
      .query()
      .pipe(map((res: HttpResponse<IPointOfSaleMySuffix[]>) => res.body ?? []))
      .pipe(
        map((pointOfSales: IPointOfSaleMySuffix[]) =>
          this.pointOfSaleService.addPointOfSaleMySuffixToCollectionIfMissing(pointOfSales, this.editForm.get('pointOfSale')!.value)
        )
      )
      .subscribe((pointOfSales: IPointOfSaleMySuffix[]) => (this.pointOfSalesSharedCollection = pointOfSales));

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

  protected createFromForm(): IPaiementBanqueMySuffix {
    return {
      ...new PaiementBanqueMySuffix(),
      id: this.editForm.get(['id'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      description: this.editForm.get(['description'])!.value,
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
      pointOfSale: this.editForm.get(['pointOfSale'])!.value,
      utilisateur: this.editForm.get(['utilisateur'])!.value,
    };
  }
}
