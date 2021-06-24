import { Component, Vue, Inject } from 'vue-property-decorator';

import { IApiRequest } from '@/shared/model/api-request.model';
import ApiRequestService from './api-request.service';

@Component
export default class ApiRequestDetails extends Vue {
  @Inject('apiRequestService') private apiRequestService: () => ApiRequestService;
  public apiRequest: IApiRequest = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.apiRequestId) {
        vm.retrieveApiRequest(to.params.apiRequestId);
      }
    });
  }

  public retrieveApiRequest(apiRequestId) {
    this.apiRequestService()
      .find(apiRequestId)
      .then(res => {
        this.apiRequest = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
