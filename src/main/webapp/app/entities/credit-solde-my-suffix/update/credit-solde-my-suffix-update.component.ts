import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICreditSoldeMySuffix, CreditSoldeMySuffix } from '../credit-solde-my-suffix.model';
import { CreditSoldeMySuffixService } from '../service/credit-solde-my-suffix.service';

@Component({
  selector: 'jhi-credit-solde-my-suffix-update',
  templateUrl: './credit-solde-my-suffix-update.component.html',
})
export class CreditSoldeMySuffixUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    montant: [null, [Validators.required]],
    createdBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
  });

  constructor(
    protected creditSoldeService: CreditSoldeMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ creditSolde }) => {
      if (creditSolde.id === undefined) {
        const today = dayjs().startOf('day');
        creditSolde.createdDate = today;
        creditSolde.lastModifiedDate = today;
      }

      this.updateForm(creditSolde);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const creditSolde = this.createFromForm();
    if (creditSolde.id !== undefined) {
      this.subscribeToSaveResponse(this.creditSoldeService.update(creditSolde));
    } else {
      this.subscribeToSaveResponse(this.creditSoldeService.create(creditSolde));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICreditSoldeMySuffix>>): void {
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

  protected updateForm(creditSolde: ICreditSoldeMySuffix): void {
    this.editForm.patchValue({
      id: creditSolde.id,
      montant: creditSolde.montant,
      createdBy: creditSolde.createdBy,
      createdDate: creditSolde.createdDate ? creditSolde.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: creditSolde.lastModifiedBy,
      lastModifiedDate: creditSolde.lastModifiedDate ? creditSolde.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ICreditSoldeMySuffix {
    return {
      ...new CreditSoldeMySuffix(),
      id: this.editForm.get(['id'])!.value,
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
