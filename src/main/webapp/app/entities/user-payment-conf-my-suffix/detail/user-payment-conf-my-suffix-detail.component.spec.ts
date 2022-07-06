import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserPaymentConfMySuffixDetailComponent } from './user-payment-conf-my-suffix-detail.component';

describe('UserPaymentConfMySuffix Management Detail Component', () => {
  let comp: UserPaymentConfMySuffixDetailComponent;
  let fixture: ComponentFixture<UserPaymentConfMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserPaymentConfMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userPaymentConf: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserPaymentConfMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserPaymentConfMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userPaymentConf on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userPaymentConf).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
