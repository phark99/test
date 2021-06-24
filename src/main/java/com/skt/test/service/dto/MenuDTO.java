package com.skt.test.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.skt.test.domain.Menu} entity.
 */
@ApiModel(description = "The Menu entity.\n@author A true hipster")
public class MenuDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * name
     */
    @ApiModelProperty(value = "name")
    private String name;

    /**
     * description
     */
    @ApiModelProperty(value = "description")
    private String description;

    /**
     * depth
     */
    @ApiModelProperty(value = "depth")
    private Integer depth;

    /**
     * dispOrder
     */
    @ApiModelProperty(value = "dispOrder")
    private Integer dispOrder;

    /**
     * dispYn
     */
    @ApiModelProperty(value = "dispYn")
    private String dispYn;

    /**
     * uriPath
     */
    @ApiModelProperty(value = "uriPath")
    private String uriPath;

    /**
     * parentId
     */
    @ApiModelProperty(value = "parentId")
    private Long parentId;

    /**
     * createdBy
     */
    @NotNull
    @ApiModelProperty(value = "createdBy", required = true)
    private Long createdBy;

    /**
     * createdAt
     */
    @NotNull
    @ApiModelProperty(value = "createdAt", required = true)
    private LocalDate createdAt;

    /**
     * modifiedBy
     */
    @ApiModelProperty(value = "modifiedBy")
    private Long modifiedBy;

    /**
     * modifiedAt
     */
    @ApiModelProperty(value = "modifiedAt")
    private LocalDate modifiedAt;

    private Set<RoleDTO> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getDispYn() {
        return dispYn;
    }

    public void setDispYn(String dispYn) {
        this.dispYn = dispYn;
    }

    public String getUriPath() {
        return uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuDTO)) {
            return false;
        }

        MenuDTO menuDTO = (MenuDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, menuDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", depth=" + getDepth() +
            ", dispOrder=" + getDispOrder() +
            ", dispYn='" + getDispYn() + "'" +
            ", uriPath='" + getUriPath() + "'" +
            ", parentId=" + getParentId() +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", roles=" + getRoles() +
            "}";
    }
}
