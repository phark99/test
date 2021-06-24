<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="project">
        <h2 class="jh-entity-heading" data-cy="projectDetailsHeading">
          <span v-text="$t('testApp.project.detail.title')">Project</span> {{ project.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('testApp.project.name')">Name</span>
          </dt>
          <dd>
            <span>{{ project.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.project.description')">Description</span>
          </dt>
          <dd>
            <span>{{ project.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.project.deptCode')">Dept Code</span>
          </dt>
          <dd>
            <span>{{ project.deptCode }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.project.tags')">Tags</span>
          </dt>
          <dd>
            <span>{{ project.tags }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.project.status')">Status</span>
          </dt>
          <dd>
            <span>{{ project.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.project.mainAdminId')">Main Admin Id</span>
          </dt>
          <dd>
            <span>{{ project.mainAdminId }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.project.createdBy')">Created By</span>
          </dt>
          <dd>
            <span>{{ project.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.project.createdAt')">Created At</span>
          </dt>
          <dd>
            <span>{{ project.createdAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.project.modifiedBy')">Modified By</span>
          </dt>
          <dd>
            <span>{{ project.modifiedBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.project.modifiedAt')">Modified At</span>
          </dt>
          <dd>
            <span>{{ project.modifiedAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.project.resource')">Resource</span>
          </dt>
          <dd>
            <span v-for="(resource, i) in project.resources" :key="resource.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'ResourceView', params: { resourceId: resource.id } }">{{ resource.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="project.id" :to="{ name: 'ProjectEdit', params: { projectId: project.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./project-details.component.ts"></script>
