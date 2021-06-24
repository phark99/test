import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILoginHistory } from '@/shared/model/login-history.model';
import LoginHistoryService from './login-history.service';

@Component
export default class LoginHistoryDetails extends Vue {
  @Inject('loginHistoryService') private loginHistoryService: () => LoginHistoryService;
  public loginHistory: ILoginHistory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.loginHistoryId) {
        vm.retrieveLoginHistory(to.params.loginHistoryId);
      }
    });
  }

  public retrieveLoginHistory(loginHistoryId) {
    this.loginHistoryService()
      .find(loginHistoryId)
      .then(res => {
        this.loginHistory = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
