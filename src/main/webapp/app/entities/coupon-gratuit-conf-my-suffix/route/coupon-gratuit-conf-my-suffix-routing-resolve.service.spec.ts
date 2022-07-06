import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICouponGratuitConfMySuffix, CouponGratuitConfMySuffix } from '../coupon-gratuit-conf-my-suffix.model';
import { CouponGratuitConfMySuffixService } from '../service/coupon-gratuit-conf-my-suffix.service';

import { CouponGratuitConfMySuffixRoutingResolveService } from './coupon-gratuit-conf-my-suffix-routing-resolve.service';

describe('CouponGratuitConfMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CouponGratuitConfMySuffixRoutingResolveService;
  let service: CouponGratuitConfMySuffixService;
  let resultCouponGratuitConfMySuffix: ICouponGratuitConfMySuffix | undefined;

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
    routingResolveService = TestBed.inject(CouponGratuitConfMySuffixRoutingResolveService);
    service = TestBed.inject(CouponGratuitConfMySuffixService);
    resultCouponGratuitConfMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return ICouponGratuitConfMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCouponGratuitConfMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCouponGratuitConfMySuffix).toEqual({ id: 123 });
    });

    it('should return new ICouponGratuitConfMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCouponGratuitConfMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCouponGratuitConfMySuffix).toEqual(new CouponGratuitConfMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CouponGratuitConfMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCouponGratuitConfMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCouponGratuitConfMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
