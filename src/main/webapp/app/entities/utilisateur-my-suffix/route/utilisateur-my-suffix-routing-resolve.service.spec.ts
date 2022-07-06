import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IUtilisateurMySuffix, UtilisateurMySuffix } from '../utilisateur-my-suffix.model';
import { UtilisateurMySuffixService } from '../service/utilisateur-my-suffix.service';

import { UtilisateurMySuffixRoutingResolveService } from './utilisateur-my-suffix-routing-resolve.service';

describe('UtilisateurMySuffix routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: UtilisateurMySuffixRoutingResolveService;
  let service: UtilisateurMySuffixService;
  let resultUtilisateurMySuffix: IUtilisateurMySuffix | undefined;

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
    routingResolveService = TestBed.inject(UtilisateurMySuffixRoutingResolveService);
    service = TestBed.inject(UtilisateurMySuffixService);
    resultUtilisateurMySuffix = undefined;
  });

  describe('resolve', () => {
    it('should return IUtilisateurMySuffix returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUtilisateurMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUtilisateurMySuffix).toEqual({ id: 123 });
    });

    it('should return new IUtilisateurMySuffix if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUtilisateurMySuffix = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultUtilisateurMySuffix).toEqual(new UtilisateurMySuffix());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as UtilisateurMySuffix })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultUtilisateurMySuffix = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultUtilisateurMySuffix).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
