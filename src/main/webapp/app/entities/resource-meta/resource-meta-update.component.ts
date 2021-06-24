import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';

import ResourceService from '@/entities/resource/resource.service';
import { IResource } from '@/shared/model/resource.model';

import { IResourceMeta, ResourceMeta } from '@/shared/model/resource-meta.model';
import ResourceMetaService from './resource-meta.service';

const validations: any = {
  resourceMeta: {
    type: {
      required,
    },
    value: {
      required,
    },
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
export default class ResourceMetaUpdate extends Vue {
  @Inject('resourceMetaService') private resourceMetaService: () => ResourceMetaService;
  public resourceMeta: IResourceMeta = new ResourceMeta();

  @Inject('resourceService') private resourceService: () => ResourceService;

  public resources: IResource[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.resourceMetaId) {
        vm.retrieveResourceMeta(to.params.resourceMetaId);
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
    if (this.resourceMeta.id) {
      this.resourceMetaService()
        .update(this.resourceMeta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.resourceMeta.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.resourceMetaService()
        .create(this.resourceMeta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.resourceMeta.created', { param: param.id });
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

  public retrieveResourceMeta(resourceMetaId): void {
    this.resourceMetaService()
      .find(resourceMetaId)
      .then(res => {
        this.resourceMeta = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.resourceService()
      .retrieve()
      .then(res => {
        this.resources = res.data;
      });
  }
}
