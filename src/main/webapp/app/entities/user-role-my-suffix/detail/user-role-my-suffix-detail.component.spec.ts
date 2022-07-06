import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserRoleMySuffixDetailComponent } from './user-role-my-suffix-detail.component';

describe('UserRoleMySuffix Management Detail Component', () => {
  let comp: UserRoleMySuffixDetailComponent;
  let fixture: ComponentFixture<UserRoleMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserRoleMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userRole: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserRoleMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserRoleMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userRole on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userRole).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
