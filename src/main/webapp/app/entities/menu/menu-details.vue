<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="menu">
        <h2 class="jh-entity-heading" data-cy="menuDetailsHeading">
          <span v-text="$t('testApp.menu.detail.title')">Menu</span> {{ menu.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('testApp.menu.name')">Name</span>
          </dt>
          <dd>
            <span>{{ menu.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.menu.description')">Description</span>
          </dt>
          <dd>
            <span>{{ menu.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.menu.depth')">Depth</span>
          </dt>
          <dd>
            <span>{{ menu.depth }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.menu.dispOrder')">Disp Order</span>
          </dt>
          <dd>
            <span>{{ menu.dispOrder }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.menu.dispYn')">Disp Yn</span>
          </dt>
          <dd>
            <span>{{ menu.dispYn }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.menu.uriPath')">Uri Path</span>
          </dt>
          <dd>
            <span>{{ menu.uriPath }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.menu.parentId')">Parent Id</span>
          </dt>
          <dd>
            <span>{{ menu.parentId }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.menu.createdBy')">Created By</span>
          </dt>
          <dd>
            <span>{{ menu.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.menu.createdAt')">Created At</span>
          </dt>
          <dd>
            <span>{{ menu.createdAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.menu.modifiedBy')">Modified By</span>
          </dt>
          <dd>
            <span>{{ menu.modifiedBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.menu.modifiedAt')">Modified At</span>
          </dt>
          <dd>
            <span>{{ menu.modifiedAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.menu.role')">Role</span>
          </dt>
          <dd>
            <span v-for="(role, i) in menu.roles" :key="role.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'RoleView', params: { roleId: role.id } }">{{ role.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="menu.id" :to="{ name: 'MenuEdit', params: { menuId: menu.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./menu-details.component.ts"></script>
