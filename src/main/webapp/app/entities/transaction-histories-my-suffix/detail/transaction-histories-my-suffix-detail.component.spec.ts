import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TransactionHistoriesMySuffixDetailComponent } from './transaction-histories-my-suffix-detail.component';

describe('TransactionHistoriesMySuffix Management Detail Component', () => {
  let comp: TransactionHistoriesMySuffixDetailComponent;
  let fixture: ComponentFixture<TransactionHistoriesMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransactionHistoriesMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ transactionHistories: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TransactionHistoriesMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TransactionHistoriesMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load transactionHistories on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.transactionHistories).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
