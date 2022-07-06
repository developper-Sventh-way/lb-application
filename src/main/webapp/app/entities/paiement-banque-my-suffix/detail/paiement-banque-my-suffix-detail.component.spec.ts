import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaiementBanqueMySuffixDetailComponent } from './paiement-banque-my-suffix-detail.component';

describe('PaiementBanqueMySuffix Management Detail Component', () => {
  let comp: PaiementBanqueMySuffixDetailComponent;
  let fixture: ComponentFixture<PaiementBanqueMySuffixDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaiementBanqueMySuffixDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paiementBanque: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PaiementBanqueMySuffixDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaiementBanqueMySuffixDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paiementBanque on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paiementBanque).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
