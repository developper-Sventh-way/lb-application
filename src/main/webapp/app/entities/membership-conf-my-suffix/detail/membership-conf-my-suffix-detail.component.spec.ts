import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MembershipConfMySuffixDetailComponent } from './membership-conf-my-suffix-detail.component';

describe('MembershipConfMySuffix Management Detail Component', () => {
  let comp: MembershipConfMySuffixDetailComponent;
  let fixture: ComponentFixture<MembershipConfMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MembershipConfMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ membershipConf: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MembershipConfMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MembershipConfMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load membershipConf on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.membershipConf).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
