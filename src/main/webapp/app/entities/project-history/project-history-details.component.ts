import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProjectHistory } from '@/shared/model/project-history.model';
import ProjectHistoryService from './project-history.service';

@Component
export default class ProjectHistoryDetails extends Vue {
  @Inject('projectHistoryService') private projectHistoryService: () => ProjectHistoryService;
  public projectHistory: IProjectHistory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectHistoryId) {
        vm.retrieveProjectHistory(to.params.projectHistoryId);
      }
    });
  }

  public retrieveProjectHistory(projectHistoryId) {
    this.projectHistoryService()
      .find(projectHistoryId)
      .then(res => {
        this.projectHistory = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
