import dayjs from 'dayjs/esm';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';

export interface ICreditSoldeMySuffix {
  id?: number;
  montant?: number;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  utilisateur?: IUtilisateurMySuffix | null;
}

export class CreditSoldeMySuffix implements ICreditSoldeMySuffix {
  constructor(
    public id?: number,
    public montant?: number,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public utilisateur?: IUtilisateurMySuffix | null
  ) {}
}

export function getCreditSoldeMySuffixIdentifier(creditSolde: ICreditSoldeMySuffix): number | undefined {
  return creditSolde.id;
}
