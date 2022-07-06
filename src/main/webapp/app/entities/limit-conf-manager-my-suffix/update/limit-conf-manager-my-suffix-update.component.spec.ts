import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LimitConfManagerMySuffixService } from '../service/limit-conf-manager-my-suffix.service';
import { ILimitConfManagerMySuffix, LimitConfManagerMySuffix } from '../limit-conf-manager-my-suffix.model';
import { IMembershipConfMySuffix } from 'app/entities/membership-conf-my-suffix/membership-conf-my-suffix.model';
import { MembershipConfMySuffixService } from 'app/entities/membership-conf-my-suffix/service/membership-conf-my-suffix.service';

import { LimitConfManagerMySuffixUpdateComponent } from './limit-conf-manager-my-suffix-update.component';

describe('LimitConfManagerMySuffix Management Update Component', () => {
  let comp: LimitConfManagerMySuffixUpdateComponent;
  let fixture: ComponentFixture<LimitConfManagerMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let limitConfManagerService: LimitConfManagerMySuffixService;
  let membershipConfService: MembershipConfMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LimitConfManagerMySuffixUpdateComponent],
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
      .overrideTemplate(LimitConfManagerMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LimitConfManagerMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    limitConfManagerService = TestBed.inject(LimitConfManagerMySuffixService);
    membershipConfService = TestBed.inject(MembershipConfMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MembershipConfMySuffix query and add missing value', () => {
      const limitConfManager: ILimitConfManagerMySuffix = { id: 456 };
      const membershipConf: IMembershipConfMySuffix = { id: 38763 };
      limitConfManager.membershipConf = membershipConf;

      const membershipConfCollection: IMembershipConfMySuffix[] = [{ id: 81298 }];
      jest.spyOn(membershipConfService, 'query').mockReturnValue(of(new HttpResponse({ body: membershipConfCollection })));
      const additionalMembershipConfMySuffixes = [membershipConf];
      const expectedCollection: IMembershipConfMySuffix[] = [...additionalMembershipConfMySuffixes, ...membershipConfCollection];
      jest.spyOn(membershipConfService, 'addMembershipConfMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ limitConfManager });
      comp.ngOnInit();

      expect(membershipConfService.query).toHaveBeenCalled();
      expect(membershipConfService.addMembershipConfMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        membershipConfCollection,
        ...additionalMembershipConfMySuffixes
      );
      expect(comp.membershipConfsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const limitConfManager: ILimitConfManagerMySuffix = { id: 456 };
      const membershipConf: IMembershipConfMySuffix = { id: 7315 };
      limitConfManager.membershipConf = membershipConf;

      activatedRoute.data = of({ limitConfManager });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(limitConfManager));
      expect(comp.membershipConfsSharedCollection).toContain(membershipConf);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LimitConfManagerMySuffix>>();
      const limitConfManager = { id: 123 };
      jest.spyOn(limitConfManagerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ limitConfManager });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: limitConfManager }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(limitConfManagerService.update).toHaveBeenCalledWith(limitConfManager);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LimitConfManagerMySuffix>>();
      const limitConfManager = new LimitConfManagerMySuffix();
      jest.spyOn(limitConfManagerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ limitConfManager });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: limitConfManager }));
      saveSubject.complete();

      // THEN
      expect(limitConfManagerService.create).toHaveBeenCalledWith(limitConfManager);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LimitConfManagerMySuffix>>();
      const limitConfManager = { id: 123 };
      jest.spyOn(limitConfManagerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ limitConfManager });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(limitConfManagerService.update).toHaveBeenCalledWith(limitConfManager);
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
  });
});
