import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import ResourceService from '@/entities/resource/resource.service';
import { IResource } from '@/shared/model/resource.model';

import ProjectHistoryService from '@/entities/project-history/project-history.service';
import { IProjectHistory } from '@/shared/model/project-history.model';

import { IProject, Project } from '@/shared/model/project.model';
import ProjectService from './project.service';

const validations: any = {
  project: {
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
    modifiedBy: {},
    modifiedAt: {},
  },
};

@Component({
  validations,
})
export default class ProjectUpdate extends Vue {
  @Inject('projectService') private projectService: () => ProjectService;
  public project: IProject = new Project();

  @Inject('resourceService') private resourceService: () => ResourceService;

  public resources: IResource[] = [];

  @Inject('projectHistoryService') private projectHistoryService: () => ProjectHistoryService;

  public projectHistories: IProjectHistory[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectId) {
        vm.retrieveProject(to.params.projectId);
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
    this.project.resources = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.project.id) {
      this.projectService()
        .update(this.project)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.project.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.projectService()
        .create(this.project)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.project.created', { param: param.id });
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

  public retrieveProject(projectId): void {
    this.projectService()
      .find(projectId)
      .then(res => {
        this.project = res;
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
    this.projectHistoryService()
      .retrieve()
      .then(res => {
        this.projectHistories = res.data;
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
