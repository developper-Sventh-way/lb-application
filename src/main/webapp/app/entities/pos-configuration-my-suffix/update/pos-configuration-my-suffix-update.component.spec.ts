import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { POSConfigurationMySuffixService } from '../service/pos-configuration-my-suffix.service';
import { IPOSConfigurationMySuffix, POSConfigurationMySuffix } from '../pos-configuration-my-suffix.model';

import { POSConfigurationMySuffixUpdateComponent } from './pos-configuration-my-suffix-update.component';

describe('POSConfigurationMySuffix Management Update Component', () => {
  let comp: POSConfigurationMySuffixUpdateComponent;
  let fixture: ComponentFixture<POSConfigurationMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pOSConfigurationService: POSConfigurationMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [POSConfigurationMySuffixUpdateComponent],
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
      .overrideTemplate(POSConfigurationMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(POSConfigurationMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pOSConfigurationService = TestBed.inject(POSConfigurationMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pOSConfiguration: IPOSConfigurationMySuffix = { id: 456 };

      activatedRoute.data = of({ pOSConfiguration });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pOSConfiguration));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<POSConfigurationMySuffix>>();
      const pOSConfiguration = { id: 123 };
      jest.spyOn(pOSConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pOSConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pOSConfiguration }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pOSConfigurationService.update).toHaveBeenCalledWith(pOSConfiguration);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<POSConfigurationMySuffix>>();
      const pOSConfiguration = new POSConfigurationMySuffix();
      jest.spyOn(pOSConfigurationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pOSConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pOSConfiguration }));
      saveSubject.complete();

      // THEN
      expect(pOSConfigurationService.create).toHaveBeenCalledWith(pOSConfiguration);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<POSConfigurationMySuffix>>();
      const pOSConfiguration = { id: 123 };
      jest.spyOn(pOSConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pOSConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pOSConfigurationService.update).toHaveBeenCalledWith(pOSConfiguration);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
