import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITirageMySuffix, TirageMySuffix } from '../tirage-my-suffix.model';
import { TirageMySuffixService } from '../service/tirage-my-suffix.service';

import { TirageMySuffixRoutingResolveService } from './tirage-my-suffix-routing-resolve.service';

describe('TirageMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TirageMySuffixRoutingResolveService;
  let service: TirageMySuffixService;
  let resultTirageMySuffix: ITirageMySuffix | undefined;

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
    routingResolveService = TestBed.inject(TirageMySuffixRoutingResolveService);
    service = TestBed.inject(TirageMySuffixService);
    resultTirageMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return ITirageMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTirageMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTirageMySuffix).toEqual({ id: 123 });
    });

    it('should return new ITirageMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTirageMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTirageMySuffix).toEqual(new TirageMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TirageMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTirageMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTirageMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
