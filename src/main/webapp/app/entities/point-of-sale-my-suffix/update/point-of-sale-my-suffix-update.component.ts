import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPointOfSaleMySuffix, PointOfSaleMySuffix } from '../point-of-sale-my-suffix.model';
import { PointOfSaleMySuffixService } from '../service/point-of-sale-my-suffix.service';
import { IPointOfSaleConfMySuffix } from 'app/entities/point-of-sale-conf-my-suffix/point-of-sale-conf-my-suffix.model';
import { PointOfSaleConfMySuffixService } from 'app/entities/point-of-sale-conf-my-suffix/service/point-of-sale-conf-my-suffix.service';
import { StatutBanque } from 'app/entities/enumerations/statut-banque.model';

@Component({
  selector: 'jhi-point-of-sale-my-suffix-update',
  templateUrl: './point-of-sale-my-suffix-update.component.html',
})
export class PointOfSaleMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  statutBanqueValues = Object.keys(StatutBanque);

  pointOfSaleConfsCollection: IPointOfSaleConfMySuffix[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    adressePoint: [null, [Validators.minLength(4), Validators.maxLength(80)]],
    statut: [null, [Validators.required]],
    phone1: [null, [Validators.minLength(6), Validators.maxLength(30)]],
    phone2: [null, [Validators.minLength(6), Validators.maxLength(30)]],
    pourcentagePoint: [null, [Validators.required]],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
    pointOfSaleConf: [],
  });

  constructor(
    protected pointOfSaleService: PointOfSaleMySuffixService,
    protected pointOfSaleConfService: PointOfSaleConfMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pointOfSale }) => {
      if (pointOfSale.id === undefined) {
        const today = dayjs().startOf('day');
        pointOfSale.createdDate = today;
        pointOfSale.lastModifiedDate = today;
      }

      this.updateForm(pointOfSale);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pointOfSale = this.createFromForm();
    if (pointOfSale.id !== undefined) {
      this.subscribeToSaveResponse(this.pointOfSaleService.update(pointOfSale));
    } else {
      this.subscribeToSaveResponse(this.pointOfSaleService.create(pointOfSale));
    }
  }

  trackPointOfSaleConfMySuffixById(_index: number, item: IPointOfSaleConfMySuffix): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPointOfSaleMySuffix>>): void {
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

  protected updateForm(pointOfSale: IPointOfSaleMySuffix): void {
    this.editForm.patchValue({
      id: pointOfSale.id,
      adressePoint: pointOfSale.adressePoint,
      statut: pointOfSale.statut,
      phone1: pointOfSale.phone1,
      phone2: pointOfSale.phone2,
      pourcentagePoint: pointOfSale.pourcentagePoint,
      createdBy: pointOfSale.createdBy,
      createdDate: pointOfSale.createdDate ? pointOfSale.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: pointOfSale.lastModifiedBy,
      lastModifiedDate: pointOfSale.lastModifiedDate ? pointOfSale.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      pointOfSaleConf: pointOfSale.pointOfSaleConf,
    });

    this.pointOfSaleConfsCollection = this.pointOfSaleConfService.addPointOfSaleConfMySuffixToCollectionIfMissing(
      this.pointOfSaleConfsCollection,
      pointOfSale.pointOfSaleConf
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pointOfSaleConfService
      .query({ filter: 'pointofsale-is-null' })
      .pipe(map((res: HttpResponse<IPointOfSaleConfMySuffix[]>) => res.body ?? []))
      .pipe(
        map((pointOfSaleConfs: IPointOfSaleConfMySuffix[]) =>
          this.pointOfSaleConfService.addPointOfSaleConfMySuffixToCollectionIfMissing(
            pointOfSaleConfs,
            this.editForm.get('pointOfSaleConf')!.value
          )
        )
      )
      .subscribe((pointOfSaleConfs: IPointOfSaleConfMySuffix[]) => (this.pointOfSaleConfsCollection = pointOfSaleConfs));
  }

  protected createFromForm(): IPointOfSaleMySuffix {
    return {
      ...new PointOfSaleMySuffix(),
      id: this.editForm.get(['id'])!.value,
      adressePoint: this.editForm.get(['adressePoint'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      phone1: this.editForm.get(['phone1'])!.value,
      phone2: this.editForm.get(['phone2'])!.value,
      pourcentagePoint: this.editForm.get(['pourcentagePoint'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? dayjs(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      pointOfSaleConf: this.editForm.get(['pointOfSaleConf'])!.value,
    };
  }
}
