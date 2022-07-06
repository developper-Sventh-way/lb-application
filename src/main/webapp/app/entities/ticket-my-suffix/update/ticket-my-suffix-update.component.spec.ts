import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TicketMySuffixService } from '../service/ticket-my-suffix.service';
import { ITicketMySuffix, TicketMySuffix } from '../ticket-my-suffix.model';
import { IPointOfSaleMySuffix } from 'app/entities/point-of-sale-my-suffix/point-of-sale-my-suffix.model';
import { PointOfSaleMySuffixService } from 'app/entities/point-of-sale-my-suffix/service/point-of-sale-my-suffix.service';
import { ITirageMySuffix } from 'app/entities/tirage-my-suffix/tirage-my-suffix.model';
import { TirageMySuffixService } from 'app/entities/tirage-my-suffix/service/tirage-my-suffix.service';
import { IUserSaleAccountMySuffix } from 'app/entities/user-sale-account-my-suffix/user-sale-account-my-suffix.model';
import { UserSaleAccountMySuffixService } from 'app/entities/user-sale-account-my-suffix/service/user-sale-account-my-suffix.service';

import { TicketMySuffixUpdateComponent } from './ticket-my-suffix-update.component';

describe('TicketMySuffix Management Update Component', () => {
  let comp: TicketMySuffixUpdateComponent;
  let fixture: ComponentFixture<TicketMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ticketService: TicketMySuffixService;
  let pointOfSaleService: PointOfSaleMySuffixService;
  let tirageService: TirageMySuffixService;
  let userSaleAccountService: UserSaleAccountMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TicketMySuffixUpdateComponent],
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
      .overrideTemplate(TicketMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TicketMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ticketService = TestBed.inject(TicketMySuffixService);
    pointOfSaleService = TestBed.inject(PointOfSaleMySuffixService);
    tirageService = TestBed.inject(TirageMySuffixService);
    userSaleAccountService = TestBed.inject(UserSaleAccountMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PointOfSaleMySuffix query and add missing value', () => {
      const ticket: ITicketMySuffix = { id: 456 };
      const pointOfSale: IPointOfSaleMySuffix = { id: 15964 };
      ticket.pointOfSale = pointOfSale;

      const pointOfSaleCollection: IPointOfSaleMySuffix[] = [{ id: 68064 }];
      jest.spyOn(pointOfSaleService, 'query').mockReturnValue(of(new HttpResponse({ body: pointOfSaleCollection })));
      const additionalPointOfSaleMySuffixes = [pointOfSale];
      const expectedCollection: IPointOfSaleMySuffix[] = [...additionalPointOfSaleMySuffixes, ...pointOfSaleCollection];
      jest.spyOn(pointOfSaleService, 'addPointOfSaleMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ticket });
      comp.ngOnInit();

      expect(pointOfSaleService.query).toHaveBeenCalled();
      expect(pointOfSaleService.addPointOfSaleMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        pointOfSaleCollection,
        ...additionalPointOfSaleMySuffixes
      );
      expect(comp.pointOfSalesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TirageMySuffix query and add missing value', () => {
      const ticket: ITicketMySuffix = { id: 456 };
      const tirage: ITirageMySuffix = { id: 94337 };
      ticket.tirage = tirage;

      const tirageCollection: ITirageMySuffix[] = [{ id: 27676 }];
      jest.spyOn(tirageService, 'query').mockReturnValue(of(new HttpResponse({ body: tirageCollection })));
      const additionalTirageMySuffixes = [tirage];
      const expectedCollection: ITirageMySuffix[] = [...additionalTirageMySuffixes, ...tirageCollection];
      jest.spyOn(tirageService, 'addTirageMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ticket });
      comp.ngOnInit();

      expect(tirageService.query).toHaveBeenCalled();
      expect(tirageService.addTirageMySuffixToCollectionIfMissing).toHaveBeenCalledWith(tirageCollection, ...additionalTirageMySuffixes);
      expect(comp.tiragesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call UserSaleAccountMySuffix query and add missing value', () => {
      const ticket: ITicketMySuffix = { id: 456 };
      const userSaleAccount: IUserSaleAccountMySuffix = { id: 94063 };
      ticket.userSaleAccount = userSaleAccount;

      const userSaleAccountCollection: IUserSaleAccountMySuffix[] = [{ id: 14075 }];
      jest.spyOn(userSaleAccountService, 'query').mockReturnValue(of(new HttpResponse({ body: userSaleAccountCollection })));
      const additionalUserSaleAccountMySuffixes = [userSaleAccount];
      const expectedCollection: IUserSaleAccountMySuffix[] = [...additionalUserSaleAccountMySuffixes, ...userSaleAccountCollection];
      jest.spyOn(userSaleAccountService, 'addUserSaleAccountMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ticket });
      comp.ngOnInit();

      expect(userSaleAccountService.query).toHaveBeenCalled();
      expect(userSaleAccountService.addUserSaleAccountMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        userSaleAccountCollection,
        ...additionalUserSaleAccountMySuffixes
      );
      expect(comp.userSaleAccountsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ticket: ITicketMySuffix = { id: 456 };
      const pointOfSale: IPointOfSaleMySuffix = { id: 73699 };
      ticket.pointOfSale = pointOfSale;
      const tirage: ITirageMySuffix = { id: 44191 };
      ticket.tirage = tirage;
      const userSaleAccount: IUserSaleAccountMySuffix = { id: 35849 };
      ticket.userSaleAccount = userSaleAccount;

      activatedRoute.data = of({ ticket });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(ticket));
      expect(comp.pointOfSalesSharedCollection).toContain(pointOfSale);
      expect(comp.tiragesSharedCollection).toContain(tirage);
      expect(comp.userSaleAccountsSharedCollection).toContain(userSaleAccount);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TicketMySuffix>>();
      const ticket = { id: 123 };
      jest.spyOn(ticketService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ticket });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ticket }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ticketService.update).toHaveBeenCalledWith(ticket);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TicketMySuffix>>();
      const ticket = new TicketMySuffix();
      jest.spyOn(ticketService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ticket });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ticket }));
      saveSubject.complete();

      // THEN
      expect(ticketService.create).toHaveBeenCalledWith(ticket);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TicketMySuffix>>();
      const ticket = { id: 123 };
      jest.spyOn(ticketService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ticket });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ticketService.update).toHaveBeenCalledWith(ticket);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPointOfSaleMySuffixById', () => {
      it('Should return tracked PointOfSaleMySuffix primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPointOfSaleMySuffixById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTirageMySuffixById', () => {
      it('Should return tracked TirageMySuffix primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTirageMySuffixById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackUserSaleAccountMySuffixById', () => {
      it('Should return tracked UserSaleAccountMySuffix primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserSaleAccountMySuffixById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
