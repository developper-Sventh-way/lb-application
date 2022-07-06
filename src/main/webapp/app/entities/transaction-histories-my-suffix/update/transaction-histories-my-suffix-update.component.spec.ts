import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransactionHistoriesMySuffixService } from '../service/transaction-histories-my-suffix.service';
import { ITransactionHistoriesMySuffix, TransactionHistoriesMySuffix } from '../transaction-histories-my-suffix.model';

import { TransactionHistoriesMySuffixUpdateComponent } from './transaction-histories-my-suffix-update.component';

describe('TransactionHistoriesMySuffix Management Update Component', () => {
  let comp: TransactionHistoriesMySuffixUpdateComponent;
  let fixture: ComponentFixture<TransactionHistoriesMySuffixUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transactionHistoriesService: TransactionHistoriesMySuffixService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransactionHistoriesMySuffixUpdateComponent],
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
      .overrideTemplate(TransactionHistoriesMySuffixUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransactionHistoriesMySuffixUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transactionHistoriesService = TestBed.inject(TransactionHistoriesMySuffixService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const transactionHistories: ITransactionHistoriesMySuffix = { id: 456 };

      activatedRoute.data = of({ transactionHistories });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(transactionHistories));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransactionHistoriesMySuffix>>();
      const transactionHistories = { id: 123 };
      jest.spyOn(transactionHistoriesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactionHistories });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transactionHistories }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(transactionHistoriesService.update).toHaveBeenCalledWith(transactionHistories);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransactionHistoriesMySuffix>>();
      const transactionHistories = new TransactionHistoriesMySuffix();
      jest.spyOn(transactionHistoriesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactionHistories });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transactionHistories }));
      saveSubject.complete();

      // THEN
      expect(transactionHistoriesService.create).toHaveBeenCalledWith(transactionHistories);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransactionHistoriesMySuffix>>();
      const transactionHistories = { id: 123 };
      jest.spyOn(transactionHistoriesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactionHistories });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transactionHistoriesService.update).toHaveBeenCalledWith(transactionHistories);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
