import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPOSConfigurationMySuffix, POSConfigurationMySuffix } from '../pos-configuration-my-suffix.model';
import { POSConfigurationMySuffixService } from '../service/pos-configuration-my-suffix.service';
import { DeviceStatut } from 'app/entities/enumerations/device-statut.model';

@Component({
  selector: 'jhi-pos-configuration-my-suffix-update',
  templateUrl: './pos-configuration-my-suffix-update.component.html',
})
export class POSConfigurationMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  deviceStatutValues = Object.keys(DeviceStatut);

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    pOSName: [null, [Validators.required, Validators.maxLength(50)]],
    iMEI: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(60)]],
    deviceStatut: [null, [Validators.required]],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
  });

  constructor(
    protected pOSConfigurationService: POSConfigurationMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pOSConfiguration }) => {
      if (pOSConfiguration.id === undefined) {
        const today = dayjs().startOf('day');
        pOSConfiguration.createdDate = today;
        pOSConfiguration.lastModifiedDate = today;
      }

      this.updateForm(pOSConfiguration);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pOSConfiguration = this.createFromForm();
    if (pOSConfiguration.id !== undefined) {
      this.subscribeToSaveResponse(this.pOSConfigurationService.update(pOSConfiguration));
    } else {
      this.subscribeToSaveResponse(this.pOSConfigurationService.create(pOSConfiguration));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPOSConfigurationMySuffix>>): void {
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

  protected updateForm(pOSConfiguration: IPOSConfigurationMySuffix): void {
    this.editForm.patchValue({
      id: pOSConfiguration.id,
      pOSName: pOSConfiguration.pOSName,
      iMEI: pOSConfiguration.iMEI,
      deviceStatut: pOSConfiguration.deviceStatut,
      createdBy: pOSConfiguration.createdBy,
      createdDate: pOSConfiguration.createdDate ? pOSConfiguration.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: pOSConfiguration.lastModifiedBy,
      lastModifiedDate: pOSConfiguration.lastModifiedDate ? pOSConfiguration.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IPOSConfigurationMySuffix {
    return {
      ...new POSConfigurationMySuffix(),
      id: this.editForm.get(['id'])!.value,
      pOSName: this.editForm.get(['pOSName'])!.value,
      iMEI: this.editForm.get(['iMEI'])!.value,
      deviceStatut: this.editForm.get(['deviceStatut'])!.value,
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
