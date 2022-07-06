import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IUserPaymentMySuffix, UserPaymentMySuffix } from '../user-payment-my-suffix.model';
import { UserPaymentMySuffixService } from '../service/user-payment-my-suffix.service';

import { UserPaymentMySuffixRoutingResolveService } from './user-payment-my-suffix-routing-resolve.service';

describe('UserPaymentMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: UserPaymentMySuffixRoutingResolveService;
  let service: UserPaymentMySuffixService;
  let resultUserPaymentMySuffix: IUserPaymentMySuffix | undefined;

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
    routingResolveService = TestBed.inject(UserPaymentMySuffixRoutingResolveService);
    service = TestBed.inject(UserPaymentMySuffixService);
    resultUserPaymentMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return IUserPaymentMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserPaymentMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUserPaymentMySuffix).toEqual({ id: 123 });
    });

    it('should return new IUserPaymentMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserPaymentMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultUserPaymentMySuffix).toEqual(new UserPaymentMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as UserPaymentMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserPaymentMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUserPaymentMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
