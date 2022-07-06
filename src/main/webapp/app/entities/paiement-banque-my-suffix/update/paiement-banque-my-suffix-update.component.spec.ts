import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaiementBanqueMySuffixService } from '../service/paiement-banque-my-suffix.service';
import { IPaiementBanqueMySuffix, PaiementBanqueMySuffix } from '../paiement-banque-my-suffix.model';
import { IPointOfSaleMySuffix } from 'app/entities/point-of-sale-my-suffix/point-of-sale-my-suffix.model';
import { PointOfSaleMySuffixService } from 'app/entities/point-of-sale-my-suffix/service/point-of-sale-my-suffix.service';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { UtilisateurMySuffixService } from 'app/entities/utilisateur-my-suffix/service/utilisateur-my-suffix.service';

import { PaiementBanqueMySuffixUpdateComponent } from './paiement-banque-my-suffix-update.component';

describe('PaiementBanqueMySuffix Management Update Component', () => {
  let comp: PaiementBanqueMySuffixUpdateComponent;
  let fixture: ComponentFixture<PaiementBanqueMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paiementBanqueService: PaiementBanqueMySuffixService;
  let pointOfSaleService: PointOfSaleMySuffixService;
  let utilisateurService: UtilisateurMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaiementBanqueMySuffixUpdateComponent],
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
      .overrideTemplate(PaiementBanqueMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaiementBanqueMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paiementBanqueService = TestBed.inject(PaiementBanqueMySuffixService);
    pointOfSaleService = TestBed.inject(PointOfSaleMySuffixService);
    utilisateurService = TestBed.inject(UtilisateurMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PointOfSaleMySuffix query and add missing value', () => {
      const paiementBanque: IPaiementBanqueMySuffix = { id: 456 };
      const pointOfSale: IPointOfSaleMySuffix = { id: 55686 };
      paiementBanque.pointOfSale = pointOfSale;

      const pointOfSaleCollection: IPointOfSaleMySuffix[] = [{ id: 94341 }];
      jest.spyOn(pointOfSaleService, 'query').mockReturnValue(of(new HttpResponse({ body: pointOfSaleCollection })));
      const additionalPointOfSaleMySuffixes = [pointOfSale];
      const expectedCollection: IPointOfSaleMySuffix[] = [...additionalPointOfSaleMySuffixes, ...pointOfSaleCollection];
      jest.spyOn(pointOfSaleService, 'addPointOfSaleMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paiementBanque });
      comp.ngOnInit();

      expect(pointOfSaleService.query).toHaveBeenCalled();
      expect(pointOfSaleService.addPointOfSaleMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        pointOfSaleCollection,
        ...additionalPointOfSaleMySuffixes
      );
      expect(comp.pointOfSalesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call UtilisateurMySuffix query and add missing value', () => {
      const paiementBanque: IPaiementBanqueMySuffix = { id: 456 };
      const utilisateur: IUtilisateurMySuffix = { id: 27413 };
      paiementBanque.utilisateur = utilisateur;

      const utilisateurCollection: IUtilisateurMySuffix[] = [{ id: 75395 }];
      jest.spyOn(utilisateurService, 'query').mockReturnValue(of(new HttpResponse({ body: utilisateurCollection })));
      const additionalUtilisateurMySuffixes = [utilisateur];
      const expectedCollection: IUtilisateurMySuffix[] = [...additionalUtilisateurMySuffixes, ...utilisateurCollection];
      jest.spyOn(utilisateurService, 'addUtilisateurMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paiementBanque });
      comp.ngOnInit();

      expect(utilisateurService.query).toHaveBeenCalled();
      expect(utilisateurService.addUtilisateurMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        utilisateurCollection,
        ...additionalUtilisateurMySuffixes
      );
      expect(comp.utilisateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paiementBanque: IPaiementBanqueMySuffix = { id: 456 };
      const pointOfSale: IPointOfSaleMySuffix = { id: 27228 };
      paiementBanque.pointOfSale = pointOfSale;
      const utilisateur: IUtilisateurMySuffix = { id: 98226 };
      paiementBanque.utilisateur = utilisateur;

      activatedRoute.data = of({ paiementBanque });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(paiementBanque));
      expect(comp.pointOfSalesSharedCollection).toContain(pointOfSale);
      expect(comp.utilisateursSharedCollection).toContain(utilisateur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaiementBanqueMySuffix>>();
      const paiementBanque = { id: 123 };
      jest.spyOn(paiementBanqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paiementBanque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paiementBanque }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(paiementBanqueService.update).toHaveBeenCalledWith(paiementBanque);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaiementBanqueMySuffix>>();
      const paiementBanque = new PaiementBanqueMySuffix();
      jest.spyOn(paiementBanqueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paiementBanque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paiementBanque }));
      saveSubject.complete();

      // THEN
      expect(paiementBanqueService.create).toHaveBeenCalledWith(paiementBanque);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PaiementBanqueMySuffix>>();
      const paiementBanque = { id: 123 };
      jest.spyOn(paiementBanqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paiementBanque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paiementBanqueService.update).toHaveBeenCalledWith(paiementBanque);
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

    describe('trackUtilisateurMySuffixById', () => {
      it('Should return tracked UtilisateurMySuffix primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUtilisateurMySuffixById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
