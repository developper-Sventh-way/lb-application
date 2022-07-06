import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PointOfSaleMembershipMySuffixDetailComponent } from './point-of-sale-membership-my-suffix-detail.component';

describe('PointOfSaleMembershipMySuffix Management Detail Component', () => {
  let comp: PointOfSaleMembershipMySuffixDetailComponent;
  let fixture: ComponentFixture<PointOfSaleMembershipMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PointOfSaleMembershipMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pointOfSaleMembership: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PointOfSaleMembershipMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PointOfSaleMembershipMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pointOfSaleMembership on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pointOfSaleMembership).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
