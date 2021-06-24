import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUserGroup } from '@/shared/model/user-group.model';
import UserGroupService from './user-group.service';

@Component
export default class UserGroupDetails extends Vue {
  @Inject('userGroupService') private userGroupService: () => UserGroupService;
  public userGroup: IUserGroup = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userGroupId) {
        vm.retrieveUserGroup(to.params.userGroupId);
      }
    });
  }

  public retrieveUserGroup(userGroupId) {
    this.userGroupService()
      .find(userGroupId)
      .then(res => {
        this.userGroup = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
