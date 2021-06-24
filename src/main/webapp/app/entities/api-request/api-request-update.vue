<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="testApp.apiRequest.home.createOrEditLabel"
          data-cy="ApiRequestCreateUpdateHeading"
          v-text="$t('testApp.apiRequest.home.createOrEditLabel')"
        >
          Create or edit a ApiRequest
        </h2>
        <div>
          <div class="form-group" v-if="apiRequest.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="apiRequest.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiRequest.apiId')" for="api-request-apiId">Api Id</label>
            <input
              type="number"
              class="form-control"
              name="apiId"
              id="api-request-apiId"
              data-cy="apiId"
              :class="{ valid: !$v.apiRequest.apiId.$invalid, invalid: $v.apiRequest.apiId.$invalid }"
              v-model.number="$v.apiRequest.apiId.$model"
              required
            />
            <div v-if="$v.apiRequest.apiId.$anyDirty && $v.apiRequest.apiId.$invalid">
              <small class="form-text text-danger" v-if="!$v.apiRequest.apiId.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.apiRequest.apiId.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiRequest.clientType')" for="api-request-clientType">Client Type</label>
            <input
              type="text"
              class="form-control"
              name="clientType"
              id="api-request-clientType"
              data-cy="clientType"
              :class="{ valid: !$v.apiRequest.clientType.$invalid, invalid: $v.apiRequest.clientType.$invalid }"
              v-model="$v.apiRequest.clientType.$model"
              required
            />
            <div v-if="$v.apiRequest.clientType.$anyDirty && $v.apiRequest.clientType.$invalid">
              <small class="form-text text-danger" v-if="!$v.apiRequest.clientType.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiRequest.projectId')" for="api-request-projectId">Project Id</label>
            <input
              type="number"
              class="form-control"
              name="projectId"
              id="api-request-projectId"
              data-cy="projectId"
              :class="{ valid: !$v.apiRequest.projectId.$invalid, invalid: $v.apiRequest.projectId.$invalid }"
              v-model.number="$v.apiRequest.projectId.$model"
              required
            />
            <div v-if="$v.apiRequest.projectId.$anyDirty && $v.apiRequest.projectId.$invalid">
              <small class="form-text text-danger" v-if="!$v.apiRequest.projectId.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.apiRequest.projectId.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiRequest.description')" for="api-request-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="api-request-description"
              data-cy="description"
              :class="{ valid: !$v.apiRequest.description.$invalid, invalid: $v.apiRequest.description.$invalid }"
              v-model="$v.apiRequest.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiRequest.createdBy')" for="api-request-createdBy">Created By</label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="api-request-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.apiRequest.createdBy.$invalid, invalid: $v.apiRequest.createdBy.$invalid }"
              v-model.number="$v.apiRequest.createdBy.$model"
              required
            />
            <div v-if="$v.apiRequest.createdBy.$anyDirty && $v.apiRequest.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.apiRequest.createdBy.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.apiRequest.createdBy.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiRequest.createdAt')" for="api-request-createdAt">Created At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="api-request-createdAt"
                  v-model="$v.apiRequest.createdAt.$model"
                  name="createdAt"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="api-request-createdAt"
                data-cy="createdAt"
                type="text"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.apiRequest.createdAt.$invalid, invalid: $v.apiRequest.createdAt.$invalid }"
                v-model="$v.apiRequest.createdAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.apiRequest.createdAt.$anyDirty && $v.apiRequest.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.apiRequest.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiRequest.modifiedBy')" for="api-request-modifiedBy">Modified By</label>
            <input
              type="number"
              class="form-control"
              name="modifiedBy"
              id="api-request-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !$v.apiRequest.modifiedBy.$invalid, invalid: $v.apiRequest.modifiedBy.$invalid }"
              v-model.number="$v.apiRequest.modifiedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.apiRequest.modifiedAt')" for="api-request-modifiedAt">Modified At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="api-request-modifiedAt"
                  v-model="$v.apiRequest.modifiedAt.$model"
                  name="modifiedAt"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="api-request-modifiedAt"
                data-cy="modifiedAt"
                type="text"
                class="form-control"
                name="modifiedAt"
                :class="{ valid: !$v.apiRequest.modifiedAt.$invalid, invalid: $v.apiRequest.modifiedAt.$invalid }"
                v-model="$v.apiRequest.modifiedAt.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label v-text="$t('testApp.apiRequest.api')" for="api-request-api">Api</label>
            <select
              class="form-control"
              id="api-request-api"
              data-cy="api"
              multiple
              name="api"
              v-if="apiRequest.apis !== undefined"
              v-model="apiRequest.apis"
            >
              <option v-bind:value="getSelected(apiRequest.apis, apiOption)" v-for="apiOption in apis" :key="apiOption.id">
                {{ apiOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.apiRequest.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./api-request-update.component.ts"></script>
