import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TicketMySuffixDetailComponent } from './ticket-my-suffix-detail.component';

describe('TicketMySuffix Management Detail Component', () => {
  let comp: TicketMySuffixDetailComponent;
  let fixture: ComponentFixture<TicketMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TicketMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ticket: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TicketMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TicketMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ticket on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ticket).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
