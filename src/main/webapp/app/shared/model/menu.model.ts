import { IRole } from '@/shared/model/role.model';

export interface IMenu {
  id?: number;
  name?: string | null;
  description?: string | null;
  depth?: number | null;
  dispOrder?: number | null;
  dispYn?: string | null;
  uriPath?: string | null;
  parentId?: number | null;
  createdBy?: number;
  createdAt?: Date;
  modifiedBy?: number | null;
  modifiedAt?: Date | null;
  roles?: IRole[] | null;
}

export class Menu implements IMenu {
  constructor(
    public id?: number,
    public name?: string | null,
    public description?: string | null,
    public depth?: number | null,
    public dispOrder?: number | null,
    public dispYn?: string | null,
    public uriPath?: string | null,
    public parentId?: number | null,
    public createdBy?: number,
    public createdAt?: Date,
    public modifiedBy?: number | null,
    public modifiedAt?: Date | null,
    public roles?: IRole[] | null
  ) {}
}
