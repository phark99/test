package com.skt.test.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.skt.test.domain.Resource} entity.
 */
@ApiModel(description = "The Resource entity.\n@author A true hipster")
public class ResourceDTO implements Serializable {

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
     * name
     */
    @NotNull
    @ApiModelProperty(value = "name", required = true)
    private String name;

    /**
     * status
     */
    @NotNull
    @ApiModelProperty(value = "status", required = true)
    private String status;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceDTO)) {
            return false;
        }

        ResourceDTO resourceDTO = (ResourceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, resourceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            "}";
    }
}
