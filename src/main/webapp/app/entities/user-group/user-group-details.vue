<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="userGroup">
        <h2 class="jh-entity-heading" data-cy="userGroupDetailsHeading">
          <span v-text="$t('testApp.userGroup.detail.title')">UserGroup</span> {{ userGroup.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('testApp.userGroup.name')">Name</span>
          </dt>
          <dd>
            <span>{{ userGroup.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.userGroup.status')">Status</span>
          </dt>
          <dd>
            <span>{{ userGroup.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.userGroup.createdBy')">Created By</span>
          </dt>
          <dd>
            <span>{{ userGroup.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.userGroup.createdAt')">Created At</span>
          </dt>
          <dd>
            <span>{{ userGroup.createdAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.userGroup.modifiedBy')">Modified By</span>
          </dt>
          <dd>
            <span>{{ userGroup.modifiedBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.userGroup.modifiedAt')">Modified At</span>
          </dt>
          <dd>
            <span>{{ userGroup.modifiedAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.userGroup.user')">User</span>
          </dt>
          <dd>
            <span v-for="(user, i) in userGroup.users" :key="user.id"
              >{{ i > 0 ? ', ' : '' }}
              {{ user.id }}
            </span>
          </dd>
          <dt>
            <span v-text="$t('testApp.userGroup.role')">Role</span>
          </dt>
          <dd>
            <span v-for="(role, i) in userGroup.roles" :key="role.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'RoleView', params: { roleId: role.id } }">{{ role.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="userGroup.id"
          :to="{ name: 'UserGroupEdit', params: { userGroupId: userGroup.id } }"
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

<script lang="ts" src="./user-group-details.component.ts"></script>
