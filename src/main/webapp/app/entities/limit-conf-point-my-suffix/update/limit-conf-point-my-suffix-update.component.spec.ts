import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LimitConfPointMySuffixService } from '../service/limit-conf-point-my-suffix.service';
import { ILimitConfPointMySuffix, LimitConfPointMySuffix } from '../limit-conf-point-my-suffix.model';
import { IPointOfSaleMySuffix } from 'app/entities/point-of-sale-my-suffix/point-of-sale-my-suffix.model';
import { PointOfSaleMySuffixService } from 'app/entities/point-of-sale-my-suffix/service/point-of-sale-my-suffix.service';

import { LimitConfPointMySuffixUpdateComponent } from './limit-conf-point-my-suffix-update.component';

describe('LimitConfPointMySuffix Management Update Component', () => {
  let comp: LimitConfPointMySuffixUpdateComponent;
  let fixture: ComponentFixture<LimitConfPointMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let limitConfPointService: LimitConfPointMySuffixService;
  let pointOfSaleService: PointOfSaleMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LimitConfPointMySuffixUpdateComponent],
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
      .overrideTemplate(LimitConfPointMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LimitConfPointMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    limitConfPointService = TestBed.inject(LimitConfPointMySuffixService);
    pointOfSaleService = TestBed.inject(PointOfSaleMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PointOfSaleMySuffix query and add missing value', () => {
      const limitConfPoint: ILimitConfPointMySuffix = { id: 456 };
      const pointOfSale: IPointOfSaleMySuffix = { id: 24353 };
      limitConfPoint.pointOfSale = pointOfSale;

      const pointOfSaleCollection: IPointOfSaleMySuffix[] = [{ id: 70747 }];
      jest.spyOn(pointOfSaleService, 'query').mockReturnValue(of(new HttpResponse({ body: pointOfSaleCollection })));
      const additionalPointOfSaleMySuffixes = [pointOfSale];
      const expectedCollection: IPointOfSaleMySuffix[] = [...additionalPointOfSaleMySuffixes, ...pointOfSaleCollection];
      jest.spyOn(pointOfSaleService, 'addPointOfSaleMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ limitConfPoint });
      comp.ngOnInit();

      expect(pointOfSaleService.query).toHaveBeenCalled();
      expect(pointOfSaleService.addPointOfSaleMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        pointOfSaleCollection,
        ...additionalPointOfSaleMySuffixes
      );
      expect(comp.pointOfSalesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const limitConfPoint: ILimitConfPointMySuffix = { id: 456 };
      const pointOfSale: IPointOfSaleMySuffix = { id: 75375 };
      limitConfPoint.pointOfSale = pointOfSale;

      activatedRoute.data = of({ limitConfPoint });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(limitConfPoint));
      expect(comp.pointOfSalesSharedCollection).toContain(pointOfSale);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LimitConfPointMySuffix>>();
      const limitConfPoint = { id: 123 };
      jest.spyOn(limitConfPointService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ limitConfPoint });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: limitConfPoint }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(limitConfPointService.update).toHaveBeenCalledWith(limitConfPoint);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LimitConfPointMySuffix>>();
      const limitConfPoint = new LimitConfPointMySuffix();
      jest.spyOn(limitConfPointService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ limitConfPoint });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: limitConfPoint }));
      saveSubject.complete();

      // THEN
      expect(limitConfPointService.create).toHaveBeenCalledWith(limitConfPoint);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LimitConfPointMySuffix>>();
      const limitConfPoint = { id: 123 };
      jest.spyOn(limitConfPointService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ limitConfPoint });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(limitConfPointService.update).toHaveBeenCalledWith(limitConfPoint);
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
  });
});
