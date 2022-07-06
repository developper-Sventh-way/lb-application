import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITicketOptionMySuffix, TicketOptionMySuffix } from '../ticket-option-my-suffix.model';
import { TicketOptionMySuffixService } from '../service/ticket-option-my-suffix.service';
import { ITicketMySuffix } from 'app/entities/ticket-my-suffix/ticket-my-suffix.model';
import { TicketMySuffixService } from 'app/entities/ticket-my-suffix/service/ticket-my-suffix.service';
import { TypeOption } from 'app/entities/enumerations/type-option.model';
import { StatutFiche } from 'app/entities/enumerations/statut-fiche.model';

@Component({
  selector: 'jhi-ticket-option-my-suffix-update',
  templateUrl: './ticket-option-my-suffix-update.component.html',
})
export class TicketOptionMySuffixUpdateComponent implements OnInit {
  isSaving = false;
  typeOptionValues = Object.keys(TypeOption);
  statutFicheValues = Object.keys(StatutFiche);

  ticketsSharedCollection: ITicketMySuffix[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    contenu: [null, [Validators.minLength(2), Validators.maxLength(7)]],
    playAmount: [null, [Validators.required]],
    typeOption: [null, [Validators.required]],
    statutOption: [null, [Validators.required]],
    multiplicateur: [null, [Validators.required]],
    createdBy: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(45)]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.minLength(4), Validators.maxLength(45)]],
    lastModifiedDate: [],
    ticket: [],
  });

  constructor(
    protected ticketOptionService: TicketOptionMySuffixService,
    protected ticketService: TicketMySuffixService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ticketOption }) => {
      if (ticketOption.id === undefined) {
        const today = dayjs().startOf('day');
        ticketOption.createdDate = today;
        ticketOption.lastModifiedDate = today;
      }

      this.updateForm(ticketOption);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ticketOption = this.createFromForm();
    if (ticketOption.id !== undefined) {
      this.subscribeToSaveResponse(this.ticketOptionService.update(ticketOption));
    } else {
      this.subscribeToSaveResponse(this.ticketOptionService.create(ticketOption));
    }
  }

  trackTicketMySuffixById(_index: number, item: ITicketMySuffix): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITicketOptionMySuffix>>): void {
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

  protected updateForm(ticketOption: ITicketOptionMySuffix): void {
    this.editForm.patchValue({
      id: ticketOption.id,
      contenu: ticketOption.contenu,
      playAmount: ticketOption.playAmount,
      typeOption: ticketOption.typeOption,
      statutOption: ticketOption.statutOption,
      multiplicateur: ticketOption.multiplicateur,
      createdBy: ticketOption.createdBy,
      createdDate: ticketOption.createdDate ? ticketOption.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: ticketOption.lastModifiedBy,
      lastModifiedDate: ticketOption.lastModifiedDate ? ticketOption.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      ticket: ticketOption.ticket,
    });

    this.ticketsSharedCollection = this.ticketService.addTicketMySuffixToCollectionIfMissing(
      this.ticketsSharedCollection,
      ticketOption.ticket
    );
  }

  protected loadRelationshipsOptions(): void {
    this.ticketService
      .query()
      .pipe(map((res: HttpResponse<ITicketMySuffix[]>) => res.body ?? []))
      .pipe(
        map((tickets: ITicketMySuffix[]) =>
          this.ticketService.addTicketMySuffixToCollectionIfMissing(tickets, this.editForm.get('ticket')!.value)
        )
      )
      .subscribe((tickets: ITicketMySuffix[]) => (this.ticketsSharedCollection = tickets));
  }

  protected createFromForm(): ITicketOptionMySuffix {
    return {
      ...new TicketOptionMySuffix(),
      id: this.editForm.get(['id'])!.value,
      contenu: this.editForm.get(['contenu'])!.value,
      playAmount: this.editForm.get(['playAmount'])!.value,
      typeOption: this.editForm.get(['typeOption'])!.value,
      statutOption: this.editForm.get(['statutOption'])!.value,
      multiplicateur: this.editForm.get(['multiplicateur'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? dayjs(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      ticket: this.editForm.get(['ticket'])!.value,
    };
  }
}
