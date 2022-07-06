import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IUserPaymentConfMySuffix, UserPaymentConfMySuffix } from '../user-payment-conf-my-suffix.model';
import { UserPaymentConfMySuffixService } from '../service/user-payment-conf-my-suffix.service';

import { UserPaymentConfMySuffixRoutingResolveService } from './user-payment-conf-my-suffix-routing-resolve.service';

describe('UserPaymentConfMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: UserPaymentConfMySuffixRoutingResolveService;
  let service: UserPaymentConfMySuffixService;
  let resultUserPaymentConfMySuffix: IUserPaymentConfMySuffix | undefined;

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
    routingResolveService = TestBed.inject(UserPaymentConfMySuffixRoutingResolveService);
    service = TestBed.inject(UserPaymentConfMySuffixService);
    resultUserPaymentConfMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return IUserPaymentConfMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserPaymentConfMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUserPaymentConfMySuffix).toEqual({ id: 123 });
    });

    it('should return new IUserPaymentConfMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserPaymentConfMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultUserPaymentConfMySuffix).toEqual(new UserPaymentConfMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as UserPaymentConfMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserPaymentConfMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUserPaymentConfMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
