import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PointOfSaleConfMySuffixService } from '../service/point-of-sale-conf-my-suffix.service';
import { IPointOfSaleConfMySuffix, PointOfSaleConfMySuffix } from '../point-of-sale-conf-my-suffix.model';

import { PointOfSaleConfMySuffixUpdateComponent } from './point-of-sale-conf-my-suffix-update.component';

describe('PointOfSaleConfMySuffix Management Update Component', () => {
  let comp: PointOfSaleConfMySuffixUpdateComponent;
  let fixture: ComponentFixture<PointOfSaleConfMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pointOfSaleConfService: PointOfSaleConfMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PointOfSaleConfMySuffixUpdateComponent],
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
      .overrideTemplate(PointOfSaleConfMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PointOfSaleConfMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pointOfSaleConfService = TestBed.inject(PointOfSaleConfMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pointOfSaleConf: IPointOfSaleConfMySuffix = { id: 456 };

      activatedRoute.data = of({ pointOfSaleConf });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pointOfSaleConf));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PointOfSaleConfMySuffix>>();
      const pointOfSaleConf = { id: 123 };
      jest.spyOn(pointOfSaleConfService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointOfSaleConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointOfSaleConf }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pointOfSaleConfService.update).toHaveBeenCalledWith(pointOfSaleConf);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PointOfSaleConfMySuffix>>();
      const pointOfSaleConf = new PointOfSaleConfMySuffix();
      jest.spyOn(pointOfSaleConfService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointOfSaleConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointOfSaleConf }));
      saveSubject.complete();

      // THEN
      expect(pointOfSaleConfService.create).toHaveBeenCalledWith(pointOfSaleConf);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PointOfSaleConfMySuffix>>();
      const pointOfSaleConf = { id: 123 };
      jest.spyOn(pointOfSaleConfService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointOfSaleConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pointOfSaleConfService.update).toHaveBeenCalledWith(pointOfSaleConf);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
