import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITicketMySuffix, TicketMySuffix } from '../ticket-my-suffix.model';
import { TicketMySuffixService } from '../service/ticket-my-suffix.service';

import { TicketMySuffixRoutingResolveService } from './ticket-my-suffix-routing-resolve.service';

describe('TicketMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TicketMySuffixRoutingResolveService;
  let service: TicketMySuffixService;
  let resultTicketMySuffix: ITicketMySuffix | undefined;

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
    routingResolveService = TestBed.inject(TicketMySuffixRoutingResolveService);
    service = TestBed.inject(TicketMySuffixService);
    resultTicketMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return ITicketMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTicketMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTicketMySuffix).toEqual({ id: 123 });
    });

    it('should return new ITicketMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTicketMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTicketMySuffix).toEqual(new TicketMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TicketMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTicketMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTicketMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
