import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUserToken } from '@/shared/model/user-token.model';
import UserTokenService from './user-token.service';

@Component
export default class UserTokenDetails extends Vue {
  @Inject('userTokenService') private userTokenService: () => UserTokenService;
  public userToken: IUserToken = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userTokenId) {
        vm.retrieveUserToken(to.params.userTokenId);
      }
    });
  }

  public retrieveUserToken(userTokenId) {
    this.userTokenService()
      .find(userTokenId)
      .then(res => {
        this.userToken = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
