import dayjs from 'dayjs/esm';
import { IMembershipConfMySuffix } from 'app/entities/membership-conf-my-suffix/membership-conf-my-suffix.model';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { TypeBanque } from 'app/entities/enumerations/type-banque.model';

export interface IPointOfSaleMembershipMySuffix {
  id?: number;
  typePoint?: TypeBanque;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  membershipConf?: IMembershipConfMySuffix | null;
  utilisateur?: IUtilisateurMySuffix | null;
}

export class PointOfSaleMembershipMySuffix implements IPointOfSaleMembershipMySuffix {
  constructor(
    public id?: number,
    public typePoint?: TypeBanque,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public membershipConf?: IMembershipConfMySuffix | null,
    public utilisateur?: IUtilisateurMySuffix | null
  ) {}
}

export function getPointOfSaleMembershipMySuffixIdentifier(pointOfSaleMembership: IPointOfSaleMembershipMySuffix): number | undefined {
  return pointOfSaleMembership.id;
}
