<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="projectHistory">
        <h2 class="jh-entity-heading" data-cy="projectHistoryDetailsHeading">
          <span v-text="$t('testApp.projectHistory.detail.title')">ProjectHistory</span> {{ projectHistory.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('testApp.projectHistory.name')">Name</span>
          </dt>
          <dd>
            <span>{{ projectHistory.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.projectHistory.description')">Description</span>
          </dt>
          <dd>
            <span>{{ projectHistory.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.projectHistory.deptCode')">Dept Code</span>
          </dt>
          <dd>
            <span>{{ projectHistory.deptCode }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.projectHistory.tags')">Tags</span>
          </dt>
          <dd>
            <span>{{ projectHistory.tags }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.projectHistory.status')">Status</span>
          </dt>
          <dd>
            <span>{{ projectHistory.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.projectHistory.mainAdminId')">Main Admin Id</span>
          </dt>
          <dd>
            <span>{{ projectHistory.mainAdminId }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.projectHistory.createdBy')">Created By</span>
          </dt>
          <dd>
            <span>{{ projectHistory.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.projectHistory.createdAt')">Created At</span>
          </dt>
          <dd>
            <span>{{ projectHistory.createdAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.projectHistory.project')">Project</span>
          </dt>
          <dd>
            <div v-if="projectHistory.project">
              <router-link :to="{ name: 'ProjectView', params: { projectId: projectHistory.project.id } }">{{
                projectHistory.project.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="projectHistory.id"
          :to="{ name: 'ProjectHistoryEdit', params: { projectHistoryId: projectHistory.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./project-history-details.component.ts"></script>
