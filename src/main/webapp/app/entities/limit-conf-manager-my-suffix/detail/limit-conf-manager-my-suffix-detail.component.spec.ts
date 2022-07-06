import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LimitConfManagerMySuffixDetailComponent } from './limit-conf-manager-my-suffix-detail.component';

describe('LimitConfManagerMySuffix Management Detail Component', () => {
  let comp: LimitConfManagerMySuffixDetailComponent;
  let fixture: ComponentFixture<LimitConfManagerMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LimitConfManagerMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ limitConfManager: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LimitConfManagerMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LimitConfManagerMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load limitConfManager on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.limitConfManager).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
