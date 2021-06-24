import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import { ILoginHistory, LoginHistory } from '@/shared/model/login-history.model';
import LoginHistoryService from './login-history.service';

const validations: any = {
  loginHistory: {
    createdAt: {
      required,
    },
    userAgent: {},
  },
};

@Component({
  validations,
})
export default class LoginHistoryUpdate extends Vue {
  @Inject('loginHistoryService') private loginHistoryService: () => LoginHistoryService;
  public loginHistory: ILoginHistory = new LoginHistory();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.loginHistoryId) {
        vm.retrieveLoginHistory(to.params.loginHistoryId);
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
  }

  public save(): void {
    this.isSaving = true;
    if (this.loginHistory.id) {
      this.loginHistoryService()
        .update(this.loginHistory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.loginHistory.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.loginHistoryService()
        .create(this.loginHistory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.loginHistory.created', { param: param.id });
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

  public retrieveLoginHistory(loginHistoryId): void {
    this.loginHistoryService()
      .find(loginHistoryId)
      .then(res => {
        this.loginHistory = res;
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
  }
}
