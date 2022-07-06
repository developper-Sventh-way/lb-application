import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITicketOptionMySuffix } from '../ticket-option-my-suffix.model';

@Component({
  selector: 'jhi-ticket-option-my-suffix-detail',
  templateUrl: './ticket-option-my-suffix-detail.component.html',
})
export class TicketOptionMySuffixDetailComponent implements OnInit {
  ticketOption: ITicketOptionMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ticketOption }) => {
      this.ticketOption = ticketOption;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
