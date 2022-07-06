import dayjs from 'dayjs/esm';
import { ICreditSoldeMySuffix } from 'app/entities/credit-solde-my-suffix/credit-solde-my-suffix.model';
import { IUserPaymentConfMySuffix } from 'app/entities/user-payment-conf-my-suffix/user-payment-conf-my-suffix.model';
import { IPointOfSaleMembershipMySuffix } from 'app/entities/point-of-sale-membership-my-suffix/point-of-sale-membership-my-suffix.model';
import { IPaiementBanqueMySuffix } from 'app/entities/paiement-banque-my-suffix/paiement-banque-my-suffix.model';
import { IUserSaleAccountMySuffix } from 'app/entities/user-sale-account-my-suffix/user-sale-account-my-suffix.model';
import { IUserRoleMySuffix } from 'app/entities/user-role-my-suffix/user-role-my-suffix.model';
import { IUserPaymentMySuffix } from 'app/entities/user-payment-my-suffix/user-payment-my-suffix.model';
import { IPointOfSaleMySuffix } from 'app/entities/point-of-sale-my-suffix/point-of-sale-my-suffix.model';
import { TypeUser } from 'app/entities/enumerations/type-user.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { UserStatut } from 'app/entities/enumerations/user-statut.model';

export interface IUtilisateurMySuffix {
  id?: number;
  userName?: string;
  password?: string;
  typeUser?: TypeUser;
  nom?: string;
  prenom?: string;
  sexe?: Sexe;
  nifOuCin?: string | null;
  statut?: UserStatut;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  creditSolde?: ICreditSoldeMySuffix | null;
  userPaymentConf?: IUserPaymentConfMySuffix | null;
  pointOfSaleMemberships?: IPointOfSaleMembershipMySuffix[] | null;
  paiementBanques?: IPaiementBanqueMySuffix[] | null;
  userSaleAccounts?: IUserSaleAccountMySuffix[] | null;
  userRoles?: IUserRoleMySuffix[] | null;
  userPayments?: IUserPaymentMySuffix[] | null;
  pointOfSale?: IPointOfSaleMySuffix | null;
}

export class UtilisateurMySuffix implements IUtilisateurMySuffix {
  constructor(
    public id?: number,
    public userName?: string,
    public password?: string,
    public typeUser?: TypeUser,
    public nom?: string,
    public prenom?: string,
    public sexe?: Sexe,
    public nifOuCin?: string | null,
    public statut?: UserStatut,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public creditSolde?: ICreditSoldeMySuffix | null,
    public userPaymentConf?: IUserPaymentConfMySuffix | null,
    public pointOfSaleMemberships?: IPointOfSaleMembershipMySuffix[] | null,
    public paiementBanques?: IPaiementBanqueMySuffix[] | null,
    public userSaleAccounts?: IUserSaleAccountMySuffix[] | null,
    public userRoles?: IUserRoleMySuffix[] | null,
    public userPayments?: IUserPaymentMySuffix[] | null,
    public pointOfSale?: IPointOfSaleMySuffix | null
  ) {}
}

export function getUtilisateurMySuffixIdentifier(utilisateur: IUtilisateurMySuffix): number | undefined {
  return utilisateur.id;
}
