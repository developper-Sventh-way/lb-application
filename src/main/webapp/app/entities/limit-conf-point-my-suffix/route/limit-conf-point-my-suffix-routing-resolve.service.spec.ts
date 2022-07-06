import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILimitConfPointMySuffix, LimitConfPointMySuffix } from '../limit-conf-point-my-suffix.model';
import { LimitConfPointMySuffixService } from '../service/limit-conf-point-my-suffix.service';

import { LimitConfPointMySuffixRoutingResolveService } from './limit-conf-point-my-suffix-routing-resolve.service';

describe('LimitConfPointMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LimitConfPointMySuffixRoutingResolveService;
  let service: LimitConfPointMySuffixService;
  let resultLimitConfPointMySuffix: ILimitConfPointMySuffix | undefined;

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
    routingResolveService = TestBed.inject(LimitConfPointMySuffixRoutingResolveService);
    service = TestBed.inject(LimitConfPointMySuffixService);
    resultLimitConfPointMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return ILimitConfPointMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLimitConfPointMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLimitConfPointMySuffix).toEqual({ id: 123 });
    });

    it('should return new ILimitConfPointMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLimitConfPointMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLimitConfPointMySuffix).toEqual(new LimitConfPointMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as LimitConfPointMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLimitConfPointMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLimitConfPointMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
