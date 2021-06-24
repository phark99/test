import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IApiMeta } from '@/shared/model/api-meta.model';
import ApiMetaService from './api-meta.service';

@Component
export default class ApiMetaDetails extends mixins(JhiDataUtils) {
  @Inject('apiMetaService') private apiMetaService: () => ApiMetaService;
  public apiMeta: IApiMeta = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.apiMetaId) {
        vm.retrieveApiMeta(to.params.apiMetaId);
      }
    });
  }

  public retrieveApiMeta(apiMetaId) {
    this.apiMetaService()
      .find(apiMetaId)
      .then(res => {
        this.apiMeta = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
