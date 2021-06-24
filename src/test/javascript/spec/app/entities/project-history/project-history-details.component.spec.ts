/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ProjectHistoryDetailComponent from '@/entities/project-history/project-history-details.vue';
import ProjectHistoryClass from '@/entities/project-history/project-history-details.component';
import ProjectHistoryService from '@/entities/project-history/project-history.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ProjectHistory Management Detail Component', () => {
    let wrapper: Wrapper<ProjectHistoryClass>;
    let comp: ProjectHistoryClass;
    let projectHistoryServiceStub: SinonStubbedInstance<ProjectHistoryService>;

    beforeEach(() => {
      projectHistoryServiceStub = sinon.createStubInstance<ProjectHistoryService>(ProjectHistoryService);

      wrapper = shallowMount<ProjectHistoryClass>(ProjectHistoryDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { projectHistoryService: () => projectHistoryServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProjectHistory = { id: 123 };
        projectHistoryServiceStub.find.resolves(foundProjectHistory);

        // WHEN
        comp.retrieveProjectHistory(123);
        await comp.$nextTick();

        // THEN
        expect(comp.projectHistory).toBe(foundProjectHistory);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProjectHistory = { id: 123 };
        projectHistoryServiceStub.find.resolves(foundProjectHistory);

        // WHEN
        comp.beforeRouteEnter({ params: { projectHistoryId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.projectHistory).toBe(foundProjectHistory);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
