import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';

import ApiMetaService from '@/entities/api-meta/api-meta.service';
import { IApiMeta } from '@/shared/model/api-meta.model';

import ApiRequestService from '@/entities/api-request/api-request.service';
import { IApiRequest } from '@/shared/model/api-request.model';

import { IApi, Api } from '@/shared/model/api.model';
import ApiService from './api.service';

const validations: any = {
  api: {
    name: {
      required,
    },
    projectId: {
      required,
      numeric,
    },
    managerId: {
      required,
      numeric,
    },
    apiType: {
      required,
    },
    host: {},
    port: {},
    uri: {},
    version: {},
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
export default class ApiUpdate extends Vue {
  @Inject('apiService') private apiService: () => ApiService;
  public api: IApi = new Api();

  @Inject('apiMetaService') private apiMetaService: () => ApiMetaService;

  public apiMetas: IApiMeta[] = [];

  @Inject('apiRequestService') private apiRequestService: () => ApiRequestService;

  public apiRequests: IApiRequest[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.apiId) {
        vm.retrieveApi(to.params.apiId);
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
    if (this.api.id) {
      this.apiService()
        .update(this.api)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.api.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.apiService()
        .create(this.api)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.api.created', { param: param.id });
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

  public retrieveApi(apiId): void {
    this.apiService()
      .find(apiId)
      .then(res => {
        this.api = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.apiMetaService()
      .retrieve()
      .then(res => {
        this.apiMetas = res.data;
      });
    this.apiRequestService()
      .retrieve()
      .then(res => {
        this.apiRequests = res.data;
      });
  }
}
