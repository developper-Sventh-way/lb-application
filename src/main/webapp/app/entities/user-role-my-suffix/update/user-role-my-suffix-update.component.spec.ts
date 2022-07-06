import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserRoleMySuffixService } from '../service/user-role-my-suffix.service';
import { IUserRoleMySuffix, UserRoleMySuffix } from '../user-role-my-suffix.model';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { UtilisateurMySuffixService } from 'app/entities/utilisateur-my-suffix/service/utilisateur-my-suffix.service';

import { UserRoleMySuffixUpdateComponent } from './user-role-my-suffix-update.component';

describe('UserRoleMySuffix Management Update Component', () => {
  let comp: UserRoleMySuffixUpdateComponent;
  let fixture: ComponentFixture<UserRoleMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userRoleService: UserRoleMySuffixService;
  let utilisateurService: UtilisateurMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserRoleMySuffixUpdateComponent],
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
      .overrideTemplate(UserRoleMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserRoleMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userRoleService = TestBed.inject(UserRoleMySuffixService);
    utilisateurService = TestBed.inject(UtilisateurMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call UtilisateurMySuffix query and add missing value', () => {
      const userRole: IUserRoleMySuffix = { id: 456 };
      const utilisateur: IUtilisateurMySuffix = { id: 48831 };
      userRole.utilisateur = utilisateur;

      const utilisateurCollection: IUtilisateurMySuffix[] = [{ id: 15701 }];
      jest.spyOn(utilisateurService, 'query').mockReturnValue(of(new HttpResponse({ body: utilisateurCollection })));
      const additionalUtilisateurMySuffixes = [utilisateur];
      const expectedCollection: IUtilisateurMySuffix[] = [...additionalUtilisateurMySuffixes, ...utilisateurCollection];
      jest.spyOn(utilisateurService, 'addUtilisateurMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ userRole });
      comp.ngOnInit();

      expect(utilisateurService.query).toHaveBeenCalled();
      expect(utilisateurService.addUtilisateurMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        utilisateurCollection,
        ...additionalUtilisateurMySuffixes
      );
      expect(comp.utilisateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const userRole: IUserRoleMySuffix = { id: 456 };
      const utilisateur: IUtilisateurMySuffix = { id: 46616 };
      userRole.utilisateur = utilisateur;

      activatedRoute.data = of({ userRole });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(userRole));
      expect(comp.utilisateursSharedCollection).toContain(utilisateur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserRoleMySuffix>>();
      const userRole = { id: 123 };
      jest.spyOn(userRoleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userRole });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userRole }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(userRoleService.update).toHaveBeenCalledWith(userRole);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserRoleMySuffix>>();
      const userRole = new UserRoleMySuffix();
      jest.spyOn(userRoleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userRole });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userRole }));
      saveSubject.complete();

      // THEN
      expect(userRoleService.create).toHaveBeenCalledWith(userRole);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserRoleMySuffix>>();
      const userRole = { id: 123 };
      jest.spyOn(userRoleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userRole });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userRoleService.update).toHaveBeenCalledWith(userRole);
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
