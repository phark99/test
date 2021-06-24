import { IUser } from '@/shared/model/user.model';
import { IRole } from '@/shared/model/role.model';

export interface IUserGroup {
  id?: number;
  name?: string | null;
  status?: string | null;
  createdBy?: number;
  createdAt?: Date;
  modifiedBy?: number | null;
  modifiedAt?: Date | null;
  users?: IUser[] | null;
  roles?: IRole[] | null;
}

export class UserGroup implements IUserGroup {
  constructor(
    public id?: number,
    public name?: string | null,
    public status?: string | null,
    public createdBy?: number,
    public createdAt?: Date,
    public modifiedBy?: number | null,
    public modifiedAt?: Date | null,
    public users?: IUser[] | null,
    public roles?: IRole[] | null
  ) {}
}
