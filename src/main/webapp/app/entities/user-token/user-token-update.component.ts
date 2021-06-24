import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import { IUserToken, UserToken } from '@/shared/model/user-token.model';
import UserTokenService from './user-token.service';

const validations: any = {
  userToken: {
    accToken: {},
    accExpTime: {},
    refToken: {},
    refExpTime: {},
    createdBy: {
      required,
      numeric,
    },
    createdAt: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class UserTokenUpdate extends Vue {
  @Inject('userTokenService') private userTokenService: () => UserTokenService;
  public userToken: IUserToken = new UserToken();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userTokenId) {
        vm.retrieveUserToken(to.params.userTokenId);
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
    if (this.userToken.id) {
      this.userTokenService()
        .update(this.userToken)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.userToken.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.userTokenService()
        .create(this.userToken)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.userToken.created', { param: param.id });
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

  public retrieveUserToken(userTokenId): void {
    this.userTokenService()
      .find(userTokenId)
      .then(res => {
        this.userToken = res;
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
