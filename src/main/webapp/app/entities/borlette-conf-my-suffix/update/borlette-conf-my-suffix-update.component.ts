import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBorletteConfMySuffix, BorletteConfMySuffix } from '../borlette-conf-my-suffix.model';
import { BorletteConfMySuffixService } from '../service/borlette-conf-my-suffix.service';
import { TirageName } from 'app/entities/enumerations/tirage-name.model';

@Component({
  selector: 'jhi-borlette-conf-my-suffix-update',
  templateUrl: './borlette-conf-my-suffix-update.component.html',
})
export class BorletteConfMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  tirageNameValues = Object.keys(TirageName);

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    name: [null, [Validators.required]],
    premierLot: [null, [Validators.required]],
    deuxiemeLot: [null, [Validators.required]],
    troisiemeLot: [null, [Validators.required]],
    mariageGratisPrix: [],
    montantMinimum: [null, [Validators.required]],
    montantMaximum: [null, [Validators.required]],
    closeTimeMidi: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(5)]],
    closeTimeSoir: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(5)]],
  });

  constructor(
    protected borletteConfService: BorletteConfMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ borletteConf }) => {
      this.updateForm(borletteConf);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const borletteConf = this.createFromForm();
    if (borletteConf.id !== undefined) {
      this.subscribeToSaveResponse(this.borletteConfService.update(borletteConf));
    } else {
      this.subscribeToSaveResponse(this.borletteConfService.create(borletteConf));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBorletteConfMySuffix>>): void {
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

  protected updateForm(borletteConf: IBorletteConfMySuffix): void {
    this.editForm.patchValue({
      id: borletteConf.id,
      name: borletteConf.name,
      premierLot: borletteConf.premierLot,
      deuxiemeLot: borletteConf.deuxiemeLot,
      troisiemeLot: borletteConf.troisiemeLot,
      mariageGratisPrix: borletteConf.mariageGratisPrix,
      montantMinimum: borletteConf.montantMinimum,
      montantMaximum: borletteConf.montantMaximum,
      closeTimeMidi: borletteConf.closeTimeMidi,
      closeTimeSoir: borletteConf.closeTimeSoir,
    });
  }

  protected createFromForm(): IBorletteConfMySuffix {
    return {
      ...new BorletteConfMySuffix(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      premierLot: this.editForm.get(['premierLot'])!.value,
      deuxiemeLot: this.editForm.get(['deuxiemeLot'])!.value,
      troisiemeLot: this.editForm.get(['troisiemeLot'])!.value,
      mariageGratisPrix: this.editForm.get(['mariageGratisPrix'])!.value,
      montantMinimum: this.editForm.get(['montantMinimum'])!.value,
      montantMaximum: this.editForm.get(['montantMaximum'])!.value,
      closeTimeMidi: this.editForm.get(['closeTimeMidi'])!.value,
      closeTimeSoir: this.editForm.get(['closeTimeSoir'])!.value,
    };
  }
}
