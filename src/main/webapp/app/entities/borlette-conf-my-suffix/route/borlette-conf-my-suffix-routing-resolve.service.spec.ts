import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IBorletteConfMySuffix, BorletteConfMySuffix } from '../borlette-conf-my-suffix.model';
import { BorletteConfMySuffixService } from '../service/borlette-conf-my-suffix.service';

import { BorletteConfMySuffixRoutingResolveService } from './borlette-conf-my-suffix-routing-resolve.service';

describe('BorletteConfMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BorletteConfMySuffixRoutingResolveService;
  let service: BorletteConfMySuffixService;
  let resultBorletteConfMySuffix: IBorletteConfMySuffix | undefined;

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
    routingResolveService = TestBed.inject(BorletteConfMySuffixRoutingResolveService);
    service = TestBed.inject(BorletteConfMySuffixService);
    resultBorletteConfMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return IBorletteConfMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBorletteConfMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBorletteConfMySuffix).toEqual({ id: 123 });
    });

    it('should return new IBorletteConfMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBorletteConfMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBorletteConfMySuffix).toEqual(new BorletteConfMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BorletteConfMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBorletteConfMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBorletteConfMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
