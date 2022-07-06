import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CouponGratuitConfMySuffixDetailComponent } from './coupon-gratuit-conf-my-suffix-detail.component';

describe('CouponGratuitConfMySuffix Management Detail Component', () => {
  let comp: CouponGratuitConfMySuffixDetailComponent;
  let fixture: ComponentFixture<CouponGratuitConfMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CouponGratuitConfMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ couponGratuitConf: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CouponGratuitConfMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CouponGratuitConfMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load couponGratuitConf on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.couponGratuitConf).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
