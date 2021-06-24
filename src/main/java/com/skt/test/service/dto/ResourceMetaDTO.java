package com.skt.test.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.skt.test.domain.ResourceMeta} entity.
 */
@ApiModel(description = "The ResourceMeta entity.\n@author A true hipster")
public class ResourceMetaDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * type
     */
    @NotNull
    @ApiModelProperty(value = "type", required = true)
    private String type;

    /**
     * value
     */
    @NotNull
    @ApiModelProperty(value = "value", required = true)
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

    private ResourceDTO resource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ResourceDTO getResource() {
        return resource;
    }

    public void setResource(ResourceDTO resource) {
        this.resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceMetaDTO)) {
            return false;
        }

        ResourceMetaDTO resourceMetaDTO = (ResourceMetaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, resourceMetaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceMetaDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", resource=" + getResource() +
            "}";
    }
}
