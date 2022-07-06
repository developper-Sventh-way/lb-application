import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CreditSoldeMySuffixService } from '../service/credit-solde-my-suffix.service';
import { ICreditSoldeMySuffix, CreditSoldeMySuffix } from '../credit-solde-my-suffix.model';

import { CreditSoldeMySuffixUpdateComponent } from './credit-solde-my-suffix-update.component';

describe('CreditSoldeMySuffix Management Update Component', () => {
  let comp: CreditSoldeMySuffixUpdateComponent;
  let fixture: ComponentFixture<CreditSoldeMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let creditSoldeService: CreditSoldeMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CreditSoldeMySuffixUpdateComponent],
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
      .overrideTemplate(CreditSoldeMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CreditSoldeMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    creditSoldeService = TestBed.inject(CreditSoldeMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const creditSolde: ICreditSoldeMySuffix = { id: 456 };

      activatedRoute.data = of({ creditSolde });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(creditSolde));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CreditSoldeMySuffix>>();
      const creditSolde = { id: 123 };
      jest.spyOn(creditSoldeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ creditSolde });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: creditSolde }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(creditSoldeService.update).toHaveBeenCalledWith(creditSolde);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CreditSoldeMySuffix>>();
      const creditSolde = new CreditSoldeMySuffix();
      jest.spyOn(creditSoldeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ creditSolde });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: creditSolde }));
      saveSubject.complete();

      // THEN
      expect(creditSoldeService.create).toHaveBeenCalledWith(creditSolde);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CreditSoldeMySuffix>>();
      const creditSolde = { id: 123 };
      jest.spyOn(creditSoldeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ creditSolde });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(creditSoldeService.update).toHaveBeenCalledWith(creditSolde);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
