import { IResource } from '@/shared/model/resource.model';

export interface IResourceMeta {
  id?: number;
  type?: string;
  value?: string;
  createdBy?: number;
  createdAt?: Date;
  modifiedBy?: number | null;
  modifiedAt?: Date | null;
  resource?: IResource | null;
}

export class ResourceMeta implements IResourceMeta {
  constructor(
    public id?: number,
    public type?: string,
    public value?: string,
    public createdBy?: number,
    public createdAt?: Date,
    public modifiedBy?: number | null,
    public modifiedAt?: Date | null,
    public resource?: IResource | null
  ) {}
}
