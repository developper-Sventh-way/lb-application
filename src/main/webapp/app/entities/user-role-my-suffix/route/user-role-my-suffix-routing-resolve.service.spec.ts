import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IUserRoleMySuffix, UserRoleMySuffix } from '../user-role-my-suffix.model';
import { UserRoleMySuffixService } from '../service/user-role-my-suffix.service';

import { UserRoleMySuffixRoutingResolveService } from './user-role-my-suffix-routing-resolve.service';

describe('UserRoleMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: UserRoleMySuffixRoutingResolveService;
  let service: UserRoleMySuffixService;
  let resultUserRoleMySuffix: IUserRoleMySuffix | undefined;

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
    routingResolveService = TestBed.inject(UserRoleMySuffixRoutingResolveService);
    service = TestBed.inject(UserRoleMySuffixService);
    resultUserRoleMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return IUserRoleMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserRoleMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUserRoleMySuffix).toEqual({ id: 123 });
    });

    it('should return new IUserRoleMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserRoleMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultUserRoleMySuffix).toEqual(new UserRoleMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as UserRoleMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUserRoleMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUserRoleMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
