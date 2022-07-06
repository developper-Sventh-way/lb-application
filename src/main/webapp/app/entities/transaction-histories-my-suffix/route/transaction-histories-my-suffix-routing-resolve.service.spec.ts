import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITransactionHistoriesMySuffix, TransactionHistoriesMySuffix } from '../transaction-histories-my-suffix.model';
import { TransactionHistoriesMySuffixService } from '../service/transaction-histories-my-suffix.service';

import { TransactionHistoriesMySuffixRoutingResolveService } from './transaction-histories-my-suffix-routing-resolve.service';

describe('TransactionHistoriesMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TransactionHistoriesMySuffixRoutingResolveService;
  let service: TransactionHistoriesMySuffixService;
  let resultTransactionHistoriesMySuffix: ITransactionHistoriesMySuffix | undefined;

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
    routingResolveService = TestBed.inject(TransactionHistoriesMySuffixRoutingResolveService);
    service = TestBed.inject(TransactionHistoriesMySuffixService);
    resultTransactionHistoriesMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return ITransactionHistoriesMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTransactionHistoriesMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTransactionHistoriesMySuffix).toEqual({ id: 123 });
    });

    it('should return new ITransactionHistoriesMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTransactionHistoriesMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTransactionHistoriesMySuffix).toEqual(new TransactionHistoriesMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TransactionHistoriesMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTransactionHistoriesMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTransactionHistoriesMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
