import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import ProjectService from '@/entities/project/project.service';
import { IProject } from '@/shared/model/project.model';

import { IProjectHistory, ProjectHistory } from '@/shared/model/project-history.model';
import ProjectHistoryService from './project-history.service';

const validations: any = {
  projectHistory: {
    name: {},
    description: {},
    deptCode: {},
    tags: {},
    status: {},
    mainAdminId: {},
    createdBy: {
      required,
      numeric,
    },
    createdAt: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ProjectHistoryUpdate extends Vue {
  @Inject('projectHistoryService') private projectHistoryService: () => ProjectHistoryService;
  public projectHistory: IProjectHistory = new ProjectHistory();

  @Inject('projectService') private projectService: () => ProjectService;

  public projects: IProject[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectHistoryId) {
        vm.retrieveProjectHistory(to.params.projectHistoryId);
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
    if (this.projectHistory.id) {
      this.projectHistoryService()
        .update(this.projectHistory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.projectHistory.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.projectHistoryService()
        .create(this.projectHistory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.projectHistory.created', { param: param.id });
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

  public retrieveProjectHistory(projectHistoryId): void {
    this.projectHistoryService()
      .find(projectHistoryId)
      .then(res => {
        this.projectHistory = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.projectService()
      .retrieve()
      .then(res => {
        this.projects = res.data;
      });
  }
}
