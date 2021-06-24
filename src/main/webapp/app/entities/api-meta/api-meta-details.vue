<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="apiMeta">
        <h2 class="jh-entity-heading" data-cy="apiMetaDetailsHeading">
          <span v-text="$t('testApp.apiMeta.detail.title')">ApiMeta</span> {{ apiMeta.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('testApp.apiMeta.appId')">App Id</span>
          </dt>
          <dd>
            <span>{{ apiMeta.appId }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiMeta.type')">Type</span>
          </dt>
          <dd>
            <span>{{ apiMeta.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiMeta.value')">Value</span>
          </dt>
          <dd>
            <span>{{ apiMeta.value }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiMeta.createdBy')">Created By</span>
          </dt>
          <dd>
            <span>{{ apiMeta.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiMeta.createdAt')">Created At</span>
          </dt>
          <dd>
            <span>{{ apiMeta.createdAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiMeta.modifiedBy')">Modified By</span>
          </dt>
          <dd>
            <span>{{ apiMeta.modifiedBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiMeta.modifiedAt')">Modified At</span>
          </dt>
          <dd>
            <span>{{ apiMeta.modifiedAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.apiMeta.api')">Api</span>
          </dt>
          <dd>
            <div v-if="apiMeta.api">
              <router-link :to="{ name: 'ApiView', params: { apiId: apiMeta.api.id } }">{{ apiMeta.api.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="apiMeta.id" :to="{ name: 'ApiMetaEdit', params: { apiMetaId: apiMeta.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./api-meta-details.component.ts"></script>
