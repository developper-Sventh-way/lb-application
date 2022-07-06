import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ILimitConfBorletteMySuffix, LimitConfBorletteMySuffix } from '../limit-conf-borlette-my-suffix.model';
import { LimitConfBorletteMySuffixService } from '../service/limit-conf-borlette-my-suffix.service';
import { IBorletteConfMySuffix } from 'app/entities/borlette-conf-my-suffix/borlette-conf-my-suffix.model';
import { BorletteConfMySuffixService } from 'app/entities/borlette-conf-my-suffix/service/borlette-conf-my-suffix.service';
import { TypeOption } from 'app/entities/enumerations/type-option.model';

@Component({
  selector: 'jhi-limit-conf-borlette-my-suffix-update',
  templateUrl: './limit-conf-borlette-my-suffix-update.component.html',
})
export class LimitConfBorletteMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  typeOptionValues = Object.keys(TypeOption);

  borletteConfsSharedCollection: IBorletteConfMySuffix[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    nombreValue: [null, [Validators.minLength(2), Validators.maxLength(5)]],
    limit: [null, [Validators.required]],
    limitStatut: [],
    createdBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
    borletteConf: [],
  });

  constructor(
    protected limitConfBorletteService: LimitConfBorletteMySuffixService,
    protected borletteConfService: BorletteConfMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ limitConfBorlette }) => {
      if (limitConfBorlette.id === undefined) {
        const today = dayjs().startOf('day');
        limitConfBorlette.createdDate = today;
        limitConfBorlette.lastModifiedDate = today;
      }

      this.updateForm(limitConfBorlette);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const limitConfBorlette = this.createFromForm();
    if (limitConfBorlette.id !== undefined) {
      this.subscribeToSaveResponse(this.limitConfBorletteService.update(limitConfBorlette));
    } else {
      this.subscribeToSaveResponse(this.limitConfBorletteService.create(limitConfBorlette));
    }
  }

  trackBorletteConfMySuffixById(_index: number, item: IBorletteConfMySuffix): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILimitConfBorletteMySuffix>>): void {
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

  protected updateForm(limitConfBorlette: ILimitConfBorletteMySuffix): void {
    this.editForm.patchValue({
      id: limitConfBorlette.id,
      nombreValue: limitConfBorlette.nombreValue,
      limit: limitConfBorlette.limit,
      limitStatut: limitConfBorlette.limitStatut,
      createdBy: limitConfBorlette.createdBy,
      createdDate: limitConfBorlette.createdDate ? limitConfBorlette.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: limitConfBorlette.lastModifiedBy,
      lastModifiedDate: limitConfBorlette.lastModifiedDate ? limitConfBorlette.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      borletteConf: limitConfBorlette.borletteConf,
    });

    this.borletteConfsSharedCollection = this.borletteConfService.addBorletteConfMySuffixToCollectionIfMissing(
      this.borletteConfsSharedCollection,
      limitConfBorlette.borletteConf
    );
  }

  protected loadRelationshipsOptions(): void {
    this.borletteConfService
      .query()
      .pipe(map((res: HttpResponse<IBorletteConfMySuffix[]>) => res.body ?? []))
      .pipe(
        map((borletteConfs: IBorletteConfMySuffix[]) =>
          this.borletteConfService.addBorletteConfMySuffixToCollectionIfMissing(borletteConfs, this.editForm.get('borletteConf')!.value)
        )
      )
      .subscribe((borletteConfs: IBorletteConfMySuffix[]) => (this.borletteConfsSharedCollection = borletteConfs));
  }

  protected createFromForm(): ILimitConfBorletteMySuffix {
    return {
      ...new LimitConfBorletteMySuffix(),
      id: this.editForm.get(['id'])!.value,
      nombreValue: this.editForm.get(['nombreValue'])!.value,
      limit: this.editForm.get(['limit'])!.value,
      limitStatut: this.editForm.get(['limitStatut'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? dayjs(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      borletteConf: this.editForm.get(['borletteConf'])!.value,
    };
  }
}
