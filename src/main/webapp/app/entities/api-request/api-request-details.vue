<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="apiRequest">
        <h2 class="jh-entity-heading" data-cy="apiRequestDetailsHeading">
          <span v-text="$t('testApp.apiRequest.detail.title')">ApiRequest</span> {{ apiRequest.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('testApp.apiRequest.apiId')">Api Id</span>
          </dt>
          <dd>
            <span>{{ apiRequest.apiId }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiRequest.clientType')">Client Type</span>
          </dt>
          <dd>
            <span>{{ apiRequest.clientType }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiRequest.projectId')">Project Id</span>
          </dt>
          <dd>
            <span>{{ apiRequest.projectId }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiRequest.description')">Description</span>
          </dt>
          <dd>
            <span>{{ apiRequest.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiRequest.createdBy')">Created By</span>
          </dt>
          <dd>
            <span>{{ apiRequest.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiRequest.createdAt')">Created At</span>
          </dt>
          <dd>
            <span>{{ apiRequest.createdAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiRequest.modifiedBy')">Modified By</span>
          </dt>
          <dd>
            <span>{{ apiRequest.modifiedBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiRequest.modifiedAt')">Modified At</span>
          </dt>
          <dd>
            <span>{{ apiRequest.modifiedAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiRequest.api')">Api</span>
          </dt>
          <dd>
            <span v-for="(api, i) in apiRequest.apis" :key="api.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'ApiView', params: { apiId: api.id } }">{{ api.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="apiRequest.id"
          :to="{ name: 'ApiRequestEdit', params: { apiRequestId: apiRequest.id } }"
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

<script lang="ts" src="./api-request-details.component.ts"></script>
