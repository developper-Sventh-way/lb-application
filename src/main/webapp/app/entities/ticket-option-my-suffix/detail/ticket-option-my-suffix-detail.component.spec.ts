import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TicketOptionMySuffixDetailComponent } from './ticket-option-my-suffix-detail.component';

describe('TicketOptionMySuffix Management Detail Component', () => {
  let comp: TicketOptionMySuffixDetailComponent;
  let fixture: ComponentFixture<TicketOptionMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TicketOptionMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ticketOption: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TicketOptionMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TicketOptionMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ticketOption on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ticketOption).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
