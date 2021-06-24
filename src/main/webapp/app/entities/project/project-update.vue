<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="testApp.project.home.createOrEditLabel"
          data-cy="ProjectCreateUpdateHeading"
          v-text="$t('testApp.project.home.createOrEditLabel')"
        >
          Create or edit a Project
        </h2>
        <div>
          <div class="form-group" v-if="project.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="project.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.project.name')" for="project-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="project-name"
              data-cy="name"
              :class="{ valid: !$v.project.name.$invalid, invalid: $v.project.name.$invalid }"
              v-model="$v.project.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.project.description')" for="project-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="project-description"
              data-cy="description"
              :class="{ valid: !$v.project.description.$invalid, invalid: $v.project.description.$invalid }"
              v-model="$v.project.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.project.deptCode')" for="project-deptCode">Dept Code</label>
            <input
              type="text"
              class="form-control"
              name="deptCode"
              id="project-deptCode"
              data-cy="deptCode"
              :class="{ valid: !$v.project.deptCode.$invalid, invalid: $v.project.deptCode.$invalid }"
              v-model="$v.project.deptCode.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.project.tags')" for="project-tags">Tags</label>
            <input
              type="text"
              class="form-control"
              name="tags"
              id="project-tags"
              data-cy="tags"
              :class="{ valid: !$v.project.tags.$invalid, invalid: $v.project.tags.$invalid }"
              v-model="$v.project.tags.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.project.status')" for="project-status">Status</label>
            <input
              type="text"
              class="form-control"
              name="status"
              id="project-status"
              data-cy="status"
              :class="{ valid: !$v.project.status.$invalid, invalid: $v.project.status.$invalid }"
              v-model="$v.project.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.project.mainAdminId')" for="project-mainAdminId">Main Admin Id</label>
            <input
              type="number"
              class="form-control"
              name="mainAdminId"
              id="project-mainAdminId"
              data-cy="mainAdminId"
              :class="{ valid: !$v.project.mainAdminId.$invalid, invalid: $v.project.mainAdminId.$invalid }"
              v-model.number="$v.project.mainAdminId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.project.createdBy')" for="project-createdBy">Created By</label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="project-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.project.createdBy.$invalid, invalid: $v.project.createdBy.$invalid }"
              v-model.number="$v.project.createdBy.$model"
              required
            />
            <div v-if="$v.project.createdBy.$anyDirty && $v.project.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.project.createdBy.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.project.createdBy.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.project.createdAt')" for="project-createdAt">Created At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="project-createdAt"
                  v-model="$v.project.createdAt.$model"
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
                id="project-createdAt"
                data-cy="createdAt"
                type="text"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.project.createdAt.$invalid, invalid: $v.project.createdAt.$invalid }"
                v-model="$v.project.createdAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.project.createdAt.$anyDirty && $v.project.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.project.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.project.modifiedBy')" for="project-modifiedBy">Modified By</label>
            <input
              type="number"
              class="form-control"
              name="modifiedBy"
              id="project-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !$v.project.modifiedBy.$invalid, invalid: $v.project.modifiedBy.$invalid }"
              v-model.number="$v.project.modifiedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.project.modifiedAt')" for="project-modifiedAt">Modified At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="project-modifiedAt"
                  v-model="$v.project.modifiedAt.$model"
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
                id="project-modifiedAt"
                data-cy="modifiedAt"
                type="text"
                class="form-control"
                name="modifiedAt"
                :class="{ valid: !$v.project.modifiedAt.$invalid, invalid: $v.project.modifiedAt.$invalid }"
                v-model="$v.project.modifiedAt.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label v-text="$t('testApp.project.resource')" for="project-resource">Resource</label>
            <select
              class="form-control"
              id="project-resource"
              data-cy="resource"
              multiple
              name="resource"
              v-if="project.resources !== undefined"
              v-model="project.resources"
            >
              <option
                v-bind:value="getSelected(project.resources, resourceOption)"
                v-for="resourceOption in resources"
                :key="resourceOption.id"
              >
                {{ resourceOption.id }}
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
            :disabled="$v.project.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./project-update.component.ts"></script>
