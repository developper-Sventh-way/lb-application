import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserPaymentMySuffixDetailComponent } from './user-payment-my-suffix-detail.component';

describe('UserPaymentMySuffix Management Detail Component', () => {
  let comp: UserPaymentMySuffixDetailComponent;
  let fixture: ComponentFixture<UserPaymentMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserPaymentMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userPayment: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserPaymentMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserPaymentMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userPayment on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userPayment).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
