import { Component, Vue, Inject } from 'vue-property-decorator';

import { IResourceMeta } from '@/shared/model/resource-meta.model';
import ResourceMetaService from './resource-meta.service';

@Component
export default class ResourceMetaDetails extends Vue {
  @Inject('resourceMetaService') private resourceMetaService: () => ResourceMetaService;
  public resourceMeta: IResourceMeta = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.resourceMetaId) {
        vm.retrieveResourceMeta(to.params.resourceMetaId);
      }
    });
  }

  public retrieveResourceMeta(resourceMetaId) {
    this.resourceMetaService()
      .find(resourceMetaId)
      .then(res => {
        this.resourceMeta = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
