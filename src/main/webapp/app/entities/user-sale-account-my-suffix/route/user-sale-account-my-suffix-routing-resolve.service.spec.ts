import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IUserSaleAccountMySuffix, UserSaleAccountMySuffix } from '../user-sale-account-my-suffix.model';
import { UserSaleAccountMySuffixService } from '../service/user-sale-account-my-suffix.service';

import { UserSaleAccountMySuffixRoutingResolveService } from './user-sale-account-my-suffix-routing-resolve.service';

describe('UserSaleAccountMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: UserSaleAccountMySuffixRoutingResolveService;
  let service: UserSaleAccountMySuffixService;
  let resultUserSaleAccountMySuffix: IUserSaleAccountMySuffix | undefined;

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
    routingResolveService = TestBed.inject(UserSaleAccountMySuffixRoutingResolveService);
    service = TestBed.inject(UserSaleAccountMySuffixService);
    resultUserSaleAccountMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return IUserSaleAccountMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserSaleAccountMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUserSaleAccountMySuffix).toEqual({ id: 123 });
    });

    it('should return new IUserSaleAccountMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserSaleAccountMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultUserSaleAccountMySuffix).toEqual(new UserSaleAccountMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as UserSaleAccountMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserSaleAccountMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUserSaleAccountMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
