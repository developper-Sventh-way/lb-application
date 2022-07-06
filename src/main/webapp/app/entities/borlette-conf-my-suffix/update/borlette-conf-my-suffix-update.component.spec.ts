import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BorletteConfMySuffixService } from '../service/borlette-conf-my-suffix.service';
import { IBorletteConfMySuffix, BorletteConfMySuffix } from '../borlette-conf-my-suffix.model';

import { BorletteConfMySuffixUpdateComponent } from './borlette-conf-my-suffix-update.component';

describe('BorletteConfMySuffix Management Update Component', () => {
  let comp: BorletteConfMySuffixUpdateComponent;
  let fixture: ComponentFixture<BorletteConfMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let borletteConfService: BorletteConfMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BorletteConfMySuffixUpdateComponent],
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
      .overrideTemplate(BorletteConfMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BorletteConfMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    borletteConfService = TestBed.inject(BorletteConfMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const borletteConf: IBorletteConfMySuffix = { id: 456 };

      activatedRoute.data = of({ borletteConf });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(borletteConf));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BorletteConfMySuffix>>();
      const borletteConf = { id: 123 };
      jest.spyOn(borletteConfService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ borletteConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: borletteConf }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(borletteConfService.update).toHaveBeenCalledWith(borletteConf);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BorletteConfMySuffix>>();
      const borletteConf = new BorletteConfMySuffix();
      jest.spyOn(borletteConfService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ borletteConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: borletteConf }));
      saveSubject.complete();

      // THEN
      expect(borletteConfService.create).toHaveBeenCalledWith(borletteConf);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BorletteConfMySuffix>>();
      const borletteConf = { id: 123 };
      jest.spyOn(borletteConfService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ borletteConf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(borletteConfService.update).toHaveBeenCalledWith(borletteConf);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
