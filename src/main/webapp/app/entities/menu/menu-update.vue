<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="testApp.menu.home.createOrEditLabel" data-cy="MenuCreateUpdateHeading" v-text="$t('testApp.menu.home.createOrEditLabel')">
          Create or edit a Menu
        </h2>
        <div>
          <div class="form-group" v-if="menu.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="menu.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.menu.name')" for="menu-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="menu-name"
              data-cy="name"
              :class="{ valid: !$v.menu.name.$invalid, invalid: $v.menu.name.$invalid }"
              v-model="$v.menu.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.menu.description')" for="menu-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="menu-description"
              data-cy="description"
              :class="{ valid: !$v.menu.description.$invalid, invalid: $v.menu.description.$invalid }"
              v-model="$v.menu.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.menu.depth')" for="menu-depth">Depth</label>
            <input
              type="number"
              class="form-control"
              name="depth"
              id="menu-depth"
              data-cy="depth"
              :class="{ valid: !$v.menu.depth.$invalid, invalid: $v.menu.depth.$invalid }"
              v-model.number="$v.menu.depth.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.menu.dispOrder')" for="menu-dispOrder">Disp Order</label>
            <input
              type="number"
              class="form-control"
              name="dispOrder"
              id="menu-dispOrder"
              data-cy="dispOrder"
              :class="{ valid: !$v.menu.dispOrder.$invalid, invalid: $v.menu.dispOrder.$invalid }"
              v-model.number="$v.menu.dispOrder.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.menu.dispYn')" for="menu-dispYn">Disp Yn</label>
            <input
              type="text"
              class="form-control"
              name="dispYn"
              id="menu-dispYn"
              data-cy="dispYn"
              :class="{ valid: !$v.menu.dispYn.$invalid, invalid: $v.menu.dispYn.$invalid }"
              v-model="$v.menu.dispYn.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.menu.uriPath')" for="menu-uriPath">Uri Path</label>
            <input
              type="text"
              class="form-control"
              name="uriPath"
              id="menu-uriPath"
              data-cy="uriPath"
              :class="{ valid: !$v.menu.uriPath.$invalid, invalid: $v.menu.uriPath.$invalid }"
              v-model="$v.menu.uriPath.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.menu.parentId')" for="menu-parentId">Parent Id</label>
            <input
              type="number"
              class="form-control"
              name="parentId"
              id="menu-parentId"
              data-cy="parentId"
              :class="{ valid: !$v.menu.parentId.$invalid, invalid: $v.menu.parentId.$invalid }"
              v-model.number="$v.menu.parentId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.menu.createdBy')" for="menu-createdBy">Created By</label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="menu-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.menu.createdBy.$invalid, invalid: $v.menu.createdBy.$invalid }"
              v-model.number="$v.menu.createdBy.$model"
              required
            />
            <div v-if="$v.menu.createdBy.$anyDirty && $v.menu.createdBy.$invalid">
              <small class="form-text text-danger" v-if="!$v.menu.createdBy.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.menu.createdBy.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.menu.createdAt')" for="menu-createdAt">Created At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="menu-createdAt"
                  v-model="$v.menu.createdAt.$model"
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
                id="menu-createdAt"
                data-cy="createdAt"
                type="text"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.menu.createdAt.$invalid, invalid: $v.menu.createdAt.$invalid }"
                v-model="$v.menu.createdAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.menu.createdAt.$anyDirty && $v.menu.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.menu.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.menu.modifiedBy')" for="menu-modifiedBy">Modified By</label>
            <input
              type="number"
              class="form-control"
              name="modifiedBy"
              id="menu-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !$v.menu.modifiedBy.$invalid, invalid: $v.menu.modifiedBy.$invalid }"
              v-model.number="$v.menu.modifiedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('testApp.menu.modifiedAt')" for="menu-modifiedAt">Modified At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="menu-modifiedAt"
                  v-model="$v.menu.modifiedAt.$model"
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
                id="menu-modifiedAt"
                data-cy="modifiedAt"
                type="text"
                class="form-control"
                name="modifiedAt"
                :class="{ valid: !$v.menu.modifiedAt.$invalid, invalid: $v.menu.modifiedAt.$invalid }"
                v-model="$v.menu.modifiedAt.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label v-text="$t('testApp.menu.role')" for="menu-role">Role</label>
            <select
              class="form-control"
              id="menu-role"
              data-cy="role"
              multiple
              name="role"
              v-if="menu.roles !== undefined"
              v-model="menu.roles"
            >
              <option v-bind:value="getSelected(menu.roles, roleOption)" v-for="roleOption in roles" :key="roleOption.id">
                {{ roleOption.id }}
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
            :disabled="$v.menu.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./menu-update.component.ts"></script>
