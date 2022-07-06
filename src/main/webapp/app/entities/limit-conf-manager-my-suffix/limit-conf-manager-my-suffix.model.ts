import { IMembershipConfMySuffix } from 'app/entities/membership-conf-my-suffix/membership-conf-my-suffix.model';
import { TypeOption } from 'app/entities/enumerations/type-option.model';

export interface ILimitConfManagerMySuffix {
  id?: number;
  nombreValue?: string | null;
  limit?: number;
  limitStatut?: TypeOption | null;
  membershipConf?: IMembershipConfMySuffix | null;
}

export class LimitConfManagerMySuffix implements ILimitConfManagerMySuffix {
  constructor(
    public id?: number,
    public nombreValue?: string | null,
    public limit?: number,
    public limitStatut?: TypeOption | null,
    public membershipConf?: IMembershipConfMySuffix | null
  ) {}
}

export function getLimitConfManagerMySuffixIdentifier(limitConfManager: ILimitConfManagerMySuffix): number | undefined {
  return limitConfManager.id;
}
