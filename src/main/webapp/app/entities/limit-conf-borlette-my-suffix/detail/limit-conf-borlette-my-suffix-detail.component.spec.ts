import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LimitConfBorletteMySuffixDetailComponent } from './limit-conf-borlette-my-suffix-detail.component';

describe('LimitConfBorletteMySuffix Management Detail Component', () => {
  let comp: LimitConfBorletteMySuffixDetailComponent;
  let fixture: ComponentFixture<LimitConfBorletteMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LimitConfBorletteMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ limitConfBorlette: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LimitConfBorletteMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LimitConfBorletteMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load limitConfBorlette on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.limitConfBorlette).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
