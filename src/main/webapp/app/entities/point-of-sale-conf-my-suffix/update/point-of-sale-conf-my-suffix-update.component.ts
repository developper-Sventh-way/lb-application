import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPointOfSaleConfMySuffix, PointOfSaleConfMySuffix } from '../point-of-sale-conf-my-suffix.model';
import { PointOfSaleConfMySuffixService } from '../service/point-of-sale-conf-my-suffix.service';

@Component({
  selector: 'jhi-point-of-sale-conf-my-suffix-update',
  templateUrl: './point-of-sale-conf-my-suffix-update.component.html',
})
export class PointOfSaleConfMySuffixUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    pourcentageCaissier: [],
    pourcentageResponsable: [],
    startTime: [null, [Validators.minLength(5), Validators.maxLength(5)]],
    endTime: [null, [Validators.minLength(5), Validators.maxLength(5)]],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
  });

  constructor(
    protected pointOfSaleConfService: PointOfSaleConfMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pointOfSaleConf }) => {
      if (pointOfSaleConf.id === undefined) {
        const today = dayjs().startOf('day');
        pointOfSaleConf.createdDate = today;
        pointOfSaleConf.lastModifiedDate = today;
      }

      this.updateForm(pointOfSaleConf);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pointOfSaleConf = this.createFromForm();
    if (pointOfSaleConf.id !== undefined) {
      this.subscribeToSaveResponse(this.pointOfSaleConfService.update(pointOfSaleConf));
    } else {
      this.subscribeToSaveResponse(this.pointOfSaleConfService.create(pointOfSaleConf));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPointOfSaleConfMySuffix>>): void {
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

  protected updateForm(pointOfSaleConf: IPointOfSaleConfMySuffix): void {
    this.editForm.patchValue({
      id: pointOfSaleConf.id,
      pourcentageCaissier: pointOfSaleConf.pourcentageCaissier,
      pourcentageResponsable: pointOfSaleConf.pourcentageResponsable,
      startTime: pointOfSaleConf.startTime,
      endTime: pointOfSaleConf.endTime,
      createdBy: pointOfSaleConf.createdBy,
      createdDate: pointOfSaleConf.createdDate ? pointOfSaleConf.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: pointOfSaleConf.lastModifiedBy,
      lastModifiedDate: pointOfSaleConf.lastModifiedDate ? pointOfSaleConf.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IPointOfSaleConfMySuffix {
    return {
      ...new PointOfSaleConfMySuffix(),
      id: this.editForm.get(['id'])!.value,
      pourcentageCaissier: this.editForm.get(['pourcentageCaissier'])!.value,
      pourcentageResponsable: this.editForm.get(['pourcentageResponsable'])!.value,
      startTime: this.editForm.get(['startTime'])!.value,
      endTime: this.editForm.get(['endTime'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? dayjs(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
