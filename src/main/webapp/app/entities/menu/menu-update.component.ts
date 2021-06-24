import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import RoleService from '@/entities/role/role.service';
import { IRole } from '@/shared/model/role.model';

import { IMenu, Menu } from '@/shared/model/menu.model';
import MenuService from './menu.service';

const validations: any = {
  menu: {
    name: {},
    description: {},
    depth: {},
    dispOrder: {},
    dispYn: {},
    uriPath: {},
    parentId: {},
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
export default class MenuUpdate extends Vue {
  @Inject('menuService') private menuService: () => MenuService;
  public menu: IMenu = new Menu();

  @Inject('roleService') private roleService: () => RoleService;

  public roles: IRole[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.menuId) {
        vm.retrieveMenu(to.params.menuId);
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
    this.menu.roles = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.menu.id) {
      this.menuService()
        .update(this.menu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.menu.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.menuService()
        .create(this.menu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.menu.created', { param: param.id });
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

  public retrieveMenu(menuId): void {
    this.menuService()
      .find(menuId)
      .then(res => {
        this.menu = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
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
