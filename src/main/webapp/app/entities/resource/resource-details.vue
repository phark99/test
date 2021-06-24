<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="resource">
        <h2 class="jh-entity-heading" data-cy="resourceDetailsHeading">
          <span v-text="$t('testApp.resource.detail.title')">Resource</span> {{ resource.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('testApp.resource.type')">Type</span>
          </dt>
          <dd>
            <span>{{ resource.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.resource.name')">Name</span>
          </dt>
          <dd>
            <span>{{ resource.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.resource.status')">Status</span>
          </dt>
          <dd>
            <span>{{ resource.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.resource.createdBy')">Created By</span>
          </dt>
          <dd>
            <span>{{ resource.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.resource.createdAt')">Created At</span>
          </dt>
          <dd>
            <span>{{ resource.createdAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.resource.modifiedBy')">Modified By</span>
          </dt>
          <dd>
            <span>{{ resource.modifiedBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.resource.modifiedAt')">Modified At</span>
          </dt>
          <dd>
            <span>{{ resource.modifiedAt }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="resource.id" :to="{ name: 'ResourceEdit', params: { resourceId: resource.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./resource-details.component.ts"></script>
