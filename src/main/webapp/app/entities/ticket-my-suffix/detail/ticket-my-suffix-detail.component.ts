import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITicketMySuffix } from '../ticket-my-suffix.model';

@Component({
  selector: 'jhi-ticket-my-suffix-detail',
  templateUrl: './ticket-my-suffix-detail.component.html',
})
export class TicketMySuffixDetailComponent implements OnInit {
  ticket: ITicketMySuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ticket }) => {
      this.ticket = ticket;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
