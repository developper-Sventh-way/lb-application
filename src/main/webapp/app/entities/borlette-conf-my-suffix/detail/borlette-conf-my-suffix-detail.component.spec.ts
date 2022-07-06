import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BorletteConfMySuffixDetailComponent } from './borlette-conf-my-suffix-detail.component';

describe('BorletteConfMySuffix Management Detail Component', () => {
  let comp: BorletteConfMySuffixDetailComponent;
  let fixture: ComponentFixture<BorletteConfMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BorletteConfMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ borletteConf: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BorletteConfMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BorletteConfMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load borletteConf on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.borletteConf).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
