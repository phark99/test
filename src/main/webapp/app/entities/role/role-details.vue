<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="role">
        <h2 class="jh-entity-heading" data-cy="roleDetailsHeading">
          <span v-text="$t('testApp.role.detail.title')">Role</span> {{ role.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('testApp.role.name')">Name</span>
          </dt>
          <dd>
            <span>{{ role.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.role.description')">Description</span>
          </dt>
          <dd>
            <span>{{ role.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.role.type')">Type</span>
          </dt>
          <dd>
            <span>{{ role.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.role.applyTarget')">Apply Target</span>
          </dt>
          <dd>
            <span>{{ role.applyTarget }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.role.permission')">Permission</span>
          </dt>
          <dd>
            <span>{{ role.permission }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.role.createdBy')">Created By</span>
          </dt>
          <dd>
            <span>{{ role.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.role.createdAt')">Created At</span>
          </dt>
          <dd>
            <span>{{ role.createdAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.role.modifiedBy')">Modified By</span>
          </dt>
          <dd>
            <span>{{ role.modifiedBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.role.modifiedAt')">Modified At</span>
          </dt>
          <dd>
            <span>{{ role.modifiedAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.role.user')">User</span>
          </dt>
          <dd>
            <span v-for="(user, i) in role.users" :key="user.id"
              >{{ i > 0 ? ', ' : '' }}
              {{ user.id }}
            </span>
          </dd>
          <dt>
            <span v-text="$t('testApp.role.resource')">Resource</span>
          </dt>
          <dd>
            <span v-for="(resource, i) in role.resources" :key="resource.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'ResourceView', params: { resourceId: resource.id } }">{{ resource.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="role.id" :to="{ name: 'RoleEdit', params: { roleId: role.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./role-details.component.ts"></script>
