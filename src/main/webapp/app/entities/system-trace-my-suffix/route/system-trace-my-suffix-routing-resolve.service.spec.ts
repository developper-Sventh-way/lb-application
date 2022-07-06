import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISystemTraceMySuffix, SystemTraceMySuffix } from '../system-trace-my-suffix.model';
import { SystemTraceMySuffixService } from '../service/system-trace-my-suffix.service';

import { SystemTraceMySuffixRoutingResolveService } from './system-trace-my-suffix-routing-resolve.service';

describe('SystemTraceMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SystemTraceMySuffixRoutingResolveService;
  let service: SystemTraceMySuffixService;
  let resultSystemTraceMySuffix: ISystemTraceMySuffix | undefined;

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
    routingResolveService = TestBed.inject(SystemTraceMySuffixRoutingResolveService);
    service = TestBed.inject(SystemTraceMySuffixService);
    resultSystemTraceMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return ISystemTraceMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSystemTraceMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSystemTraceMySuffix).toEqual({ id: 123 });
    });

    it('should return new ISystemTraceMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSystemTraceMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSystemTraceMySuffix).toEqual(new SystemTraceMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SystemTraceMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSystemTraceMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSystemTraceMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
