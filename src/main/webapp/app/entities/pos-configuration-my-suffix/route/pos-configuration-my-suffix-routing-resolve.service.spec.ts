import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPOSConfigurationMySuffix, POSConfigurationMySuffix } from '../pos-configuration-my-suffix.model';
import { POSConfigurationMySuffixService } from '../service/pos-configuration-my-suffix.service';

import { POSConfigurationMySuffixRoutingResolveService } from './pos-configuration-my-suffix-routing-resolve.service';

describe('POSConfigurationMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: POSConfigurationMySuffixRoutingResolveService;
  let service: POSConfigurationMySuffixService;
  let resultPOSConfigurationMySuffix: IPOSConfigurationMySuffix | undefined;

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
    routingResolveService = TestBed.inject(POSConfigurationMySuffixRoutingResolveService);
    service = TestBed.inject(POSConfigurationMySuffixService);
    resultPOSConfigurationMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return IPOSConfigurationMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPOSConfigurationMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPOSConfigurationMySuffix).toEqual({ id: 123 });
    });

    it('should return new IPOSConfigurationMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPOSConfigurationMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPOSConfigurationMySuffix).toEqual(new POSConfigurationMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as POSConfigurationMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPOSConfigurationMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPOSConfigurationMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
