import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PointOfSaleMembershipMySuffixService } from '../service/point-of-sale-membership-my-suffix.service';
import { IPointOfSaleMembershipMySuffix, PointOfSaleMembershipMySuffix } from '../point-of-sale-membership-my-suffix.model';
import { IMembershipConfMySuffix } from 'app/entities/membership-conf-my-suffix/membership-conf-my-suffix.model';
import { MembershipConfMySuffixService } from 'app/entities/membership-conf-my-suffix/service/membership-conf-my-suffix.service';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { UtilisateurMySuffixService } from 'app/entities/utilisateur-my-suffix/service/utilisateur-my-suffix.service';

import { PointOfSaleMembershipMySuffixUpdateComponent } from './point-of-sale-membership-my-suffix-update.component';

describe('PointOfSaleMembershipMySuffix Management Update Component', () => {
  let comp: PointOfSaleMembershipMySuffixUpdateComponent;
  let fixture: ComponentFixture<PointOfSaleMembershipMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pointOfSaleMembershipService: PointOfSaleMembershipMySuffixService;
  let membershipConfService: MembershipConfMySuffixService;
  let utilisateurService: UtilisateurMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PointOfSaleMembershipMySuffixUpdateComponent],
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
      .overrideTemplate(PointOfSaleMembershipMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PointOfSaleMembershipMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pointOfSaleMembershipService = TestBed.inject(PointOfSaleMembershipMySuffixService);
    membershipConfService = TestBed.inject(MembershipConfMySuffixService);
    utilisateurService = TestBed.inject(UtilisateurMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MembershipConfMySuffix query and add missing value', () => {
      const pointOfSaleMembership: IPointOfSaleMembershipMySuffix = { id: 456 };
      const membershipConf: IMembershipConfMySuffix = { id: 9778 };
      pointOfSaleMembership.membershipConf = membershipConf;

      const membershipConfCollection: IMembershipConfMySuffix[] = [{ id: 41015 }];
      jest.spyOn(membershipConfService, 'query').mockReturnValue(of(new HttpResponse({ body: membershipConfCollection })));
      const additionalMembershipConfMySuffixes = [membershipConf];
      const expectedCollection: IMembershipConfMySuffix[] = [...additionalMembershipConfMySuffixes, ...membershipConfCollection];
      jest.spyOn(membershipConfService, 'addMembershipConfMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pointOfSaleMembership });
      comp.ngOnInit();

      expect(membershipConfService.query).toHaveBeenCalled();
      expect(membershipConfService.addMembershipConfMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        membershipConfCollection,
        ...additionalMembershipConfMySuffixes
      );
      expect(comp.membershipConfsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call UtilisateurMySuffix query and add missing value', () => {
      const pointOfSaleMembership: IPointOfSaleMembershipMySuffix = { id: 456 };
      const utilisateur: IUtilisateurMySuffix = { id: 17932 };
      pointOfSaleMembership.utilisateur = utilisateur;

      const utilisateurCollection: IUtilisateurMySuffix[] = [{ id: 50389 }];
      jest.spyOn(utilisateurService, 'query').mockReturnValue(of(new HttpResponse({ body: utilisateurCollection })));
      const additionalUtilisateurMySuffixes = [utilisateur];
      const expectedCollection: IUtilisateurMySuffix[] = [...additionalUtilisateurMySuffixes, ...utilisateurCollection];
      jest.spyOn(utilisateurService, 'addUtilisateurMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pointOfSaleMembership });
      comp.ngOnInit();

      expect(utilisateurService.query).toHaveBeenCalled();
      expect(utilisateurService.addUtilisateurMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        utilisateurCollection,
        ...additionalUtilisateurMySuffixes
      );
      expect(comp.utilisateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pointOfSaleMembership: IPointOfSaleMembershipMySuffix = { id: 456 };
      const membershipConf: IMembershipConfMySuffix = { id: 50875 };
      pointOfSaleMembership.membershipConf = membershipConf;
      const utilisateur: IUtilisateurMySuffix = { id: 40215 };
      pointOfSaleMembership.utilisateur = utilisateur;

      activatedRoute.data = of({ pointOfSaleMembership });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pointOfSaleMembership));
      expect(comp.membershipConfsSharedCollection).toContain(membershipConf);
      expect(comp.utilisateursSharedCollection).toContain(utilisateur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PointOfSaleMembershipMySuffix>>();
      const pointOfSaleMembership = { id: 123 };
      jest.spyOn(pointOfSaleMembershipService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointOfSaleMembership });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointOfSaleMembership }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pointOfSaleMembershipService.update).toHaveBeenCalledWith(pointOfSaleMembership);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PointOfSaleMembershipMySuffix>>();
      const pointOfSaleMembership = new PointOfSaleMembershipMySuffix();
      jest.spyOn(pointOfSaleMembershipService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointOfSaleMembership });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointOfSaleMembership }));
      saveSubject.complete();

      // THEN
      expect(pointOfSaleMembershipService.create).toHaveBeenCalledWith(pointOfSaleMembership);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PointOfSaleMembershipMySuffix>>();
      const pointOfSaleMembership = { id: 123 };
      jest.spyOn(pointOfSaleMembershipService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointOfSaleMembership });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pointOfSaleMembershipService.update).toHaveBeenCalledWith(pointOfSaleMembership);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackMembershipConfMySuffixById', () => {
      it('Should return tracked MembershipConfMySuffix primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMembershipConfMySuffixById(0, entity);
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
