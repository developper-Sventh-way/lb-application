import dayjs from 'dayjs/esm';
import { IPointOfSaleMySuffix } from 'app/entities/point-of-sale-my-suffix/point-of-sale-my-suffix.model';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';

export interface IPaiementBanqueMySuffix {
  id?: number;
  montant?: number;
  balance?: number;
  description?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  pointOfSale?: IPointOfSaleMySuffix | null;
  utilisateur?: IUtilisateurMySuffix | null;
}

export class PaiementBanqueMySuffix implements IPaiementBanqueMySuffix {
  constructor(
    public id?: number,
    public montant?: number,
    public balance?: number,
    public description?: string | null,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public pointOfSale?: IPointOfSaleMySuffix | null,
    public utilisateur?: IUtilisateurMySuffix | null
  ) {}
}

export function getPaiementBanqueMySuffixIdentifier(paiementBanque: IPaiementBanqueMySuffix): number | undefined {
  return paiementBanque.id;
}
