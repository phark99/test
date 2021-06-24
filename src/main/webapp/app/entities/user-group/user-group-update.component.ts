import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import RoleService from '@/entities/role/role.service';
import { IRole } from '@/shared/model/role.model';

import { IUserGroup, UserGroup } from '@/shared/model/user-group.model';
import UserGroupService from './user-group.service';

const validations: any = {
  userGroup: {
    name: {},
    status: {},
    createdBy: {
      required,
      numeric,
    },
    createdAt: {
      required,
    },
    modifiedBy: {},
    modifiedAt: {},
  },
};

@Component({
  validations,
})
export default class UserGroupUpdate extends Vue {
  @Inject('userGroupService') private userGroupService: () => UserGroupService;
  public userGroup: IUserGroup = new UserGroup();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('roleService') private roleService: () => RoleService;

  public roles: IRole[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userGroupId) {
        vm.retrieveUserGroup(to.params.userGroupId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
    this.userGroup.users = [];
    this.userGroup.roles = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.userGroup.id) {
      this.userGroupService()
        .update(this.userGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.userGroup.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.userGroupService()
        .create(this.userGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.userGroup.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveUserGroup(userGroupId): void {
    this.userGroupService()
      .find(userGroupId)
      .then(res => {
        this.userGroup = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.roleService()
      .retrieve()
      .then(res => {
        this.roles = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
