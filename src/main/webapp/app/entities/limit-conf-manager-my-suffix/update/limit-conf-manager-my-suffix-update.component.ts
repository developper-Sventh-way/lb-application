import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILimitConfManagerMySuffix, LimitConfManagerMySuffix } from '../limit-conf-manager-my-suffix.model';
import { LimitConfManagerMySuffixService } from '../service/limit-conf-manager-my-suffix.service';
import { IMembershipConfMySuffix } from 'app/entities/membership-conf-my-suffix/membership-conf-my-suffix.model';
import { MembershipConfMySuffixService } from 'app/entities/membership-conf-my-suffix/service/membership-conf-my-suffix.service';
import { TypeOption } from 'app/entities/enumerations/type-option.model';

@Component({
  selector: 'jhi-limit-conf-manager-my-suffix-update',
  templateUrl: './limit-conf-manager-my-suffix-update.component.html',
})
export class LimitConfManagerMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  typeOptionValues = Object.keys(TypeOption);

  membershipConfsSharedCollection: IMembershipConfMySuffix[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    nombreValue: [null, [Validators.minLength(2), Validators.maxLength(5)]],
    limit: [null, [Validators.required]],
    limitStatut: [],
    membershipConf: [],
  });

  constructor(
    protected limitConfManagerService: LimitConfManagerMySuffixService,
    protected membershipConfService: MembershipConfMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ limitConfManager }) => {
      this.updateForm(limitConfManager);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const limitConfManager = this.createFromForm();
    if (limitConfManager.id !== undefined) {
      this.subscribeToSaveResponse(this.limitConfManagerService.update(limitConfManager));
    } else {
      this.subscribeToSaveResponse(this.limitConfManagerService.create(limitConfManager));
    }
  }

  trackMembershipConfMySuffixById(_index: number, item: IMembershipConfMySuffix): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILimitConfManagerMySuffix>>): void {
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

  protected updateForm(limitConfManager: ILimitConfManagerMySuffix): void {
    this.editForm.patchValue({
      id: limitConfManager.id,
      nombreValue: limitConfManager.nombreValue,
      limit: limitConfManager.limit,
      limitStatut: limitConfManager.limitStatut,
      membershipConf: limitConfManager.membershipConf,
    });

    this.membershipConfsSharedCollection = this.membershipConfService.addMembershipConfMySuffixToCollectionIfMissing(
      this.membershipConfsSharedCollection,
      limitConfManager.membershipConf
    );
  }

  protected loadRelationshipsOptions(): void {
    this.membershipConfService
      .query()
      .pipe(map((res: HttpResponse<IMembershipConfMySuffix[]>) => res.body ?? []))
      .pipe(
        map((membershipConfs: IMembershipConfMySuffix[]) =>
          this.membershipConfService.addMembershipConfMySuffixToCollectionIfMissing(
            membershipConfs,
            this.editForm.get('membershipConf')!.value
          )
        )
      )
      .subscribe((membershipConfs: IMembershipConfMySuffix[]) => (this.membershipConfsSharedCollection = membershipConfs));
  }

  protected createFromForm(): ILimitConfManagerMySuffix {
    return {
      ...new LimitConfManagerMySuffix(),
      id: this.editForm.get(['id'])!.value,
      nombreValue: this.editForm.get(['nombreValue'])!.value,
      limit: this.editForm.get(['limit'])!.value,
      limitStatut: this.editForm.get(['limitStatut'])!.value,
      membershipConf: this.editForm.get(['membershipConf'])!.value,
    };
  }
}
