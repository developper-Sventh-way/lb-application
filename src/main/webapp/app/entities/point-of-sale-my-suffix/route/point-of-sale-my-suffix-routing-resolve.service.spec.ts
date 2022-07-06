import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPointOfSaleMySuffix, PointOfSaleMySuffix } from '../point-of-sale-my-suffix.model';
import { PointOfSaleMySuffixService } from '../service/point-of-sale-my-suffix.service';

import { PointOfSaleMySuffixRoutingResolveService } from './point-of-sale-my-suffix-routing-resolve.service';

describe('PointOfSaleMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PointOfSaleMySuffixRoutingResolveService;
  let service: PointOfSaleMySuffixService;
  let resultPointOfSaleMySuffix: IPointOfSaleMySuffix | undefined;

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
    routingResolveService = TestBed.inject(PointOfSaleMySuffixRoutingResolveService);
    service = TestBed.inject(PointOfSaleMySuffixService);
    resultPointOfSaleMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return IPointOfSaleMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPointOfSaleMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPointOfSaleMySuffix).toEqual({ id: 123 });
    });

    it('should return new IPointOfSaleMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPointOfSaleMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPointOfSaleMySuffix).toEqual(new PointOfSaleMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PointOfSaleMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPointOfSaleMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPointOfSaleMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
