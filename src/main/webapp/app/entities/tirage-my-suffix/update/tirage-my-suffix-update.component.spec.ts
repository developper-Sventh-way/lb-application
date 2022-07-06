import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TirageMySuffixService } from '../service/tirage-my-suffix.service';
import { ITirageMySuffix, TirageMySuffix } from '../tirage-my-suffix.model';
import { IBorletteConfMySuffix } from 'app/entities/borlette-conf-my-suffix/borlette-conf-my-suffix.model';
import { BorletteConfMySuffixService } from 'app/entities/borlette-conf-my-suffix/service/borlette-conf-my-suffix.service';

import { TirageMySuffixUpdateComponent } from './tirage-my-suffix-update.component';

describe('TirageMySuffix Management Update Component', () => {
  let comp: TirageMySuffixUpdateComponent;
  let fixture: ComponentFixture<TirageMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tirageService: TirageMySuffixService;
  let borletteConfService: BorletteConfMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TirageMySuffixUpdateComponent],
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
      .overrideTemplate(TirageMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TirageMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tirageService = TestBed.inject(TirageMySuffixService);
    borletteConfService = TestBed.inject(BorletteConfMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call BorletteConfMySuffix query and add missing value', () => {
      const tirage: ITirageMySuffix = { id: 456 };
      const borletteConf: IBorletteConfMySuffix = { id: 99944 };
      tirage.borletteConf = borletteConf;

      const borletteConfCollection: IBorletteConfMySuffix[] = [{ id: 31735 }];
      jest.spyOn(borletteConfService, 'query').mockReturnValue(of(new HttpResponse({ body: borletteConfCollection })));
      const additionalBorletteConfMySuffixes = [borletteConf];
      const expectedCollection: IBorletteConfMySuffix[] = [...additionalBorletteConfMySuffixes, ...borletteConfCollection];
      jest.spyOn(borletteConfService, 'addBorletteConfMySuffixToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tirage });
      comp.ngOnInit();

      expect(borletteConfService.query).toHaveBeenCalled();
      expect(borletteConfService.addBorletteConfMySuffixToCollectionIfMissing).toHaveBeenCalledWith(
        borletteConfCollection,
        ...additionalBorletteConfMySuffixes
      );
      expect(comp.borletteConfsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tirage: ITirageMySuffix = { id: 456 };
      const borletteConf: IBorletteConfMySuffix = { id: 73232 };
      tirage.borletteConf = borletteConf;

      activatedRoute.data = of({ tirage });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tirage));
      expect(comp.borletteConfsSharedCollection).toContain(borletteConf);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TirageMySuffix>>();
      const tirage = { id: 123 };
      jest.spyOn(tirageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tirage });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tirage }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tirageService.update).toHaveBeenCalledWith(tirage);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TirageMySuffix>>();
      const tirage = new TirageMySuffix();
      jest.spyOn(tirageService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tirage });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tirage }));
      saveSubject.complete();

      // THEN
      expect(tirageService.create).toHaveBeenCalledWith(tirage);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TirageMySuffix>>();
      const tirage = { id: 123 };
      jest.spyOn(tirageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tirage });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tirageService.update).toHaveBeenCalledWith(tirage);
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
