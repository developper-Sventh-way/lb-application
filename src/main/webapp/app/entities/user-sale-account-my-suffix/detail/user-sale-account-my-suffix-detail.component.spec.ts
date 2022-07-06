import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserSaleAccountMySuffixDetailComponent } from './user-sale-account-my-suffix-detail.component';

describe('UserSaleAccountMySuffix Management Detail Component', () => {
  let comp: UserSaleAccountMySuffixDetailComponent;
  let fixture: ComponentFixture<UserSaleAccountMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserSaleAccountMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userSaleAccount: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserSaleAccountMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserSaleAccountMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userSaleAccount on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userSaleAccount).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
