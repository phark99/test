package com.skt.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Menu entity.\n@author A true hipster
 */
@Entity
@Table(name = "menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * name
     */
    @Column(name = "name")
    private String name;

    /**
     * description
     */
    @Column(name = "description")
    private String description;

    /**
     * depth
     */
    @Column(name = "depth")
    private Integer depth;

    /**
     * dispOrder
     */
    @Column(name = "disp_order")
    private Integer dispOrder;

    /**
     * dispYn
     */
    @Column(name = "disp_yn")
    private String dispYn;

    /**
     * uriPath
     */
    @Column(name = "uri_path")
    private String uriPath;

    /**
     * parentId
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * createdBy
     */
    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    /**
     * createdAt
     */
    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    /**
     * modifiedBy
     */
    @Column(name = "modified_by")
    private Long modifiedBy;

    /**
     * modifiedAt
     */
    @Column(name = "modified_at")
    private LocalDate modifiedAt;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "rel_menu__role", joinColumns = @JoinColumn(name = "menu_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnoreProperties(value = { "users", "resources", "menus", "userGroups" }, allowSetters = true)
    private Set<Role> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Menu id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Menu name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Menu description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDepth() {
        return this.depth;
    }

    public Menu depth(Integer depth) {
        this.depth = depth;
        return this;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getDispOrder() {
        return this.dispOrder;
    }

    public Menu dispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
        return this;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getDispYn() {
        return this.dispYn;
    }

    public Menu dispYn(String dispYn) {
        this.dispYn = dispYn;
        return this;
    }

    public void setDispYn(String dispYn) {
        this.dispYn = dispYn;
    }

    public String getUriPath() {
        return this.uriPath;
    }

    public Menu uriPath(String uriPath) {
        this.uriPath = uriPath;
        return this;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public Menu parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Menu createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Menu createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Long getModifiedBy() {
        return this.modifiedBy;
    }

    public Menu modifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedAt() {
        return this.modifiedAt;
    }

    public Menu modifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public Menu roles(Set<Role> roles) {
        this.setRoles(roles);
        return this;
    }

    public Menu addRole(Role role) {
        this.roles.add(role);
        role.getMenus().add(this);
        return this;
    }

    public Menu removeRole(Role role) {
        this.roles.remove(role);
        role.getMenus().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        return id != null && id.equals(((Menu) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Menu{" +
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
            "}";
    }
}
