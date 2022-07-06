import dayjs from 'dayjs/esm';
import { ITicketMySuffix } from 'app/entities/ticket-my-suffix/ticket-my-suffix.model';
import { IBorletteConfMySuffix } from 'app/entities/borlette-conf-my-suffix/borlette-conf-my-suffix.model';
import { TirageName } from 'app/entities/enumerations/tirage-name.model';
import { TirageType } from 'app/entities/enumerations/tirage-type.model';
import { UserStatut } from 'app/entities/enumerations/user-statut.model';

export interface ITirageMySuffix {
  id?: number;
  tirageName?: TirageName;
  type?: TirageType;
  premierLot?: string | null;
  deuxiemeLot?: string | null;
  troisiemeLot?: string | null;
  loto3Chif?: string | null;
  statut?: UserStatut;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  tickets?: ITicketMySuffix[] | null;
  borletteConf?: IBorletteConfMySuffix | null;
}

export class TirageMySuffix implements ITirageMySuffix {
  constructor(
    public id?: number,
    public tirageName?: TirageName,
    public type?: TirageType,
    public premierLot?: string | null,
    public deuxiemeLot?: string | null,
    public troisiemeLot?: string | null,
    public loto3Chif?: string | null,
    public statut?: UserStatut,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public tickets?: ITicketMySuffix[] | null,
    public borletteConf?: IBorletteConfMySuffix | null
  ) {}
}

export function getTirageMySuffixIdentifier(tirage: ITirageMySuffix): number | undefined {
  return tirage.id;
}
