<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="testApp.api.home.createOrEditLabel" data-cy="ApiCreateUpdateHeading" v-text="$t('testApp.api.home.createOrEditLabel')">
          Create or edit a Api
        </h2>
        <div>
          <div class="form-group" v-if="api.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="api.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.api.name')" for="api-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="api-name"
              data-cy="name"
              :class="{ valid: !$v.api.name.$invalid, invalid: $v.api.name.$invalid }"
              v-model="$v.api.name.$model"
              required
            />
            <div v-if="$v.api.name.$anyDirty && $v.api.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.api.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.api.projectId')" for="api-projectId">Project Id</label>
            <input
              type="number"
              class="form-control"
              name="projectId"
              id="api-projectId"
              data-cy="projectId"
              :class="{ valid: !$v.api.projectId.$invalid, invalid: $v.api.projectId.$invalid }"
              v-model.number="$v.api.projectId.$model"
              required
            />
            <div v-if="$v.api.projectId.$anyDirty && $v.api.projectId.$invalid">
              <small class="form-text text-danger" v-if="!$v.api.projectId.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.api.projectId.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.api.managerId')" for="api-managerId">Manager Id</label>
            <input
              type="number"
              class="form-control"
              name="managerId"
              id="api-managerId"
              data-cy="managerId"
              :class="{ valid: !$v.api.managerId.$invalid, invalid: $v.api.managerId.$invalid }"
              v-model.number="$v.api.managerId.$model"
              required
            />
            <div v-if="$v.api.managerId.$anyDirty && $v.api.managerId.$invalid">
              <small class="form-text text-danger" v-if="!$v.api.managerId.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.api.managerId.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.api.apiType')" for="api-apiType">Api Type</label>
            <input
              type="text"
              class="form-control"
              name="apiType"
              id="api-apiType"
              data-cy="apiType"
              :class="{ valid: !$v.api.apiType.$invalid, invalid: $v.api.apiType.$invalid }"
              v-model="$v.api.apiType.$model"
              required
            />
            <div v-if="$v.api.apiType.$anyDirty && $v.api.apiType.$invalid">
              <small class="form-text text-danger" v-if="!$v.api.apiType.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.api.host')" for="api-host">Host</label>
            <input
              type="text"
              class="form-control"
              name="host"
              id="api-host"
              data-cy="host"
              :class="{ valid: !$v.api.host.$invalid, invalid: $v.api.host.$invalid }"
              v-model="$v.api.host.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.api.port')" for="api-port">Port</label>
            <input
              type="number"
              class="form-control"
              name="port"
              id="api-port"
              data-cy="port"
              :class="{ valid: !$v.api.port.$invalid, invalid: $v.api.port.$invalid }"
              v-model.number="$v.api.port.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.api.uri')" for="api-uri">Uri</label>
            <input
              type="text"
              class="form-control"
              name="uri"
              id="api-uri"
              data-cy="uri"
              :class="{ valid: !$v.api.uri.$invalid, invalid: $v.api.uri.$invalid }"
              v-model="$v.api.uri.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.api.version')" for="api-version">Version</label>
            <input
              type="text"
              class="form-control"
              name="version"
              id="api-version"
              data-cy="version"
              :class="{ valid: !$v.api.version.$invalid, invalid: $v.api.version.$invalid }"
              v-model="$v.api.version.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.api.createdBy')" for="api-createdBy">Created By</label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="api-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.api.createdBy.$invalid, invalid: $v.api.createdBy.$invalid }"
              v-model.number="$v.api.createdBy.$model"
              required
            />
            <div v-if="$v.api.createdBy.$anyDirty && $v.api.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.api.createdBy.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.api.createdBy.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.api.createdAt')" for="api-createdAt">Created At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="api-createdAt"
                  v-model="$v.api.createdAt.$model"
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
                id="api-createdAt"
                data-cy="createdAt"
                type="text"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.api.createdAt.$invalid, invalid: $v.api.createdAt.$invalid }"
                v-model="$v.api.createdAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.api.createdAt.$anyDirty && $v.api.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.api.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.api.modifiedBy')" for="api-modifiedBy">Modified By</label>
            <input
              type="number"
              class="form-control"
              name="modifiedBy"
              id="api-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !$v.api.modifiedBy.$invalid, invalid: $v.api.modifiedBy.$invalid }"
              v-model.number="$v.api.modifiedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.api.modifiedAt')" for="api-modifiedAt">Modified At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="api-modifiedAt"
                  v-model="$v.api.modifiedAt.$model"
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
                id="api-modifiedAt"
                data-cy="modifiedAt"
                type="text"
                class="form-control"
                name="modifiedAt"
                :class="{ valid: !$v.api.modifiedAt.$invalid, invalid: $v.api.modifiedAt.$invalid }"
                v-model="$v.api.modifiedAt.$model"
              />
            </b-input-group>
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
            :disabled="$v.api.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./api-update.component.ts"></script>
