package com.skt.test.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.skt.test.domain.ApiMeta} entity.
 */
@ApiModel(description = "The ApiMeta entity.\n@author A true hipster")
public class ApiMetaDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * app_id
     */
    @NotNull
    @ApiModelProperty(value = "app_id", required = true)
    private Long appId;

    /**
     * type
     */
    @NotNull
    @ApiModelProperty(value = "type", required = true)
    private String type;

    /**
     * value
     */
    @ApiModelProperty(value = "value")
    @Lob
    private String value;

    /**
     * created_by
     */
    @NotNull
    @ApiModelProperty(value = "created_by", required = true)
    private Long createdBy;

    /**
     * created_at
     */
    @NotNull
    @ApiModelProperty(value = "created_at", required = true)
    private LocalDate createdAt;

    /**
     * modified_by
     */
    @ApiModelProperty(value = "modified_by")
    private Long modifiedBy;

    /**
     * modified_at
     */
    @ApiModelProperty(value = "modified_at")
    private LocalDate modifiedAt;

    private ApiDTO api;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public ApiDTO getApi() {
        return api;
    }

    public void setApi(ApiDTO api) {
        this.api = api;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiMetaDTO)) {
            return false;
        }

        ApiMetaDTO apiMetaDTO = (ApiMetaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, apiMetaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiMetaDTO{" +
            "id=" + getId() +
            ", appId=" + getAppId() +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", api=" + getApi() +
            "}";
    }
}
