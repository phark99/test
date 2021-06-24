import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required } from 'vuelidate/lib/validators';

import ApiService from '@/entities/api/api.service';
import { IApi } from '@/shared/model/api.model';

import { IApiMeta, ApiMeta } from '@/shared/model/api-meta.model';
import ApiMetaService from './api-meta.service';

const validations: any = {
  apiMeta: {
    appId: {
      required,
      numeric,
    },
    type: {
      required,
    },
    value: {},
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
export default class ApiMetaUpdate extends mixins(JhiDataUtils) {
  @Inject('apiMetaService') private apiMetaService: () => ApiMetaService;
  public apiMeta: IApiMeta = new ApiMeta();

  @Inject('apiService') private apiService: () => ApiService;

  public apis: IApi[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.apiMetaId) {
        vm.retrieveApiMeta(to.params.apiMetaId);
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
    if (this.apiMeta.id) {
      this.apiMetaService()
        .update(this.apiMeta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.apiMeta.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.apiMetaService()
        .create(this.apiMeta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.apiMeta.created', { param: param.id });
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

  public retrieveApiMeta(apiMetaId): void {
    this.apiMetaService()
      .find(apiMetaId)
      .then(res => {
        this.apiMeta = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.apiService()
      .retrieve()
      .then(res => {
        this.apis = res.data;
      });
  }
}
