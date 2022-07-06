import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPaiementBanqueMySuffix, PaiementBanqueMySuffix } from '../paiement-banque-my-suffix.model';
import { PaiementBanqueMySuffixService } from '../service/paiement-banque-my-suffix.service';

import { PaiementBanqueMySuffixRoutingResolveService } from './paiement-banque-my-suffix-routing-resolve.service';

describe('PaiementBanqueMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PaiementBanqueMySuffixRoutingResolveService;
  let service: PaiementBanqueMySuffixService;
  let resultPaiementBanqueMySuffix: IPaiementBanqueMySuffix | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(PaiementBanqueMySuffixRoutingResolveService);
    service = TestBed.inject(PaiementBanqueMySuffixService);
    resultPaiementBanqueMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return IPaiementBanqueMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaiementBanqueMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPaiementBanqueMySuffix).toEqual({ id: 123 });
    });

    it('should return new IPaiementBanqueMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaiementBanqueMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPaiementBanqueMySuffix).toEqual(new PaiementBanqueMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PaiementBanqueMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaiementBanqueMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPaiementBanqueMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
