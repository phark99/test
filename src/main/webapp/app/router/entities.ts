import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const UserToken = () => import('@/entities/user-token/user-token.vue');
// prettier-ignore
const UserTokenUpdate = () => import('@/entities/user-token/user-token-update.vue');
// prettier-ignore
const UserTokenDetails = () => import('@/entities/user-token/user-token-details.vue');
// prettier-ignore
const LoginHistory = () => import('@/entities/login-history/login-history.vue');
// prettier-ignore
const LoginHistoryUpdate = () => import('@/entities/login-history/login-history-update.vue');
// prettier-ignore
const LoginHistoryDetails = () => import('@/entities/login-history/login-history-details.vue');
// prettier-ignore
const UserGroup = () => import('@/entities/user-group/user-group.vue');
// prettier-ignore
const UserGroupUpdate = () => import('@/entities/user-group/user-group-update.vue');
// prettier-ignore
const UserGroupDetails = () => import('@/entities/user-group/user-group-details.vue');
// prettier-ignore
const Api = () => import('@/entities/api/api.vue');
// prettier-ignore
const ApiUpdate = () => import('@/entities/api/api-update.vue');
// prettier-ignore
const ApiDetails = () => import('@/entities/api/api-details.vue');
// prettier-ignore
const ApiMeta = () => import('@/entities/api-meta/api-meta.vue');
// prettier-ignore
const ApiMetaUpdate = () => import('@/entities/api-meta/api-meta-update.vue');
// prettier-ignore
const ApiMetaDetails = () => import('@/entities/api-meta/api-meta-details.vue');
// prettier-ignore
const ApiRequest = () => import('@/entities/api-request/api-request.vue');
// prettier-ignore
const ApiRequestUpdate = () => import('@/entities/api-request/api-request-update.vue');
// prettier-ignore
const ApiRequestDetails = () => import('@/entities/api-request/api-request-details.vue');
// prettier-ignore
const Resource = () => import('@/entities/resource/resource.vue');
// prettier-ignore
const ResourceUpdate = () => import('@/entities/resource/resource-update.vue');
// prettier-ignore
const ResourceDetails = () => import('@/entities/resource/resource-details.vue');
// prettier-ignore
const ResourceMeta = () => import('@/entities/resource-meta/resource-meta.vue');
// prettier-ignore
const ResourceMetaUpdate = () => import('@/entities/resource-meta/resource-meta-update.vue');
// prettier-ignore
const ResourceMetaDetails = () => import('@/entities/resource-meta/resource-meta-details.vue');
// prettier-ignore
const Project = () => import('@/entities/project/project.vue');
// prettier-ignore
const ProjectUpdate = () => import('@/entities/project/project-update.vue');
// prettier-ignore
const ProjectDetails = () => import('@/entities/project/project-details.vue');
// prettier-ignore
const ProjectHistory = () => import('@/entities/project-history/project-history.vue');
// prettier-ignore
const ProjectHistoryUpdate = () => import('@/entities/project-history/project-history-update.vue');
// prettier-ignore
const ProjectHistoryDetails = () => import('@/entities/project-history/project-history-details.vue');
// prettier-ignore
const Role = () => import('@/entities/role/role.vue');
// prettier-ignore
const RoleUpdate = () => import('@/entities/role/role-update.vue');
// prettier-ignore
const RoleDetails = () => import('@/entities/role/role-details.vue');
// prettier-ignore
const Menu = () => import('@/entities/menu/menu.vue');
// prettier-ignore
const MenuUpdate = () => import('@/entities/menu/menu-update.vue');
// prettier-ignore
const MenuDetails = () => import('@/entities/menu/menu-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/user-token',
    name: 'UserToken',
    component: UserToken,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/user-token/new',
    name: 'UserTokenCreate',
    component: UserTokenUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/user-token/:userTokenId/edit',
    name: 'UserTokenEdit',
    component: UserTokenUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/user-token/:userTokenId/view',
    name: 'UserTokenView',
    component: UserTokenDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/login-history',
    name: 'LoginHistory',
    component: LoginHistory,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/login-history/new',
    name: 'LoginHistoryCreate',
    component: LoginHistoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/login-history/:loginHistoryId/edit',
    name: 'LoginHistoryEdit',
    component: LoginHistoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/login-history/:loginHistoryId/view',
    name: 'LoginHistoryView',
    component: LoginHistoryDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/user-group',
    name: 'UserGroup',
    component: UserGroup,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/user-group/new',
    name: 'UserGroupCreate',
    component: UserGroupUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/user-group/:userGroupId/edit',
    name: 'UserGroupEdit',
    component: UserGroupUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/user-group/:userGroupId/view',
    name: 'UserGroupView',
    component: UserGroupDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api',
    name: 'Api',
    component: Api,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api/new',
    name: 'ApiCreate',
    component: ApiUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api/:apiId/edit',
    name: 'ApiEdit',
    component: ApiUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api/:apiId/view',
    name: 'ApiView',
    component: ApiDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api-meta',
    name: 'ApiMeta',
    component: ApiMeta,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api-meta/new',
    name: 'ApiMetaCreate',
    component: ApiMetaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api-meta/:apiMetaId/edit',
    name: 'ApiMetaEdit',
    component: ApiMetaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api-meta/:apiMetaId/view',
    name: 'ApiMetaView',
    component: ApiMetaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api-request',
    name: 'ApiRequest',
    component: ApiRequest,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api-request/new',
    name: 'ApiRequestCreate',
    component: ApiRequestUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api-request/:apiRequestId/edit',
    name: 'ApiRequestEdit',
    component: ApiRequestUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/api-request/:apiRequestId/view',
    name: 'ApiRequestView',
    component: ApiRequestDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/resource',
    name: 'Resource',
    component: Resource,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/resource/new',
    name: 'ResourceCreate',
    component: ResourceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/resource/:resourceId/edit',
    name: 'ResourceEdit',
    component: ResourceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/resource/:resourceId/view',
    name: 'ResourceView',
    component: ResourceDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/resource-meta',
    name: 'ResourceMeta',
    component: ResourceMeta,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/resource-meta/new',
    name: 'ResourceMetaCreate',
    component: ResourceMetaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/resource-meta/:resourceMetaId/edit',
    name: 'ResourceMetaEdit',
    component: ResourceMetaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/resource-meta/:resourceMetaId/view',
    name: 'ResourceMetaView',
    component: ResourceMetaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project',
    name: 'Project',
    component: Project,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project/new',
    name: 'ProjectCreate',
    component: ProjectUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project/:projectId/edit',
    name: 'ProjectEdit',
    component: ProjectUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project/:projectId/view',
    name: 'ProjectView',
    component: ProjectDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project-history',
    name: 'ProjectHistory',
    component: ProjectHistory,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project-history/new',
    name: 'ProjectHistoryCreate',
    component: ProjectHistoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project-history/:projectHistoryId/edit',
    name: 'ProjectHistoryEdit',
    component: ProjectHistoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project-history/:projectHistoryId/view',
    name: 'ProjectHistoryView',
    component: ProjectHistoryDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/role',
    name: 'Role',
    component: Role,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/role/new',
    name: 'RoleCreate',
    component: RoleUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/role/:roleId/edit',
    name: 'RoleEdit',
    component: RoleUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/role/:roleId/view',
    name: 'RoleView',
    component: RoleDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/menu',
    name: 'Menu',
    component: Menu,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/menu/new',
    name: 'MenuCreate',
    component: MenuUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/menu/:menuId/edit',
    name: 'MenuEdit',
    component: MenuUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/menu/:menuId/view',
    name: 'MenuView',
    component: MenuDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
