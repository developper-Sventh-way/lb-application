import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserPaymentMySuffixService } from '../service/user-payment-my-suffix.service';
import { IUserPaymentMySuffix, UserPaymentMySuffix } from '../user-payment-my-suffix.model';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { UtilisateurMySuffixService } from 'app/entities/utilisateur-my-suffix/service/utilisateur-my-suffix.service';

import { UserPaymentMySuffixUpdateComponent } from './user-payment-my-suffix-update.component';

describe('UserPaymentMySuffix Management Update Component', () => {
  let comp: UserPaymentMySuffixUpdateComponent;
  let fixture: ComponentFixture<UserPaymentMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userPaymentService: UserPaymentMySuffixService;
  let utilisateurService: UtilisateurMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserPaymentMySuffixUpdateComponent],
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
      .overrideTemplate(UserPaymentMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserPaymentMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userPaymentService = TestBed.inject(UserPaymentMySuffixService);
    utilisateurService = TestBed.inject(UtilisateurMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call UtilisateurMySuffix query and add missing value', () => {
      const userPayment: IUserPaymentMySuffix = { id: 456 };
      const utilisateur: IUtilisateurMySuffix = { id: 22976 };
      userPayment.utilisateur = utilisateur;

      const utilisateurCollection: IUtilisateurMySuffix[] = [{ id: 6227 }];
      jest.spyOn(utilisateurService, 'query').mockReturnValue(of(new HttpResponse({ body: utilisateurCollection })));
      const additionalUtilisateurMySuffixes = [utilisateur];
      const expectedCollection: IUtilisateurMySuffix[] = [...additionalUtilisateurMySuffixes, ...utilisateurCollection];
      jest.spyOn(utilisateurService, 'addUtilisateurMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ userPayment });
      comp.ngOnInit();

      expect(utilisateurService.query).toHaveBeenCalled();
      expect(utilisateurService.addUtilisateurMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        utilisateurCollection,
        ...additionalUtilisateurMySuffixes
      );
      expect(comp.utilisateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const userPayment: IUserPaymentMySuffix = { id: 456 };
      const utilisateur: IUtilisateurMySuffix = { id: 45137 };
      userPayment.utilisateur = utilisateur;

      activatedRoute.data = of({ userPayment });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(userPayment));
      expect(comp.utilisateursSharedCollection).toContain(utilisateur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserPaymentMySuffix>>();
      const userPayment = { id: 123 };
      jest.spyOn(userPaymentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userPayment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userPayment }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(userPaymentService.update).toHaveBeenCalledWith(userPayment);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserPaymentMySuffix>>();
      const userPayment = new UserPaymentMySuffix();
      jest.spyOn(userPaymentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userPayment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userPayment }));
      saveSubject.complete();

      // THEN
      expect(userPaymentService.create).toHaveBeenCalledWith(userPayment);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserPaymentMySuffix>>();
      const userPayment = { id: 123 };
      jest.spyOn(userPaymentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userPayment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userPaymentService.update).toHaveBeenCalledWith(userPayment);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackUtilisateurMySuffixById', () => {
      it('Should return tracked UtilisateurMySuffix primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUtilisateurMySuffixById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
