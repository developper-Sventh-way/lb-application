import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserSaleAccountMySuffixService } from '../service/user-sale-account-my-suffix.service';
import { IUserSaleAccountMySuffix, UserSaleAccountMySuffix } from '../user-sale-account-my-suffix.model';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { UtilisateurMySuffixService } from 'app/entities/utilisateur-my-suffix/service/utilisateur-my-suffix.service';

import { UserSaleAccountMySuffixUpdateComponent } from './user-sale-account-my-suffix-update.component';

describe('UserSaleAccountMySuffix Management Update Component', () => {
  let comp: UserSaleAccountMySuffixUpdateComponent;
  let fixture: ComponentFixture<UserSaleAccountMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userSaleAccountService: UserSaleAccountMySuffixService;
  let utilisateurService: UtilisateurMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserSaleAccountMySuffixUpdateComponent],
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
      .overrideTemplate(UserSaleAccountMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserSaleAccountMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userSaleAccountService = TestBed.inject(UserSaleAccountMySuffixService);
    utilisateurService = TestBed.inject(UtilisateurMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call UtilisateurMySuffix query and add missing value', () => {
      const userSaleAccount: IUserSaleAccountMySuffix = { id: 456 };
      const utilisateur: IUtilisateurMySuffix = { id: 2318 };
      userSaleAccount.utilisateur = utilisateur;

      const utilisateurCollection: IUtilisateurMySuffix[] = [{ id: 55466 }];
      jest.spyOn(utilisateurService, 'query').mockReturnValue(of(new HttpResponse({ body: utilisateurCollection })));
      const additionalUtilisateurMySuffixes = [utilisateur];
      const expectedCollection: IUtilisateurMySuffix[] = [...additionalUtilisateurMySuffixes, ...utilisateurCollection];
      jest.spyOn(utilisateurService, 'addUtilisateurMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ userSaleAccount });
      comp.ngOnInit();

      expect(utilisateurService.query).toHaveBeenCalled();
      expect(utilisateurService.addUtilisateurMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        utilisateurCollection,
        ...additionalUtilisateurMySuffixes
      );
      expect(comp.utilisateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const userSaleAccount: IUserSaleAccountMySuffix = { id: 456 };
      const utilisateur: IUtilisateurMySuffix = { id: 94618 };
      userSaleAccount.utilisateur = utilisateur;

      activatedRoute.data = of({ userSaleAccount });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(userSaleAccount));
      expect(comp.utilisateursSharedCollection).toContain(utilisateur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserSaleAccountMySuffix>>();
      const userSaleAccount = { id: 123 };
      jest.spyOn(userSaleAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userSaleAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userSaleAccount }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(userSaleAccountService.update).toHaveBeenCalledWith(userSaleAccount);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserSaleAccountMySuffix>>();
      const userSaleAccount = new UserSaleAccountMySuffix();
      jest.spyOn(userSaleAccountService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userSaleAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userSaleAccount }));
      saveSubject.complete();

      // THEN
      expect(userSaleAccountService.create).toHaveBeenCalledWith(userSaleAccount);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserSaleAccountMySuffix>>();
      const userSaleAccount = { id: 123 };
      jest.spyOn(userSaleAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userSaleAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userSaleAccountService.update).toHaveBeenCalledWith(userSaleAccount);
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
