import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPointOfSaleMembershipMySuffix, PointOfSaleMembershipMySuffix } from '../point-of-sale-membership-my-suffix.model';
import { PointOfSaleMembershipMySuffixService } from '../service/point-of-sale-membership-my-suffix.service';

import { PointOfSaleMembershipMySuffixRoutingResolveService } from './point-of-sale-membership-my-suffix-routing-resolve.service';

describe('PointOfSaleMembershipMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PointOfSaleMembershipMySuffixRoutingResolveService;
  let service: PointOfSaleMembershipMySuffixService;
  let resultPointOfSaleMembershipMySuffix: IPointOfSaleMembershipMySuffix | undefined;

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
    routingResolveService = TestBed.inject(PointOfSaleMembershipMySuffixRoutingResolveService);
    service = TestBed.inject(PointOfSaleMembershipMySuffixService);
    resultPointOfSaleMembershipMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return IPointOfSaleMembershipMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPointOfSaleMembershipMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPointOfSaleMembershipMySuffix).toEqual({ id: 123 });
    });

    it('should return new IPointOfSaleMembershipMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPointOfSaleMembershipMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPointOfSaleMembershipMySuffix).toEqual(new PointOfSaleMembershipMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PointOfSaleMembershipMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPointOfSaleMembershipMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPointOfSaleMembershipMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
