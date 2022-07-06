import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISystemTraceMySuffix, SystemTraceMySuffix } from '../system-trace-my-suffix.model';
import { SystemTraceMySuffixService } from '../service/system-trace-my-suffix.service';

@Component({
  selector: 'jhi-system-trace-my-suffix-update',
  templateUrl: './system-trace-my-suffix-update.component.html',
})
export class SystemTraceMySuffixUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    traceContenu: [null, [Validators.required, Validators.maxLength(100)]],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
  });

  constructor(
    protected systemTraceService: SystemTraceMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ systemTrace }) => {
      if (systemTrace.id === undefined) {
        const today = dayjs().startOf('day');
        systemTrace.createdDate = today;
        systemTrace.lastModifiedDate = today;
      }

      this.updateForm(systemTrace);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const systemTrace = this.createFromForm();
    if (systemTrace.id !== undefined) {
      this.subscribeToSaveResponse(this.systemTraceService.update(systemTrace));
    } else {
      this.subscribeToSaveResponse(this.systemTraceService.create(systemTrace));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISystemTraceMySuffix>>): void {
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

  protected updateForm(systemTrace: ISystemTraceMySuffix): void {
    this.editForm.patchValue({
      id: systemTrace.id,
      traceContenu: systemTrace.traceContenu,
      createdBy: systemTrace.createdBy,
      createdDate: systemTrace.createdDate ? systemTrace.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: systemTrace.lastModifiedBy,
      lastModifiedDate: systemTrace.lastModifiedDate ? systemTrace.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISystemTraceMySuffix {
    return {
      ...new SystemTraceMySuffix(),
      id: this.editForm.get(['id'])!.value,
      traceContenu: this.editForm.get(['traceContenu'])!.value,
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
