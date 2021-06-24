<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="api">
        <h2 class="jh-entity-heading" data-cy="apiDetailsHeading"><span v-text="$t('testApp.api.detail.title')">Api</span> {{ api.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('testApp.api.name')">Name</span>
          </dt>
          <dd>
            <span>{{ api.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.api.projectId')">Project Id</span>
          </dt>
          <dd>
            <span>{{ api.projectId }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.api.managerId')">Manager Id</span>
          </dt>
          <dd>
            <span>{{ api.managerId }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.api.apiType')">Api Type</span>
          </dt>
          <dd>
            <span>{{ api.apiType }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.api.host')">Host</span>
          </dt>
          <dd>
            <span>{{ api.host }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.api.port')">Port</span>
          </dt>
          <dd>
            <span>{{ api.port }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.api.uri')">Uri</span>
          </dt>
          <dd>
            <span>{{ api.uri }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.api.version')">Version</span>
          </dt>
          <dd>
            <span>{{ api.version }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.api.createdBy')">Created By</span>
          </dt>
          <dd>
            <span>{{ api.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.api.createdAt')">Created At</span>
          </dt>
          <dd>
            <span>{{ api.createdAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.api.modifiedBy')">Modified By</span>
          </dt>
          <dd>
            <span>{{ api.modifiedBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.api.modifiedAt')">Modified At</span>
          </dt>
          <dd>
            <span>{{ api.modifiedAt }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="api.id" :to="{ name: 'ApiEdit', params: { apiId: api.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./api-details.component.ts"></script>
