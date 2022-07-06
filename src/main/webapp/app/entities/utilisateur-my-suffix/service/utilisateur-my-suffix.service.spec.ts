import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { TypeUser } from 'app/entities/enumerations/type-user.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { UserStatut } from 'app/entities/enumerations/user-statut.model';
import { IUtilisateurMySuffix, UtilisateurMySuffix } from '../utilisateur-my-suffix.model';

import { UtilisateurMySuffixService } from './utilisateur-my-suffix.service';

describe('UtilisateurMySuffix Service', () => {
  let service: UtilisateurMySuffixService;
  let httpMock: HttpTestingController;
  let elemDefault: IUtilisateurMySuffix;
  let expectedResult: IUtilisateurMySuffix | IUtilisateurMySuffix[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UtilisateurMySuffixService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      userName: 'AAAAAAA',
      password: 'AAAAAAA',
      typeUser: TypeUser.INTERNE,
      nom: 'AAAAAAA',
      prenom: 'AAAAAAA',
      sexe: Sexe.Masculin,
      nifOuCin: 'AAAAAAA',
      statut: UserStatut.ACTIVE,
      createdBy: 'AAAAAAA',
      createdDate: currentDate,
      lastModifiedBy: 'AAAAAAA',
      lastModifiedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a UtilisateurMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new UtilisateurMySuffix()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UtilisateurMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          userName: 'BBBBBB',
          password: 'BBBBBB',
          typeUser: 'BBBBBB',
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          sexe: 'BBBBBB',
          nifOuCin: 'BBBBBB',
          statut: 'BBBBBB',
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UtilisateurMySuffix', () => {
      const patchObject = Object.assign(
        {
          password: 'BBBBBB',
          typeUser: 'BBBBBB',
          prenom: 'BBBBBB',
          nifOuCin: 'BBBBBB',
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new UtilisateurMySuffix()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UtilisateurMySuffix', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          userName: 'BBBBBB',
          password: 'BBBBBB',
          typeUser: 'BBBBBB',
          nom: 'BBBBBB',
          prenom: 'BBBBBB',
          sexe: 'BBBBBB',
          nifOuCin: 'BBBBBB',
          statut: 'BBBBBB',
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a UtilisateurMySuffix', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUtilisateurMySuffixToCollectionIfMissing', () => {
      it('should add a UtilisateurMySuffix to an empty array', () => {
        const utilisateur: IUtilisateurMySuffix = { id: 123 };
        expectedResult = service.addUtilisateurMySuffixToCollectionIfMissing([], utilisateur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(utilisateur);
      });

      it('should not add a UtilisateurMySuffix to an array that contains it', () => {
        const utilisateur: IUtilisateurMySuffix = { id: 123 };
        const utilisateurCollection: IUtilisateurMySuffix[] = [
          {
            ...utilisateur,
          },
          { id: 456 },
        ];
        expectedResult = service.addUtilisateurMySuffixToCollectionIfMissing(utilisateurCollection, utilisateur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UtilisateurMySuffix to an array that doesn't contain it", () => {
        const utilisateur: IUtilisateurMySuffix = { id: 123 };
        const utilisateurCollection: IUtilisateurMySuffix[] = [{ id: 456 }];
        expectedResult = service.addUtilisateurMySuffixToCollectionIfMissing(utilisateurCollection, utilisateur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(utilisateur);
      });

      it('should add only unique UtilisateurMySuffix to an array', () => {
        const utilisateurArray: IUtilisateurMySuffix[] = [{ id: 123 }, { id: 456 }, { id: 48767 }];
        const utilisateurCollection: IUtilisateurMySuffix[] = [{ id: 123 }];
        expectedResult = service.addUtilisateurMySuffixToCollectionIfMissing(utilisateurCollection, ...utilisateurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const utilisateur: IUtilisateurMySuffix = { id: 123 };
        const utilisateur2: IUtilisateurMySuffix = { id: 456 };
        expectedResult = service.addUtilisateurMySuffixToCollectionIfMissing([], utilisateur, utilisateur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(utilisateur);
        expect(expectedResult).toContain(utilisateur2);
      });

      it('should accept null and undefined values', () => {
        const utilisateur: IUtilisateurMySuffix = { id: 123 };
        expectedResult = service.addUtilisateurMySuffixToCollectionIfMissing([], null, utilisateur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(utilisateur);
      });

      it('should return initial array if no UtilisateurMySuffix is added', () => {
        const utilisateurCollection: IUtilisateurMySuffix[] = [{ id: 123 }];
        expectedResult = service.addUtilisateurMySuffixToCollectionIfMissing(utilisateurCollection, undefined, null);
        expect(expectedResult).toEqual(utilisateurCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
