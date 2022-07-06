import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PointOfSaleConfMySuffixDetailComponent } from './point-of-sale-conf-my-suffix-detail.component';

describe('PointOfSaleConfMySuffix Management Detail Component', () => {
  let comp: PointOfSaleConfMySuffixDetailComponent;
  let fixture: ComponentFixture<PointOfSaleConfMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PointOfSaleConfMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pointOfSaleConf: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PointOfSaleConfMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PointOfSaleConfMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pointOfSaleConf on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pointOfSaleConf).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
