import { IUser } from '@/shared/model/user.model';
import { IResource } from '@/shared/model/resource.model';
import { IMenu } from '@/shared/model/menu.model';
import { IUserGroup } from '@/shared/model/user-group.model';

export interface IRole {
  id?: number;
  name?: string | null;
  description?: string | null;
  type?: string | null;
  applyTarget?: string | null;
  permission?: string | null;
  createdBy?: number;
  createdAt?: Date;
  modifiedBy?: number | null;
  modifiedAt?: Date | null;
  users?: IUser[] | null;
  resources?: IResource[] | null;
  menus?: IMenu[] | null;
  userGroups?: IUserGroup[] | null;
}

export class Role implements IRole {
  constructor(
    public id?: number,
    public name?: string | null,
    public description?: string | null,
    public type?: string | null,
    public applyTarget?: string | null,
    public permission?: string | null,
    public createdBy?: number,
    public createdAt?: Date,
    public modifiedBy?: number | null,
    public modifiedAt?: Date | null,
    public users?: IUser[] | null,
    public resources?: IResource[] | null,
    public menus?: IMenu[] | null,
    public userGroups?: IUserGroup[] | null
  ) {}
}
