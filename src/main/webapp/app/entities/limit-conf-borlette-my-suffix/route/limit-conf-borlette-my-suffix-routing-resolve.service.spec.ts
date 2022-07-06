import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILimitConfBorletteMySuffix, LimitConfBorletteMySuffix } from '../limit-conf-borlette-my-suffix.model';
import { LimitConfBorletteMySuffixService } from '../service/limit-conf-borlette-my-suffix.service';

import { LimitConfBorletteMySuffixRoutingResolveService } from './limit-conf-borlette-my-suffix-routing-resolve.service';

describe('LimitConfBorletteMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LimitConfBorletteMySuffixRoutingResolveService;
  let service: LimitConfBorletteMySuffixService;
  let resultLimitConfBorletteMySuffix: ILimitConfBorletteMySuffix | undefined;

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
    routingResolveService = TestBed.inject(LimitConfBorletteMySuffixRoutingResolveService);
    service = TestBed.inject(LimitConfBorletteMySuffixService);
    resultLimitConfBorletteMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return ILimitConfBorletteMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLimitConfBorletteMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLimitConfBorletteMySuffix).toEqual({ id: 123 });
    });

    it('should return new ILimitConfBorletteMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLimitConfBorletteMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLimitConfBorletteMySuffix).toEqual(new LimitConfBorletteMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as LimitConfBorletteMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLimitConfBorletteMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLimitConfBorletteMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
