import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import ResourceService from '@/entities/resource/resource.service';
import { IResource } from '@/shared/model/resource.model';

import MenuService from '@/entities/menu/menu.service';
import { IMenu } from '@/shared/model/menu.model';

import UserGroupService from '@/entities/user-group/user-group.service';
import { IUserGroup } from '@/shared/model/user-group.model';

import { IRole, Role } from '@/shared/model/role.model';
import RoleService from './role.service';

const validations: any = {
  role: {
    name: {},
    description: {},
    type: {},
    applyTarget: {},
    permission: {},
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
export default class RoleUpdate extends Vue {
  @Inject('roleService') private roleService: () => RoleService;
  public role: IRole = new Role();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('resourceService') private resourceService: () => ResourceService;

  public resources: IResource[] = [];

  @Inject('menuService') private menuService: () => MenuService;

  public menus: IMenu[] = [];

  @Inject('userGroupService') private userGroupService: () => UserGroupService;

  public userGroups: IUserGroup[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.roleId) {
        vm.retrieveRole(to.params.roleId);
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
    this.role.users = [];
    this.role.resources = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.role.id) {
      this.roleService()
        .update(this.role)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.role.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.roleService()
        .create(this.role)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.role.created', { param: param.id });
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

  public retrieveRole(roleId): void {
    this.roleService()
      .find(roleId)
      .then(res => {
        this.role = res;
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
    this.resourceService()
      .retrieve()
      .then(res => {
        this.resources = res.data;
      });
    this.menuService()
      .retrieve()
      .then(res => {
        this.menus = res.data;
      });
    this.userGroupService()
      .retrieve()
      .then(res => {
        this.userGroups = res.data;
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
