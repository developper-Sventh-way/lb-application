import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PointOfSaleMySuffixDetailComponent } from './point-of-sale-my-suffix-detail.component';

describe('PointOfSaleMySuffix Management Detail Component', () => {
  let comp: PointOfSaleMySuffixDetailComponent;
  let fixture: ComponentFixture<PointOfSaleMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PointOfSaleMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pointOfSale: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PointOfSaleMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PointOfSaleMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pointOfSale on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pointOfSale).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
