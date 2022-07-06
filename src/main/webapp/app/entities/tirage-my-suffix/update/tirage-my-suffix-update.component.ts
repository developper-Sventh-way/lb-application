import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITirageMySuffix, TirageMySuffix } from '../tirage-my-suffix.model';
import { TirageMySuffixService } from '../service/tirage-my-suffix.service';
import { IBorletteConfMySuffix } from 'app/entities/borlette-conf-my-suffix/borlette-conf-my-suffix.model';
import { BorletteConfMySuffixService } from 'app/entities/borlette-conf-my-suffix/service/borlette-conf-my-suffix.service';
import { TirageName } from 'app/entities/enumerations/tirage-name.model';
import { TirageType } from 'app/entities/enumerations/tirage-type.model';
import { UserStatut } from 'app/entities/enumerations/user-statut.model';

@Component({
  selector: 'jhi-tirage-my-suffix-update',
  templateUrl: './tirage-my-suffix-update.component.html',
})
export class TirageMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  tirageNameValues = Object.keys(TirageName);
  tirageTypeValues = Object.keys(TirageType);
  userStatutValues = Object.keys(UserStatut);

  borletteConfsSharedCollection: IBorletteConfMySuffix[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    tirageName: [null, [Validators.required]],
    type: [null, [Validators.required]],
    premierLot: [null, [Validators.minLength(2), Validators.maxLength(2)]],
    deuxiemeLot: [null, [Validators.minLength(2), Validators.maxLength(2)]],
    troisiemeLot: [null, [Validators.minLength(2), Validators.maxLength(2)]],
    loto3Chif: [null, [Validators.minLength(2), Validators.maxLength(2)]],
    statut: [null, [Validators.required]],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
    borletteConf: [],
  });

  constructor(
    protected tirageService: TirageMySuffixService,
    protected borletteConfService: BorletteConfMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tirage }) => {
      if (tirage.id === undefined) {
        const today = dayjs().startOf('day');
        tirage.createdDate = today;
        tirage.lastModifiedDate = today;
      }

      this.updateForm(tirage);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tirage = this.createFromForm();
    if (tirage.id !== undefined) {
      this.subscribeToSaveResponse(this.tirageService.update(tirage));
    } else {
      this.subscribeToSaveResponse(this.tirageService.create(tirage));
    }
  }

  trackBorletteConfMySuffixById(_index: number, item: IBorletteConfMySuffix): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITirageMySuffix>>): void {
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

  protected updateForm(tirage: ITirageMySuffix): void {
    this.editForm.patchValue({
      id: tirage.id,
      tirageName: tirage.tirageName,
      type: tirage.type,
      premierLot: tirage.premierLot,
      deuxiemeLot: tirage.deuxiemeLot,
      troisiemeLot: tirage.troisiemeLot,
      loto3Chif: tirage.loto3Chif,
      statut: tirage.statut,
      createdBy: tirage.createdBy,
      createdDate: tirage.createdDate ? tirage.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: tirage.lastModifiedBy,
      lastModifiedDate: tirage.lastModifiedDate ? tirage.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      borletteConf: tirage.borletteConf,
    });

    this.borletteConfsSharedCollection = this.borletteConfService.addBorletteConfMySuffixToCollectionIfMissing(
      this.borletteConfsSharedCollection,
      tirage.borletteConf
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

  protected createFromForm(): ITirageMySuffix {
    return {
      ...new TirageMySuffix(),
      id: this.editForm.get(['id'])!.value,
      tirageName: this.editForm.get(['tirageName'])!.value,
      type: this.editForm.get(['type'])!.value,
      premierLot: this.editForm.get(['premierLot'])!.value,
      deuxiemeLot: this.editForm.get(['deuxiemeLot'])!.value,
      troisiemeLot: this.editForm.get(['troisiemeLot'])!.value,
      loto3Chif: this.editForm.get(['loto3Chif'])!.value,
      statut: this.editForm.get(['statut'])!.value,
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
