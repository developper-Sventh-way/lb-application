import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MembershipConfMySuffixService } from '../service/membership-conf-my-suffix.service';
import { IMembershipConfMySuffix, MembershipConfMySuffix } from '../membership-conf-my-suffix.model';

import { MembershipConfMySuffixUpdateComponent } from './membership-conf-my-suffix-update.component';

describe('MembershipConfMySuffix Management Update Component', () => {
  let comp: MembershipConfMySuffixUpdateComponent;
  let fixture: ComponentFixture<MembershipConfMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let membershipConfService: MembershipConfMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MembershipConfMySuffixUpdateComponent],
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
      .overrideTemplate(MembershipConfMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MembershipConfMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    membershipConfService = TestBed.inject(MembershipConfMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const membershipConf: IMembershipConfMySuffix = { id: 456 };

      activatedRoute.data = of({ membershipConf });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(membershipConf));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MembershipConfMySuffix>>();
      const membershipConf = { id: 123 };
      jest.spyOn(membershipConfService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membershipConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: membershipConf }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(membershipConfService.update).toHaveBeenCalledWith(membershipConf);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MembershipConfMySuffix>>();
      const membershipConf = new MembershipConfMySuffix();
      jest.spyOn(membershipConfService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membershipConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: membershipConf }));
      saveSubject.complete();

      // THEN
      expect(membershipConfService.create).toHaveBeenCalledWith(membershipConf);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MembershipConfMySuffix>>();
      const membershipConf = { id: 123 };
      jest.spyOn(membershipConfService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membershipConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(membershipConfService.update).toHaveBeenCalledWith(membershipConf);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
