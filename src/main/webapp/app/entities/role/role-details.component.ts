import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRole } from '@/shared/model/role.model';
import RoleService from './role.service';

@Component
export default class RoleDetails extends Vue {
  @Inject('roleService') private roleService: () => RoleService;
  public role: IRole = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.roleId) {
        vm.retrieveRole(to.params.roleId);
      }
    });
  }

  public retrieveRole(roleId) {
    this.roleService()
      .find(roleId)
      .then(res => {
        this.role = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
