import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IMembershipConfMySuffix, MembershipConfMySuffix } from '../membership-conf-my-suffix.model';
import { MembershipConfMySuffixService } from '../service/membership-conf-my-suffix.service';

@Component({
  selector: 'jhi-membership-conf-my-suffix-update',
  templateUrl: './membership-conf-my-suffix-update.component.html',
})
export class MembershipConfMySuffixUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    nomClient: [null, [Validators.minLength(4), Validators.maxLength(100)]],
    slogan: [null, [Validators.maxLength(60)]],
    telephones: [null, [Validators.maxLength(60)]],
    adresse: [null, [Validators.maxLength(60)]],
    infos: [null, [Validators.maxLength(120)]],
    logoLink: [],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
  });

  constructor(
    protected membershipConfService: MembershipConfMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membershipConf }) => {
      if (membershipConf.id === undefined) {
        const today = dayjs().startOf('day');
        membershipConf.createdDate = today;
        membershipConf.lastModifiedDate = today;
      }

      this.updateForm(membershipConf);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const membershipConf = this.createFromForm();
    if (membershipConf.id !== undefined) {
      this.subscribeToSaveResponse(this.membershipConfService.update(membershipConf));
    } else {
      this.subscribeToSaveResponse(this.membershipConfService.create(membershipConf));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembershipConfMySuffix>>): void {
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

  protected updateForm(membershipConf: IMembershipConfMySuffix): void {
    this.editForm.patchValue({
      id: membershipConf.id,
      nomClient: membershipConf.nomClient,
      slogan: membershipConf.slogan,
      telephones: membershipConf.telephones,
      adresse: membershipConf.adresse,
      infos: membershipConf.infos,
      logoLink: membershipConf.logoLink,
      createdBy: membershipConf.createdBy,
      createdDate: membershipConf.createdDate ? membershipConf.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: membershipConf.lastModifiedBy,
      lastModifiedDate: membershipConf.lastModifiedDate ? membershipConf.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IMembershipConfMySuffix {
    return {
      ...new MembershipConfMySuffix(),
      id: this.editForm.get(['id'])!.value,
      nomClient: this.editForm.get(['nomClient'])!.value,
      slogan: this.editForm.get(['slogan'])!.value,
      telephones: this.editForm.get(['telephones'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      infos: this.editForm.get(['infos'])!.value,
      logoLink: this.editForm.get(['logoLink'])!.value,
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
