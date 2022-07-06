import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITransactionHistoriesMySuffix, TransactionHistoriesMySuffix } from '../transaction-histories-my-suffix.model';
import { TransactionHistoriesMySuffixService } from '../service/transaction-histories-my-suffix.service';
import { TransactionType } from 'app/entities/enumerations/transaction-type.model';

@Component({
  selector: 'jhi-transaction-histories-my-suffix-update',
  templateUrl: './transaction-histories-my-suffix-update.component.html',
})
export class TransactionHistoriesMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  transactionTypeValues = Object.keys(TransactionType);

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    type: [null, [Validators.required]],
    description: [null, [Validators.maxLength(60)]],
    montant: [null, [Validators.required]],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
  });

  constructor(
    protected transactionHistoriesService: TransactionHistoriesMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionHistories }) => {
      if (transactionHistories.id === undefined) {
        const today = dayjs().startOf('day');
        transactionHistories.createdDate = today;
        transactionHistories.lastModifiedDate = today;
      }

      this.updateForm(transactionHistories);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactionHistories = this.createFromForm();
    if (transactionHistories.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionHistoriesService.update(transactionHistories));
    } else {
      this.subscribeToSaveResponse(this.transactionHistoriesService.create(transactionHistories));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionHistoriesMySuffix>>): void {
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

  protected updateForm(transactionHistories: ITransactionHistoriesMySuffix): void {
    this.editForm.patchValue({
      id: transactionHistories.id,
      type: transactionHistories.type,
      description: transactionHistories.description,
      montant: transactionHistories.montant,
      createdBy: transactionHistories.createdBy,
      createdDate: transactionHistories.createdDate ? transactionHistories.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: transactionHistories.lastModifiedBy,
      lastModifiedDate: transactionHistories.lastModifiedDate ? transactionHistories.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ITransactionHistoriesMySuffix {
    return {
      ...new TransactionHistoriesMySuffix(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      description: this.editForm.get(['description'])!.value,
      montant: this.editForm.get(['montant'])!.value,
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
