<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="loginHistory">
        <h2 class="jh-entity-heading" data-cy="loginHistoryDetailsHeading">
          <span v-text="$t('testApp.loginHistory.detail.title')">LoginHistory</span> {{ loginHistory.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('testApp.loginHistory.createdAt')">Created At</span>
          </dt>
          <dd>
            <span>{{ loginHistory.createdAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.loginHistory.userAgent')">User Agent</span>
          </dt>
          <dd>
            <span>{{ loginHistory.userAgent }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.loginHistory.user')">User</span>
          </dt>
          <dd>
            {{ loginHistory.user ? loginHistory.user.id : '' }}
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="loginHistory.id"
          :to="{ name: 'LoginHistoryEdit', params: { loginHistoryId: loginHistory.id } }"
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

<script lang="ts" src="./login-history-details.component.ts"></script>
