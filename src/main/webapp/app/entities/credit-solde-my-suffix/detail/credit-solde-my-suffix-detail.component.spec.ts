import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CreditSoldeMySuffixDetailComponent } from './credit-solde-my-suffix-detail.component';

describe('CreditSoldeMySuffix Management Detail Component', () => {
  let comp: CreditSoldeMySuffixDetailComponent;
  let fixture: ComponentFixture<CreditSoldeMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreditSoldeMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ creditSolde: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CreditSoldeMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CreditSoldeMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load creditSolde on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.creditSolde).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
