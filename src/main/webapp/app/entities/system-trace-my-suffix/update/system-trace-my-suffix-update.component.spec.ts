import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SystemTraceMySuffixService } from '../service/system-trace-my-suffix.service';
import { ISystemTraceMySuffix, SystemTraceMySuffix } from '../system-trace-my-suffix.model';

import { SystemTraceMySuffixUpdateComponent } from './system-trace-my-suffix-update.component';

describe('SystemTraceMySuffix Management Update Component', () => {
  let comp: SystemTraceMySuffixUpdateComponent;
  let fixture: ComponentFixture<SystemTraceMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let systemTraceService: SystemTraceMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SystemTraceMySuffixUpdateComponent],
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
      .overrideTemplate(SystemTraceMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SystemTraceMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    systemTraceService = TestBed.inject(SystemTraceMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const systemTrace: ISystemTraceMySuffix = { id: 456 };

      activatedRoute.data = of({ systemTrace });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(systemTrace));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SystemTraceMySuffix>>();
      const systemTrace = { id: 123 };
      jest.spyOn(systemTraceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ systemTrace });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: systemTrace }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(systemTraceService.update).toHaveBeenCalledWith(systemTrace);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SystemTraceMySuffix>>();
      const systemTrace = new SystemTraceMySuffix();
      jest.spyOn(systemTraceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ systemTrace });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: systemTrace }));
      saveSubject.complete();

      // THEN
      expect(systemTraceService.create).toHaveBeenCalledWith(systemTrace);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SystemTraceMySuffix>>();
      const systemTrace = { id: 123 };
      jest.spyOn(systemTraceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ systemTrace });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(systemTraceService.update).toHaveBeenCalledWith(systemTrace);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
