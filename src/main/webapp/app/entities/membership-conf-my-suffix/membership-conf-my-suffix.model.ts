import dayjs from 'dayjs/esm';
import { IPointOfSaleMembershipMySuffix } from 'app/entities/point-of-sale-membership-my-suffix/point-of-sale-membership-my-suffix.model';
import { ILimitConfManagerMySuffix } from 'app/entities/limit-conf-manager-my-suffix/limit-conf-manager-my-suffix.model';

export interface IMembershipConfMySuffix {
  id?: number;
  nomClient?: string | null;
  slogan?: string | null;
  telephones?: string | null;
  adresse?: string | null;
  infos?: string | null;
  logoLink?: string | null;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  pointOfSaleMemberships?: IPointOfSaleMembershipMySuffix[] | null;
  limitConfManagers?: ILimitConfManagerMySuffix[] | null;
}

export class MembershipConfMySuffix implements IMembershipConfMySuffix {
  constructor(
    public id?: number,
    public nomClient?: string | null,
    public slogan?: string | null,
    public telephones?: string | null,
    public adresse?: string | null,
    public infos?: string | null,
    public logoLink?: string | null,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public pointOfSaleMemberships?: IPointOfSaleMembershipMySuffix[] | null,
    public limitConfManagers?: ILimitConfManagerMySuffix[] | null
  ) {}
}

export function getMembershipConfMySuffixIdentifier(membershipConf: IMembershipConfMySuffix): number | undefined {
  return membershipConf.id;
}
