import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LimitConfBorletteMySuffixService } from '../service/limit-conf-borlette-my-suffix.service';
import { ILimitConfBorletteMySuffix, LimitConfBorletteMySuffix } from '../limit-conf-borlette-my-suffix.model';
import { IBorletteConfMySuffix } from 'app/entities/borlette-conf-my-suffix/borlette-conf-my-suffix.model';
import { BorletteConfMySuffixService } from 'app/entities/borlette-conf-my-suffix/service/borlette-conf-my-suffix.service';

import { LimitConfBorletteMySuffixUpdateComponent } from './limit-conf-borlette-my-suffix-update.component';

describe('LimitConfBorletteMySuffix Management Update Component', () => {
  let comp: LimitConfBorletteMySuffixUpdateComponent;
  let fixture: ComponentFixture<LimitConfBorletteMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let limitConfBorletteService: LimitConfBorletteMySuffixService;
  let borletteConfService: BorletteConfMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LimitConfBorletteMySuffixUpdateComponent],
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
      .overrideTemplate(LimitConfBorletteMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LimitConfBorletteMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    limitConfBorletteService = TestBed.inject(LimitConfBorletteMySuffixService);
    borletteConfService = TestBed.inject(BorletteConfMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call BorletteConfMySuffix query and add missing value', () => {
      const limitConfBorlette: ILimitConfBorletteMySuffix = { id: 456 };
      const borletteConf: IBorletteConfMySuffix = { id: 45823 };
      limitConfBorlette.borletteConf = borletteConf;

      const borletteConfCollection: IBorletteConfMySuffix[] = [{ id: 62991 }];
      jest.spyOn(borletteConfService, 'query').mockReturnValue(of(new HttpResponse({ body: borletteConfCollection })));
      const additionalBorletteConfMySuffixes = [borletteConf];
      const expectedCollection: IBorletteConfMySuffix[] = [...additionalBorletteConfMySuffixes, ...borletteConfCollection];
      jest.spyOn(borletteConfService, 'addBorletteConfMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ limitConfBorlette });
      comp.ngOnInit();

      expect(borletteConfService.query).toHaveBeenCalled();
      expect(borletteConfService.addBorletteConfMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        borletteConfCollection,
        ...additionalBorletteConfMySuffixes
      );
      expect(comp.borletteConfsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const limitConfBorlette: ILimitConfBorletteMySuffix = { id: 456 };
      const borletteConf: IBorletteConfMySuffix = { id: 83974 };
      limitConfBorlette.borletteConf = borletteConf;

      activatedRoute.data = of({ limitConfBorlette });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(limitConfBorlette));
      expect(comp.borletteConfsSharedCollection).toContain(borletteConf);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LimitConfBorletteMySuffix>>();
      const limitConfBorlette = { id: 123 };
      jest.spyOn(limitConfBorletteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ limitConfBorlette });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: limitConfBorlette }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(limitConfBorletteService.update).toHaveBeenCalledWith(limitConfBorlette);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LimitConfBorletteMySuffix>>();
      const limitConfBorlette = new LimitConfBorletteMySuffix();
      jest.spyOn(limitConfBorletteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ limitConfBorlette });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: limitConfBorlette }));
      saveSubject.complete();

      // THEN
      expect(limitConfBorletteService.create).toHaveBeenCalledWith(limitConfBorlette);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LimitConfBorletteMySuffix>>();
      const limitConfBorlette = { id: 123 };
      jest.spyOn(limitConfBorletteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ limitConfBorlette });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(limitConfBorletteService.update).toHaveBeenCalledWith(limitConfBorlette);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackBorletteConfMySuffixById', () => {
      it('Should return tracked BorletteConfMySuffix primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBorletteConfMySuffixById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
