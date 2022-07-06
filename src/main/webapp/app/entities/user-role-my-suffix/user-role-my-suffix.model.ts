import dayjs from 'dayjs/esm';
import { IUtilisateurMySuffix } from 'app/entities/utilisateur-my-suffix/utilisateur-my-suffix.model';
import { UserRoleName } from 'app/entities/enumerations/user-role-name.model';

export interface IUserRoleMySuffix {
  id?: number;
  role?: UserRoleName;
  description?: string | null;
  exceptions?: string | null;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  utilisateur?: IUtilisateurMySuffix | null;
}

export class UserRoleMySuffix implements IUserRoleMySuffix {
  constructor(
    public id?: number,
    public role?: UserRoleName,
    public description?: string | null,
    public exceptions?: string | null,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public utilisateur?: IUtilisateurMySuffix | null
  ) {}
}

export function getUserRoleMySuffixIdentifier(userRole: IUserRoleMySuffix): number | undefined {
  return userRole.id;
}
