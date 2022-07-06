import { ILimitConfBorletteMySuffix } from 'app/entities/limit-conf-borlette-my-suffix/limit-conf-borlette-my-suffix.model';
import { ITirageMySuffix } from 'app/entities/tirage-my-suffix/tirage-my-suffix.model';
import { TirageName } from 'app/entities/enumerations/tirage-name.model';

export interface IBorletteConfMySuffix {
  id?: number;
  name?: TirageName;
  premierLot?: number;
  deuxiemeLot?: number;
  troisiemeLot?: number;
  mariageGratisPrix?: number | null;
  montantMinimum?: number;
  montantMaximum?: number;
  closeTimeMidi?: string;
  closeTimeSoir?: string;
  limitConfBorlettes?: ILimitConfBorletteMySuffix[] | null;
  tirages?: ITirageMySuffix[] | null;
}

export class BorletteConfMySuffix implements IBorletteConfMySuffix {
  constructor(
    public id?: number,
    public name?: TirageName,
    public premierLot?: number,
    public deuxiemeLot?: number,
    public troisiemeLot?: number,
    public mariageGratisPrix?: number | null,
    public montantMinimum?: number,
    public montantMaximum?: number,
    public closeTimeMidi?: string,
    public closeTimeSoir?: string,
    public limitConfBorlettes?: ILimitConfBorletteMySuffix[] | null,
    public tirages?: ITirageMySuffix[] | null
  ) {}
}

export function getBorletteConfMySuffixIdentifier(borletteConf: IBorletteConfMySuffix): number | undefined {
  return borletteConf.id;
}
