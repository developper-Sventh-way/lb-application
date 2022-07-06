import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILimitConfManagerMySuffix, LimitConfManagerMySuffix } from '../limit-conf-manager-my-suffix.model';
import { LimitConfManagerMySuffixService } from '../service/limit-conf-manager-my-suffix.service';

import { LimitConfManagerMySuffixRoutingResolveService } from './limit-conf-manager-my-suffix-routing-resolve.service';

describe('LimitConfManagerMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LimitConfManagerMySuffixRoutingResolveService;
  let service: LimitConfManagerMySuffixService;
  let resultLimitConfManagerMySuffix: ILimitConfManagerMySuffix | undefined;

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
    routingResolveService = TestBed.inject(LimitConfManagerMySuffixRoutingResolveService);
    service = TestBed.inject(LimitConfManagerMySuffixService);
    resultLimitConfManagerMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return ILimitConfManagerMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLimitConfManagerMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLimitConfManagerMySuffix).toEqual({ id: 123 });
    });

    it('should return new ILimitConfManagerMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLimitConfManagerMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLimitConfManagerMySuffix).toEqual(new LimitConfManagerMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as LimitConfManagerMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLimitConfManagerMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLimitConfManagerMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
