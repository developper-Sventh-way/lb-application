import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITicketMySuffix, TicketMySuffix } from '../ticket-my-suffix.model';
import { TicketMySuffixService } from '../service/ticket-my-suffix.service';
import { IPointOfSaleMySuffix } from 'app/entities/point-of-sale-my-suffix/point-of-sale-my-suffix.model';
import { PointOfSaleMySuffixService } from 'app/entities/point-of-sale-my-suffix/service/point-of-sale-my-suffix.service';
import { ITirageMySuffix } from 'app/entities/tirage-my-suffix/tirage-my-suffix.model';
import { TirageMySuffixService } from 'app/entities/tirage-my-suffix/service/tirage-my-suffix.service';
import { IUserSaleAccountMySuffix } from 'app/entities/user-sale-account-my-suffix/user-sale-account-my-suffix.model';
import { UserSaleAccountMySuffixService } from 'app/entities/user-sale-account-my-suffix/service/user-sale-account-my-suffix.service';
import { StatutFiche } from 'app/entities/enumerations/statut-fiche.model';

@Component({
  selector: 'jhi-ticket-my-suffix-update',
  templateUrl: './ticket-my-suffix-update.component.html',
})
export class TicketMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  statutFicheValues = Object.keys(StatutFiche);

  pointOfSalesSharedCollection: IPointOfSaleMySuffix[] = [];
  tiragesSharedCollection: ITirageMySuffix[] = [];
  userSaleAccountsSharedCollection: IUserSaleAccountMySuffix[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    ticketNo: [null, [Validators.minLength(18), Validators.maxLength(18)]],
    statutFiche: [null, [Validators.required]],
    closeBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    closeDate: [],
    isClosed: [],
    closeById: [],
    payBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    payDate: [],
    isPay: [],
    payById: [],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
    pointOfSale: [],
    tirage: [],
    userSaleAccount: [],
  });

  constructor(
    protected ticketService: TicketMySuffixService,
    protected pointOfSaleService: PointOfSaleMySuffixService,
    protected tirageService: TirageMySuffixService,
    protected userSaleAccountService: UserSaleAccountMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ticket }) => {
      if (ticket.id === undefined) {
        const today = dayjs().startOf('day');
        ticket.closeDate = today;
        ticket.payDate = today;
        ticket.createdDate = today;
        ticket.lastModifiedDate = today;
      }

      this.updateForm(ticket);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ticket = this.createFromForm();
    if (ticket.id !== undefined) {
      this.subscribeToSaveResponse(this.ticketService.update(ticket));
    } else {
      this.subscribeToSaveResponse(this.ticketService.create(ticket));
    }
  }

  trackPointOfSaleMySuffixById(_index: number, item: IPointOfSaleMySuffix): number {
    return item.id!;
  }

  trackTirageMySuffixById(_index: number, item: ITirageMySuffix): number {
    return item.id!;
  }

  trackUserSaleAccountMySuffixById(_index: number, item: IUserSaleAccountMySuffix): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITicketMySuffix>>): void {
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

  protected updateForm(ticket: ITicketMySuffix): void {
    this.editForm.patchValue({
      id: ticket.id,
      ticketNo: ticket.ticketNo,
      statutFiche: ticket.statutFiche,
      closeBy: ticket.closeBy,
      closeDate: ticket.closeDate ? ticket.closeDate.format(DATE_TIME_FORMAT) : null,
      isClosed: ticket.isClosed,
      closeById: ticket.closeById,
      payBy: ticket.payBy,
      payDate: ticket.payDate ? ticket.payDate.format(DATE_TIME_FORMAT) : null,
      isPay: ticket.isPay,
      payById: ticket.payById,
      createdBy: ticket.createdBy,
      createdDate: ticket.createdDate ? ticket.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: ticket.lastModifiedBy,
      lastModifiedDate: ticket.lastModifiedDate ? ticket.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      pointOfSale: ticket.pointOfSale,
      tirage: ticket.tirage,
      userSaleAccount: ticket.userSaleAccount,
    });

    this.pointOfSalesSharedCollection = this.pointOfSaleService.addPointOfSaleMySuffixToCollectionIfMissing(
      this.pointOfSalesSharedCollection,
      ticket.pointOfSale
    );
    this.tiragesSharedCollection = this.tirageService.addTirageMySuffixToCollectionIfMissing(this.tiragesSharedCollection, ticket.tirage);
    this.userSaleAccountsSharedCollection = this.userSaleAccountService.addUserSaleAccountMySuffixToCollectionIfMissing(
      this.userSaleAccountsSharedCollection,
      ticket.userSaleAccount
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pointOfSaleService
      .query()
      .pipe(map((res: HttpResponse<IPointOfSaleMySuffix[]>) => res.body ?? []))
      .pipe(
        map((pointOfSales: IPointOfSaleMySuffix[]) =>
          this.pointOfSaleService.addPointOfSaleMySuffixToCollectionIfMissing(pointOfSales, this.editForm.get('pointOfSale')!.value)
        )
      )
      .subscribe((pointOfSales: IPointOfSaleMySuffix[]) => (this.pointOfSalesSharedCollection = pointOfSales));

    this.tirageService
      .query()
      .pipe(map((res: HttpResponse<ITirageMySuffix[]>) => res.body ?? []))
      .pipe(
        map((tirages: ITirageMySuffix[]) =>
          this.tirageService.addTirageMySuffixToCollectionIfMissing(tirages, this.editForm.get('tirage')!.value)
        )
      )
      .subscribe((tirages: ITirageMySuffix[]) => (this.tiragesSharedCollection = tirages));

    this.userSaleAccountService
      .query()
      .pipe(map((res: HttpResponse<IUserSaleAccountMySuffix[]>) => res.body ?? []))
      .pipe(
        map((userSaleAccounts: IUserSaleAccountMySuffix[]) =>
          this.userSaleAccountService.addUserSaleAccountMySuffixToCollectionIfMissing(
            userSaleAccounts,
            this.editForm.get('userSaleAccount')!.value
          )
        )
      )
      .subscribe((userSaleAccounts: IUserSaleAccountMySuffix[]) => (this.userSaleAccountsSharedCollection = userSaleAccounts));
  }

  protected createFromForm(): ITicketMySuffix {
    return {
      ...new TicketMySuffix(),
      id: this.editForm.get(['id'])!.value,
      ticketNo: this.editForm.get(['ticketNo'])!.value,
      statutFiche: this.editForm.get(['statutFiche'])!.value,
      closeBy: this.editForm.get(['closeBy'])!.value,
      closeDate: this.editForm.get(['closeDate'])!.value ? dayjs(this.editForm.get(['closeDate'])!.value, DATE_TIME_FORMAT) : undefined,
      isClosed: this.editForm.get(['isClosed'])!.value,
      closeById: this.editForm.get(['closeById'])!.value,
      payBy: this.editForm.get(['payBy'])!.value,
      payDate: this.editForm.get(['payDate'])!.value ? dayjs(this.editForm.get(['payDate'])!.value, DATE_TIME_FORMAT) : undefined,
      isPay: this.editForm.get(['isPay'])!.value,
      payById: this.editForm.get(['payById'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? dayjs(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      pointOfSale: this.editForm.get(['pointOfSale'])!.value,
      tirage: this.editForm.get(['tirage'])!.value,
      userSaleAccount: this.editForm.get(['userSaleAccount'])!.value,
    };
  }
}
