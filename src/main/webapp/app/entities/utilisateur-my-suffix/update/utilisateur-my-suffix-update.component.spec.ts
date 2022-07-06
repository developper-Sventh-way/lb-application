import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UtilisateurMySuffixService } from '../service/utilisateur-my-suffix.service';
import { IUtilisateurMySuffix, UtilisateurMySuffix } from '../utilisateur-my-suffix.model';
import { ICreditSoldeMySuffix } from 'app/entities/credit-solde-my-suffix/credit-solde-my-suffix.model';
import { CreditSoldeMySuffixService } from 'app/entities/credit-solde-my-suffix/service/credit-solde-my-suffix.service';
import { IUserPaymentConfMySuffix } from 'app/entities/user-payment-conf-my-suffix/user-payment-conf-my-suffix.model';
import { UserPaymentConfMySuffixService } from 'app/entities/user-payment-conf-my-suffix/service/user-payment-conf-my-suffix.service';
import { IPointOfSaleMySuffix } from 'app/entities/point-of-sale-my-suffix/point-of-sale-my-suffix.model';
import { PointOfSaleMySuffixService } from 'app/entities/point-of-sale-my-suffix/service/point-of-sale-my-suffix.service';

import { UtilisateurMySuffixUpdateComponent } from './utilisateur-my-suffix-update.component';

describe('UtilisateurMySuffix Management Update Component', () => {
  let comp: UtilisateurMySuffixUpdateComponent;
  let fixture: ComponentFixture<UtilisateurMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let utilisateurService: UtilisateurMySuffixService;
  let creditSoldeService: CreditSoldeMySuffixService;
  let userPaymentConfService: UserPaymentConfMySuffixService;
  let pointOfSaleService: PointOfSaleMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UtilisateurMySuffixUpdateComponent],
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
      .overrideTemplate(UtilisateurMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UtilisateurMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    utilisateurService = TestBed.inject(UtilisateurMySuffixService);
    creditSoldeService = TestBed.inject(CreditSoldeMySuffixService);
    userPaymentConfService = TestBed.inject(UserPaymentConfMySuffixService);
    pointOfSaleService = TestBed.inject(PointOfSaleMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call creditSolde query and add missing value', () => {
      const utilisateur: IUtilisateurMySuffix = { id: 456 };
      const creditSolde: ICreditSoldeMySuffix = { id: 2510 };
      utilisateur.creditSolde = creditSolde;

      const creditSoldeCollection: ICreditSoldeMySuffix[] = [{ id: 24687 }];
      jest.spyOn(creditSoldeService, 'query').mockReturnValue(of(new HttpResponse({ body: creditSoldeCollection })));
      const expectedCollection: ICreditSoldeMySuffix[] = [creditSolde, ...creditSoldeCollection];
      jest.spyOn(creditSoldeService, 'addCreditSoldeMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ utilisateur });
      comp.ngOnInit();

      expect(creditSoldeService.query).toHaveBeenCalled();
      expect(creditSoldeService.addCreditSoldeMySuffixToCollectionIfMissing).toHaveBeenCalledWith(creditSoldeCollection, creditSolde);
      expect(comp.creditSoldesCollection).toEqual(expectedCollection);
    });

    it('Should call userPaymentConf query and add missing value', () => {
      const utilisateur: IUtilisateurMySuffix = { id: 456 };
      const userPaymentConf: IUserPaymentConfMySuffix = { id: 13552 };
      utilisateur.userPaymentConf = userPaymentConf;

      const userPaymentConfCollection: IUserPaymentConfMySuffix[] = [{ id: 74841 }];
      jest.spyOn(userPaymentConfService, 'query').mockReturnValue(of(new HttpResponse({ body: userPaymentConfCollection })));
      const expectedCollection: IUserPaymentConfMySuffix[] = [userPaymentConf, ...userPaymentConfCollection];
      jest.spyOn(userPaymentConfService, 'addUserPaymentConfMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ utilisateur });
      comp.ngOnInit();

      expect(userPaymentConfService.query).toHaveBeenCalled();
      expect(userPaymentConfService.addUserPaymentConfMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        userPaymentConfCollection,
        userPaymentConf
      );
      expect(comp.userPaymentConfsCollection).toEqual(expectedCollection);
    });

    it('Should call PointOfSaleMySuffix query and add missing value', () => {
      const utilisateur: IUtilisateurMySuffix = { id: 456 };
      const pointOfSale: IPointOfSaleMySuffix = { id: 30404 };
      utilisateur.pointOfSale = pointOfSale;

      const pointOfSaleCollection: IPointOfSaleMySuffix[] = [{ id: 12536 }];
      jest.spyOn(pointOfSaleService, 'query').mockReturnValue(of(new HttpResponse({ body: pointOfSaleCollection })));
      const additionalPointOfSaleMySuffixes = [pointOfSale];
      const expectedCollection: IPointOfSaleMySuffix[] = [...additionalPointOfSaleMySuffixes, ...pointOfSaleCollection];
      jest.spyOn(pointOfSaleService, 'addPointOfSaleMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ utilisateur });
      comp.ngOnInit();

      expect(pointOfSaleService.query).toHaveBeenCalled();
      expect(pointOfSaleService.addPointOfSaleMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        pointOfSaleCollection,
        ...additionalPointOfSaleMySuffixes
      );
      expect(comp.pointOfSalesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const utilisateur: IUtilisateurMySuffix = { id: 456 };
      const creditSolde: ICreditSoldeMySuffix = { id: 70027 };
      utilisateur.creditSolde = creditSolde;
      const userPaymentConf: IUserPaymentConfMySuffix = { id: 8382 };
      utilisateur.userPaymentConf = userPaymentConf;
      const pointOfSale: IPointOfSaleMySuffix = { id: 2963 };
      utilisateur.pointOfSale = pointOfSale;

      activatedRoute.data = of({ utilisateur });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(utilisateur));
      expect(comp.creditSoldesCollection).toContain(creditSolde);
      expect(comp.userPaymentConfsCollection).toContain(userPaymentConf);
      expect(comp.pointOfSalesSharedCollection).toContain(pointOfSale);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UtilisateurMySuffix>>();
      const utilisateur = { id: 123 };
      jest.spyOn(utilisateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ utilisateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: utilisateur }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(utilisateurService.update).toHaveBeenCalledWith(utilisateur);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UtilisateurMySuffix>>();
      const utilisateur = new UtilisateurMySuffix();
      jest.spyOn(utilisateurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ utilisateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: utilisateur }));
      saveSubject.complete();

      // THEN
      expect(utilisateurService.create).toHaveBeenCalledWith(utilisateur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UtilisateurMySuffix>>();
      const utilisateur = { id: 123 };
      jest.spyOn(utilisateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ utilisateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(utilisateurService.update).toHaveBeenCalledWith(utilisateur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCreditSoldeMySuffixById', () => {
      it('Should return tracked CreditSoldeMySuffix primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCreditSoldeMySuffixById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackUserPaymentConfMySuffixById', () => {
      it('Should return tracked UserPaymentConfMySuffix primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserPaymentConfMySuffixById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPointOfSaleMySuffixById', () => {
      it('Should return tracked PointOfSaleMySuffix primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPointOfSaleMySuffixById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
