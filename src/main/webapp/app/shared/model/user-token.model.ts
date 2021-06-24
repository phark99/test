import { IUser } from '@/shared/model/user.model';

export interface IUserToken {
  id?: number;
  accToken?: string | null;
  accExpTime?: Date | null;
  refToken?: string | null;
  refExpTime?: Date | null;
  createdBy?: number;
  createdAt?: Date;
  user?: IUser | null;
}

export class UserToken implements IUserToken {
  constructor(
    public id?: number,
    public accToken?: string | null,
    public accExpTime?: Date | null,
    public refToken?: string | null,
    public refExpTime?: Date | null,
    public createdBy?: number,
    public createdAt?: Date,
    public user?: IUser | null
  ) {}
}
