import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICreditSoldeMySuffix, CreditSoldeMySuffix } from '../credit-solde-my-suffix.model';
import { CreditSoldeMySuffixService } from '../service/credit-solde-my-suffix.service';

import { CreditSoldeMySuffixRoutingResolveService } from './credit-solde-my-suffix-routing-resolve.service';

describe('CreditSoldeMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CreditSoldeMySuffixRoutingResolveService;
  let service: CreditSoldeMySuffixService;
  let resultCreditSoldeMySuffix: ICreditSoldeMySuffix | undefined;

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
    routingResolveService = TestBed.inject(CreditSoldeMySuffixRoutingResolveService);
    service = TestBed.inject(CreditSoldeMySuffixService);
    resultCreditSoldeMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return ICreditSoldeMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCreditSoldeMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCreditSoldeMySuffix).toEqual({ id: 123 });
    });

    it('should return new ICreditSoldeMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCreditSoldeMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCreditSoldeMySuffix).toEqual(new CreditSoldeMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CreditSoldeMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCreditSoldeMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCreditSoldeMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
