import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUserPaymentConfMySuffix, UserPaymentConfMySuffix } from '../user-payment-conf-my-suffix.model';
import { UserPaymentConfMySuffixService } from '../service/user-payment-conf-my-suffix.service';
import { UserPaymentType } from 'app/entities/enumerations/user-payment-type.model';

@Component({
  selector: 'jhi-user-payment-conf-my-suffix-update',
  templateUrl: './user-payment-conf-my-suffix-update.component.html',
})
export class UserPaymentConfMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  userPaymentTypeValues = Object.keys(UserPaymentType);

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    type: [null, [Validators.required]],
    total: [null, [Validators.required]],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
  });

  constructor(
    protected userPaymentConfService: UserPaymentConfMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userPaymentConf }) => {
      if (userPaymentConf.id === undefined) {
        const today = dayjs().startOf('day');
        userPaymentConf.createdDate = today;
        userPaymentConf.lastModifiedDate = today;
      }

      this.updateForm(userPaymentConf);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userPaymentConf = this.createFromForm();
    if (userPaymentConf.id !== undefined) {
      this.subscribeToSaveResponse(this.userPaymentConfService.update(userPaymentConf));
    } else {
      this.subscribeToSaveResponse(this.userPaymentConfService.create(userPaymentConf));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserPaymentConfMySuffix>>): void {
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

  protected updateForm(userPaymentConf: IUserPaymentConfMySuffix): void {
    this.editForm.patchValue({
      id: userPaymentConf.id,
      type: userPaymentConf.type,
      total: userPaymentConf.total,
      createdBy: userPaymentConf.createdBy,
      createdDate: userPaymentConf.createdDate ? userPaymentConf.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: userPaymentConf.lastModifiedBy,
      lastModifiedDate: userPaymentConf.lastModifiedDate ? userPaymentConf.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IUserPaymentConfMySuffix {
    return {
      ...new UserPaymentConfMySuffix(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      total: this.editForm.get(['total'])!.value,
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
