import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserPaymentConfMySuffixService } from '../service/user-payment-conf-my-suffix.service';
import { IUserPaymentConfMySuffix, UserPaymentConfMySuffix } from '../user-payment-conf-my-suffix.model';

import { UserPaymentConfMySuffixUpdateComponent } from './user-payment-conf-my-suffix-update.component';

describe('UserPaymentConfMySuffix Management Update Component', () => {
  let comp: UserPaymentConfMySuffixUpdateComponent;
  let fixture: ComponentFixture<UserPaymentConfMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userPaymentConfService: UserPaymentConfMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserPaymentConfMySuffixUpdateComponent],
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
      .overrideTemplate(UserPaymentConfMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserPaymentConfMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userPaymentConfService = TestBed.inject(UserPaymentConfMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const userPaymentConf: IUserPaymentConfMySuffix = { id: 456 };

      activatedRoute.data = of({ userPaymentConf });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(userPaymentConf));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserPaymentConfMySuffix>>();
      const userPaymentConf = { id: 123 };
      jest.spyOn(userPaymentConfService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userPaymentConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userPaymentConf }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(userPaymentConfService.update).toHaveBeenCalledWith(userPaymentConf);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserPaymentConfMySuffix>>();
      const userPaymentConf = new UserPaymentConfMySuffix();
      jest.spyOn(userPaymentConfService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userPaymentConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userPaymentConf }));
      saveSubject.complete();

      // THEN
      expect(userPaymentConfService.create).toHaveBeenCalledWith(userPaymentConf);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserPaymentConfMySuffix>>();
      const userPaymentConf = { id: 123 };
      jest.spyOn(userPaymentConfService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userPaymentConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userPaymentConfService.update).toHaveBeenCalledWith(userPaymentConf);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
