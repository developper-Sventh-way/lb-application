import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TicketOptionMySuffixService } from '../service/ticket-option-my-suffix.service';
import { ITicketOptionMySuffix, TicketOptionMySuffix } from '../ticket-option-my-suffix.model';
import { ITicketMySuffix } from 'app/entities/ticket-my-suffix/ticket-my-suffix.model';
import { TicketMySuffixService } from 'app/entities/ticket-my-suffix/service/ticket-my-suffix.service';

import { TicketOptionMySuffixUpdateComponent } from './ticket-option-my-suffix-update.component';

describe('TicketOptionMySuffix Management Update Component', () => {
  let comp: TicketOptionMySuffixUpdateComponent;
  let fixture: ComponentFixture<TicketOptionMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ticketOptionService: TicketOptionMySuffixService;
  let ticketService: TicketMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TicketOptionMySuffixUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TicketOptionMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TicketOptionMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ticketOptionService = TestBed.inject(TicketOptionMySuffixService);
    ticketService = TestBed.inject(TicketMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TicketMySuffix query and add missing value', () => {
      const ticketOption: ITicketOptionMySuffix = { id: 456 };
      const ticket: ITicketMySuffix = { id: 64946 };
      ticketOption.ticket = ticket;

      const ticketCollection: ITicketMySuffix[] = [{ id: 12734 }];
      jest.spyOn(ticketService, 'query').mockReturnValue(of(new HttpResponse({ body: ticketCollection })));
      const additionalTicketMySuffixes = [ticket];
      const expectedCollection: ITicketMySuffix[] = [...additionalTicketMySuffixes, ...ticketCollection];
      jest.spyOn(ticketService, 'addTicketMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ticketOption });
      comp.ngOnInit();

      expect(ticketService.query).toHaveBeenCalled();
      expect(ticketService.addTicketMySuffixToCollectionIfMissing).toHaveBeenCalledWith(ticketCollection, ...additionalTicketMySuffixes);
      expect(comp.ticketsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ticketOption: ITicketOptionMySuffix = { id: 456 };
      const ticket: ITicketMySuffix = { id: 37802 };
      ticketOption.ticket = ticket;

      activatedRoute.data = of({ ticketOption });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(ticketOption));
      expect(comp.ticketsSharedCollection).toContain(ticket);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TicketOptionMySuffix>>();
      const ticketOption = { id: 123 };
      jest.spyOn(ticketOptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ticketOption });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ticketOption }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ticketOptionService.update).toHaveBeenCalledWith(ticketOption);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TicketOptionMySuffix>>();
      const ticketOption = new TicketOptionMySuffix();
      jest.spyOn(ticketOptionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ticketOption });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ticketOption }));
      saveSubject.complete();

      // THEN
      expect(ticketOptionService.create).toHaveBeenCalledWith(ticketOption);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TicketOptionMySuffix>>();
      const ticketOption = { id: 123 };
      jest.spyOn(ticketOptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ticketOption });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ticketOptionService.update).toHaveBeenCalledWith(ticketOption);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTicketMySuffixById', () => {
      it('Should return tracked TicketMySuffix primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTicketMySuffixById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
