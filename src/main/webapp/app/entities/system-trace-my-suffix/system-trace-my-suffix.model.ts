import dayjs from 'dayjs/esm';

export interface ISystemTraceMySuffix {
  id?: number;
  traceContenu?: string;
  createdBy?: string;
  createdDate?: dayjs.Dayjs;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export class SystemTraceMySuffix implements ISystemTraceMySuffix {
  constructor(
    public id?: number,
    public traceContenu?: string,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null
  ) {}
}

export function getSystemTraceMySuffixIdentifier(systemTrace: ISystemTraceMySuffix): number | undefined {
  return systemTrace.id;
}
