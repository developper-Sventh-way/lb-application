import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICouponGratuitConfMySuffix, CouponGratuitConfMySuffix } from '../coupon-gratuit-conf-my-suffix.model';
import { CouponGratuitConfMySuffixService } from '../service/coupon-gratuit-conf-my-suffix.service';
import { TypeOption } from 'app/entities/enumerations/type-option.model';
import { UserStatut } from 'app/entities/enumerations/user-statut.model';

@Component({
  selector: 'jhi-coupon-gratuit-conf-my-suffix-update',
  templateUrl: './coupon-gratuit-conf-my-suffix-update.component.html',
})
export class CouponGratuitConfMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  typeOptionValues = Object.keys(TypeOption);
  userStatutValues = Object.keys(UserStatut);

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    typeOption: [null, [Validators.required]],
    maximumCount: [null, [Validators.required]],
    obstinateAmount: [null, [Validators.required]],
    statut: [null, [Validators.required]],
    createdBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
  });

  constructor(
    protected couponGratuitConfService: CouponGratuitConfMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ couponGratuitConf }) => {
      if (couponGratuitConf.id === undefined) {
        const today = dayjs().startOf('day');
        couponGratuitConf.createdDate = today;
        couponGratuitConf.lastModifiedDate = today;
      }

      this.updateForm(couponGratuitConf);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const couponGratuitConf = this.createFromForm();
    if (couponGratuitConf.id !== undefined) {
      this.subscribeToSaveResponse(this.couponGratuitConfService.update(couponGratuitConf));
    } else {
      this.subscribeToSaveResponse(this.couponGratuitConfService.create(couponGratuitConf));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICouponGratuitConfMySuffix>>): void {
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

  protected updateForm(couponGratuitConf: ICouponGratuitConfMySuffix): void {
    this.editForm.patchValue({
      id: couponGratuitConf.id,
      typeOption: couponGratuitConf.typeOption,
      maximumCount: couponGratuitConf.maximumCount,
      obstinateAmount: couponGratuitConf.obstinateAmount,
      statut: couponGratuitConf.statut,
      createdBy: couponGratuitConf.createdBy,
      createdDate: couponGratuitConf.createdDate ? couponGratuitConf.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: couponGratuitConf.lastModifiedBy,
      lastModifiedDate: couponGratuitConf.lastModifiedDate ? couponGratuitConf.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ICouponGratuitConfMySuffix {
    return {
      ...new CouponGratuitConfMySuffix(),
      id: this.editForm.get(['id'])!.value,
      typeOption: this.editForm.get(['typeOption'])!.value,
      maximumCount: this.editForm.get(['maximumCount'])!.value,
      obstinateAmount: this.editForm.get(['obstinateAmount'])!.value,
      statut: this.editForm.get(['statut'])!.value,
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
