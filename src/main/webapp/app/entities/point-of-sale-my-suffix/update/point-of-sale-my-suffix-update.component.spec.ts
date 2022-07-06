import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PointOfSaleMySuffixService } from '../service/point-of-sale-my-suffix.service';
import { IPointOfSaleMySuffix, PointOfSaleMySuffix } from '../point-of-sale-my-suffix.model';
import { IPointOfSaleConfMySuffix } from 'app/entities/point-of-sale-conf-my-suffix/point-of-sale-conf-my-suffix.model';
import { PointOfSaleConfMySuffixService } from 'app/entities/point-of-sale-conf-my-suffix/service/point-of-sale-conf-my-suffix.service';

import { PointOfSaleMySuffixUpdateComponent } from './point-of-sale-my-suffix-update.component';

describe('PointOfSaleMySuffix Management Update Component', () => {
  let comp: PointOfSaleMySuffixUpdateComponent;
  let fixture: ComponentFixture<PointOfSaleMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pointOfSaleService: PointOfSaleMySuffixService;
  let pointOfSaleConfService: PointOfSaleConfMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PointOfSaleMySuffixUpdateComponent],
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
      .overrideTemplate(PointOfSaleMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PointOfSaleMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pointOfSaleService = TestBed.inject(PointOfSaleMySuffixService);
    pointOfSaleConfService = TestBed.inject(PointOfSaleConfMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call pointOfSaleConf query and add missing value', () => {
      const pointOfSale: IPointOfSaleMySuffix = { id: 456 };
      const pointOfSaleConf: IPointOfSaleConfMySuffix = { id: 52028 };
      pointOfSale.pointOfSaleConf = pointOfSaleConf;

      const pointOfSaleConfCollection: IPointOfSaleConfMySuffix[] = [{ id: 52066 }];
      jest.spyOn(pointOfSaleConfService, 'query').mockReturnValue(of(new HttpResponse({ body: pointOfSaleConfCollection })));
      const expectedCollection: IPointOfSaleConfMySuffix[] = [pointOfSaleConf, ...pointOfSaleConfCollection];
      jest.spyOn(pointOfSaleConfService, 'addPointOfSaleConfMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pointOfSale });
      comp.ngOnInit();

      expect(pointOfSaleConfService.query).toHaveBeenCalled();
      expect(pointOfSaleConfService.addPointOfSaleConfMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        pointOfSaleConfCollection,
        pointOfSaleConf
      );
      expect(comp.pointOfSaleConfsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pointOfSale: IPointOfSaleMySuffix = { id: 456 };
      const pointOfSaleConf: IPointOfSaleConfMySuffix = { id: 85415 };
      pointOfSale.pointOfSaleConf = pointOfSaleConf;

      activatedRoute.data = of({ pointOfSale });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pointOfSale));
      expect(comp.pointOfSaleConfsCollection).toContain(pointOfSaleConf);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PointOfSaleMySuffix>>();
      const pointOfSale = { id: 123 };
      jest.spyOn(pointOfSaleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointOfSale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointOfSale }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pointOfSaleService.update).toHaveBeenCalledWith(pointOfSale);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PointOfSaleMySuffix>>();
      const pointOfSale = new PointOfSaleMySuffix();
      jest.spyOn(pointOfSaleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointOfSale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointOfSale }));
      saveSubject.complete();

      // THEN
      expect(pointOfSaleService.create).toHaveBeenCalledWith(pointOfSale);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PointOfSaleMySuffix>>();
      const pointOfSale = { id: 123 };
      jest.spyOn(pointOfSaleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointOfSale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pointOfSaleService.update).toHaveBeenCalledWith(pointOfSale);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPointOfSaleConfMySuffixById', () => {
      it('Should return tracked PointOfSaleConfMySuffix primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPointOfSaleConfMySuffixById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
