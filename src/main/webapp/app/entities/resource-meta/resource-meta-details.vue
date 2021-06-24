<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="resourceMeta">
        <h2 class="jh-entity-heading" data-cy="resourceMetaDetailsHeading">
          <span v-text="$t('testApp.resourceMeta.detail.title')">ResourceMeta</span> {{ resourceMeta.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('testApp.resourceMeta.type')">Type</span>
          </dt>
          <dd>
            <span>{{ resourceMeta.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.resourceMeta.value')">Value</span>
          </dt>
          <dd>
            <span>{{ resourceMeta.value }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.resourceMeta.createdBy')">Created By</span>
          </dt>
          <dd>
            <span>{{ resourceMeta.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.resourceMeta.createdAt')">Created At</span>
          </dt>
          <dd>
            <span>{{ resourceMeta.createdAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.resourceMeta.modifiedBy')">Modified By</span>
          </dt>
          <dd>
            <span>{{ resourceMeta.modifiedBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.resourceMeta.modifiedAt')">Modified At</span>
          </dt>
          <dd>
            <span>{{ resourceMeta.modifiedAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.resourceMeta.resource')">Resource</span>
          </dt>
          <dd>
            <div v-if="resourceMeta.resource">
              <router-link :to="{ name: 'ResourceView', params: { resourceId: resourceMeta.resource.id } }">{{
                resourceMeta.resource.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="resourceMeta.id"
          :to="{ name: 'ResourceMetaEdit', params: { resourceMetaId: resourceMeta.id } }"
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

<script lang="ts" src="./resource-meta-details.component.ts"></script>
