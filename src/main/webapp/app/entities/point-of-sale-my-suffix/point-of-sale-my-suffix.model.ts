import dayjs from 'dayjs/esm';
import { IPointOfSaleConfMySuffix } from 'app/entities/point-of-sale-conf-my-suffix/point-of-sale-conf-my-suffix.model';
import { ILimitConfPointMySuffix } from 'app/entities/limit-conf-point-my-suffix/limit-conf-point-my-suffix.model';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { IPaiementBanqueMySuffix } from 'app/entities/paiement-banque-my-suffix/paiement-banque-my-suffix.model';
import { ITicketMySuffix } from 'app/entities/ticket-my-suffix/ticket-my-suffix.model';
import { StatutBanque } from 'app/entities/enumerations/statut-banque.model';

export interface IPointOfSaleMySuffix {
  id?: number;
  adressePoint?: string | null;
  statut?: StatutBanque;
  phone1?: string | null;
  phone2?: string | null;
  pourcentagePoint?: number;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  pointOfSaleConf?: IPointOfSaleConfMySuffix | null;
  limitConfPoints?: ILimitConfPointMySuffix[] | null;
  utilisateurs?: IUtilisateurMySuffix[] | null;
  paiementBanques?: IPaiementBanqueMySuffix[] | null;
  tickets?: ITicketMySuffix[] | null;
}

export class PointOfSaleMySuffix implements IPointOfSaleMySuffix {
  constructor(
    public id?: number,
    public adressePoint?: string | null,
    public statut?: StatutBanque,
    public phone1?: string | null,
    public phone2?: string | null,
    public pourcentagePoint?: number,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public pointOfSaleConf?: IPointOfSaleConfMySuffix | null,
    public limitConfPoints?: ILimitConfPointMySuffix[] | null,
    public utilisateurs?: IUtilisateurMySuffix[] | null,
    public paiementBanques?: IPaiementBanqueMySuffix[] | null,
    public tickets?: ITicketMySuffix[] | null
  ) {}
}

export function getPointOfSaleMySuffixIdentifier(pointOfSale: IPointOfSaleMySuffix): number | undefined {
  return pointOfSale.id;
}
