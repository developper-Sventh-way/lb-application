import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LimitConfPointMySuffixDetailComponent } from './limit-conf-point-my-suffix-detail.component';

describe('LimitConfPointMySuffix Management Detail Component', () => {
  let comp: LimitConfPointMySuffixDetailComponent;
  let fixture: ComponentFixture<LimitConfPointMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LimitConfPointMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ limitConfPoint: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LimitConfPointMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LimitConfPointMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load limitConfPoint on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.limitConfPoint).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
