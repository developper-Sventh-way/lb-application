import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUtilisateurMySuffix, getUtilisateurMySuffixIdentifier } from '../utilisateur-my-suffix.model';

export type EntityResponseType = HttpResponse<IUtilisateurMySuffix>;
export type EntityArrayResponseType = HttpResponse<IUtilisateurMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class UtilisateurMySuffixService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/utilisateurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(utilisateur: IUtilisateurMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(utilisateur);
    return this.http
      .post<IUtilisateurMySuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(utilisateur: IUtilisateurMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(utilisateur);
    return this.http
      .put<IUtilisateurMySuffix>(`${this.resourceUrl}/${getUtilisateurMySuffixIdentifier(utilisateur) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(utilisateur: IUtilisateurMySuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(utilisateur);
    return this.http
      .patch<IUtilisateurMySuffix>(`${this.resourceUrl}/${getUtilisateurMySuffixIdentifier(utilisateur) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUtilisateurMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUtilisateurMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUtilisateurMySuffixToCollectionIfMissing(
    utilisateurCollection: IUtilisateurMySuffix[],
    ...utilisateursToCheck: (IUtilisateurMySuffix | null | undefined)[]
  ): IUtilisateurMySuffix[] {
    const utilisateurs: IUtilisateurMySuffix[] = utilisateursToCheck.filter(isPresent);
    if (utilisateurs.length > 0) {
      const utilisateurCollectionIdentifiers = utilisateurCollection.map(
        utilisateurItem => getUtilisateurMySuffixIdentifier(utilisateurItem)!
      );
      const utilisateursToAdd = utilisateurs.filter(utilisateurItem => {
        const utilisateurIdentifier = getUtilisateurMySuffixIdentifier(utilisateurItem);
        if (utilisateurIdentifier == null || utilisateurCollectionIdentifiers.includes(utilisateurIdentifier)) {
          return false;
        }
        utilisateurCollectionIdentifiers.push(utilisateurIdentifier);
        return true;
      });
      return [...utilisateursToAdd, ...utilisateurCollection];
    }
    return utilisateurCollection;
  }

  protected convertDateFromClient(utilisateur: IUtilisateurMySuffix): IUtilisateurMySuffix {
    return Object.assign({}, utilisateur, {
      createdDate: utilisateur.createdDate?.isValid() ? utilisateur.createdDate.toJSON() : undefined,
      lastModifiedDate: utilisateur.lastModifiedDate?.isValid() ? utilisateur.lastModifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? dayjs(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((utilisateur: IUtilisateurMySuffix) => {
        utilisateur.createdDate = utilisateur.createdDate ? dayjs(utilisateur.createdDate) : undefined;
        utilisateur.lastModifiedDate = utilisateur.lastModifiedDate ? dayjs(utilisateur.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
