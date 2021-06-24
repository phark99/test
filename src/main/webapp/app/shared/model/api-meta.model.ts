import { IApi } from '@/shared/model/api.model';

export interface IApiMeta {
  id?: number;
  appId?: number;
  type?: string;
  value?: string | null;
  createdBy?: number;
  createdAt?: Date;
  modifiedBy?: number | null;
  modifiedAt?: Date | null;
  api?: IApi | null;
}

export class ApiMeta implements IApiMeta {
  constructor(
    public id?: number,
    public appId?: number,
    public type?: string,
    public value?: string | null,
    public createdBy?: number,
    public createdAt?: Date,
    public modifiedBy?: number | null,
    public modifiedAt?: Date | null,
    public api?: IApi | null
  ) {}
}
