import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILimitConfPointMySuffix, LimitConfPointMySuffix } from '../limit-conf-point-my-suffix.model';
import { LimitConfPointMySuffixService } from '../service/limit-conf-point-my-suffix.service';
import { IPointOfSaleMySuffix } from 'app/entities/point-of-sale-my-suffix/point-of-sale-my-suffix.model';
import { PointOfSaleMySuffixService } from 'app/entities/point-of-sale-my-suffix/service/point-of-sale-my-suffix.service';
import { TypeOption } from 'app/entities/enumerations/type-option.model';

@Component({
  selector: 'jhi-limit-conf-point-my-suffix-update',
  templateUrl: './limit-conf-point-my-suffix-update.component.html',
})
export class LimitConfPointMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  typeOptionValues = Object.keys(TypeOption);

  pointOfSalesSharedCollection: IPointOfSaleMySuffix[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    nombreValue: [null, [Validators.minLength(2), Validators.maxLength(5)]],
    limit: [null, [Validators.required]],
    limitStatut: [],
    pointOfSale: [],
  });

  constructor(
    protected limitConfPointService: LimitConfPointMySuffixService,
    protected pointOfSaleService: PointOfSaleMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ limitConfPoint }) => {
      this.updateForm(limitConfPoint);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const limitConfPoint = this.createFromForm();
    if (limitConfPoint.id !== undefined) {
      this.subscribeToSaveResponse(this.limitConfPointService.update(limitConfPoint));
    } else {
      this.subscribeToSaveResponse(this.limitConfPointService.create(limitConfPoint));
    }
  }

  trackPointOfSaleMySuffixById(_index: number, item: IPointOfSaleMySuffix): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILimitConfPointMySuffix>>): void {
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

  protected updateForm(limitConfPoint: ILimitConfPointMySuffix): void {
    this.editForm.patchValue({
      id: limitConfPoint.id,
      nombreValue: limitConfPoint.nombreValue,
      limit: limitConfPoint.limit,
      limitStatut: limitConfPoint.limitStatut,
      pointOfSale: limitConfPoint.pointOfSale,
    });

    this.pointOfSalesSharedCollection = this.pointOfSaleService.addPointOfSaleMySuffixToCollectionIfMissing(
      this.pointOfSalesSharedCollection,
      limitConfPoint.pointOfSale
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
  }

  protected createFromForm(): ILimitConfPointMySuffix {
    return {
      ...new LimitConfPointMySuffix(),
      id: this.editForm.get(['id'])!.value,
      nombreValue: this.editForm.get(['nombreValue'])!.value,
      limit: this.editForm.get(['limit'])!.value,
      limitStatut: this.editForm.get(['limitStatut'])!.value,
      pointOfSale: this.editForm.get(['pointOfSale'])!.value,
    };
  }
}
