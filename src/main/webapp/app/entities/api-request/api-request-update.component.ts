import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import ApiService from '@/entities/api/api.service';
import { IApi } from '@/shared/model/api.model';

import { IApiRequest, ApiRequest } from '@/shared/model/api-request.model';
import ApiRequestService from './api-request.service';

const validations: any = {
  apiRequest: {
    apiId: {
      required,
      numeric,
    },
    clientType: {
      required,
    },
    projectId: {
      required,
      numeric,
    },
    description: {},
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
export default class ApiRequestUpdate extends Vue {
  @Inject('apiRequestService') private apiRequestService: () => ApiRequestService;
  public apiRequest: IApiRequest = new ApiRequest();

  @Inject('apiService') private apiService: () => ApiService;

  public apis: IApi[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.apiRequestId) {
        vm.retrieveApiRequest(to.params.apiRequestId);
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
    this.apiRequest.apis = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.apiRequest.id) {
      this.apiRequestService()
        .update(this.apiRequest)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.apiRequest.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.apiRequestService()
        .create(this.apiRequest)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.apiRequest.created', { param: param.id });
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

  public retrieveApiRequest(apiRequestId): void {
    this.apiRequestService()
      .find(apiRequestId)
      .then(res => {
        this.apiRequest = res;
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

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
