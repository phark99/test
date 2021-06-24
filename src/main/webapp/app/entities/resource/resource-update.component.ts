import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';

import ResourceMetaService from '@/entities/resource-meta/resource-meta.service';
import { IResourceMeta } from '@/shared/model/resource-meta.model';

import RoleService from '@/entities/role/role.service';
import { IRole } from '@/shared/model/role.model';

import ProjectService from '@/entities/project/project.service';
import { IProject } from '@/shared/model/project.model';

import { IResource, Resource } from '@/shared/model/resource.model';
import ResourceService from './resource.service';

const validations: any = {
  resource: {
    type: {
      required,
    },
    name: {
      required,
    },
    status: {
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
export default class ResourceUpdate extends Vue {
  @Inject('resourceService') private resourceService: () => ResourceService;
  public resource: IResource = new Resource();

  @Inject('resourceMetaService') private resourceMetaService: () => ResourceMetaService;

  public resourceMetas: IResourceMeta[] = [];

  @Inject('roleService') private roleService: () => RoleService;

  public roles: IRole[] = [];

  @Inject('projectService') private projectService: () => ProjectService;

  public projects: IProject[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.resourceId) {
        vm.retrieveResource(to.params.resourceId);
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
    if (this.resource.id) {
      this.resourceService()
        .update(this.resource)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.resource.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.resourceService()
        .create(this.resource)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testApp.resource.created', { param: param.id });
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

  public retrieveResource(resourceId): void {
    this.resourceService()
      .find(resourceId)
      .then(res => {
        this.resource = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.resourceMetaService()
      .retrieve()
      .then(res => {
        this.resourceMetas = res.data;
      });
    this.roleService()
      .retrieve()
      .then(res => {
        this.roles = res.data;
      });
    this.projectService()
      .retrieve()
      .then(res => {
        this.projects = res.data;
      });
  }
}
