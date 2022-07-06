import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IMembershipConfMySuffix, MembershipConfMySuffix } from '../membership-conf-my-suffix.model';
import { MembershipConfMySuffixService } from '../service/membership-conf-my-suffix.service';

import { MembershipConfMySuffixRoutingResolveService } from './membership-conf-my-suffix-routing-resolve.service';

describe('MembershipConfMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: MembershipConfMySuffixRoutingResolveService;
  let service: MembershipConfMySuffixService;
  let resultMembershipConfMySuffix: IMembershipConfMySuffix | undefined;

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
    routingResolveService = TestBed.inject(MembershipConfMySuffixRoutingResolveService);
    service = TestBed.inject(MembershipConfMySuffixService);
    resultMembershipConfMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return IMembershipConfMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMembershipConfMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMembershipConfMySuffix).toEqual({ id: 123 });
    });

    it('should return new IMembershipConfMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMembershipConfMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMembershipConfMySuffix).toEqual(new MembershipConfMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as MembershipConfMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMembershipConfMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMembershipConfMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
