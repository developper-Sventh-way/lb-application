import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CouponGratuitConfMySuffixService } from '../service/coupon-gratuit-conf-my-suffix.service';
import { ICouponGratuitConfMySuffix, CouponGratuitConfMySuffix } from '../coupon-gratuit-conf-my-suffix.model';

import { CouponGratuitConfMySuffixUpdateComponent } from './coupon-gratuit-conf-my-suffix-update.component';

describe('CouponGratuitConfMySuffix Management Update Component', () => {
  let comp: CouponGratuitConfMySuffixUpdateComponent;
  let fixture: ComponentFixture<CouponGratuitConfMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let couponGratuitConfService: CouponGratuitConfMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CouponGratuitConfMySuffixUpdateComponent],
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
      .overrideTemplate(CouponGratuitConfMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CouponGratuitConfMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    couponGratuitConfService = TestBed.inject(CouponGratuitConfMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const couponGratuitConf: ICouponGratuitConfMySuffix = { id: 456 };

      activatedRoute.data = of({ couponGratuitConf });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(couponGratuitConf));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CouponGratuitConfMySuffix>>();
      const couponGratuitConf = { id: 123 };
      jest.spyOn(couponGratuitConfService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ couponGratuitConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: couponGratuitConf }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(couponGratuitConfService.update).toHaveBeenCalledWith(couponGratuitConf);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CouponGratuitConfMySuffix>>();
      const couponGratuitConf = new CouponGratuitConfMySuffix();
      jest.spyOn(couponGratuitConfService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ couponGratuitConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: couponGratuitConf }));
      saveSubject.complete();

      // THEN
      expect(couponGratuitConfService.create).toHaveBeenCalledWith(couponGratuitConf);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CouponGratuitConfMySuffix>>();
      const couponGratuitConf = { id: 123 };
      jest.spyOn(couponGratuitConfService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ couponGratuitConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(couponGratuitConfService.update).toHaveBeenCalledWith(couponGratuitConf);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
