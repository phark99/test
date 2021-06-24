<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="userToken">
        <h2 class="jh-entity-heading" data-cy="userTokenDetailsHeading">
          <span v-text="$t('testApp.userToken.detail.title')">UserToken</span> {{ userToken.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('testApp.userToken.accToken')">Acc Token</span>
          </dt>
          <dd>
            <span>{{ userToken.accToken }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.userToken.accExpTime')">Acc Exp Time</span>
          </dt>
          <dd>
            <span>{{ userToken.accExpTime }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.userToken.refToken')">Ref Token</span>
          </dt>
          <dd>
            <span>{{ userToken.refToken }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.userToken.refExpTime')">Ref Exp Time</span>
          </dt>
          <dd>
            <span>{{ userToken.refExpTime }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.userToken.createdBy')">Created By</span>
          </dt>
          <dd>
            <span>{{ userToken.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.userToken.createdAt')">Created At</span>
          </dt>
          <dd>
            <span>{{ userToken.createdAt }}</span>
          </dd>
          <dt>
            <span v-text="$t('testApp.userToken.user')">User</span>
          </dt>
          <dd>
            {{ userToken.user ? userToken.user.id : '' }}
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="userToken.id"
          :to="{ name: 'UserTokenEdit', params: { userTokenId: userToken.id } }"
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

<script lang="ts" src="./user-token-details.component.ts"></script>
