import { Component, Vue, Inject } from 'vue-property-decorator';

import { IApi } from '@/shared/model/api.model';
import ApiService from './api.service';

@Component
export default class ApiDetails extends Vue {
  @Inject('apiService') private apiService: () => ApiService;
  public api: IApi = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.apiId) {
        vm.retrieveApi(to.params.apiId);
      }
    });
  }

  public retrieveApi(apiId) {
    this.apiService()
      .find(apiId)
      .then(res => {
        this.api = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
