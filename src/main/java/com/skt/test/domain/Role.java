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
 * The Role entity.\n@author A true hipster
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role implements Serializable {

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
     * type
     */
    @Column(name = "type")
    private String type;

    /**
     * applyTarget
     */
    @Column(name = "apply_target")
    private String applyTarget;

    /**
     * permission
     */
    @Column(name = "permission")
    private String permission;

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
    @JoinTable(name = "rel_role__user", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_role__resource",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "resource_id")
    )
    @JsonIgnoreProperties(value = { "resourceMetas", "roles", "projectResources" }, allowSetters = true)
    private Set<Resource> resources = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "roles" }, allowSetters = true)
    private Set<Menu> menus = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users", "roles" }, allowSetters = true)
    private Set<UserGroup> userGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Role name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Role description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public Role type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplyTarget() {
        return this.applyTarget;
    }

    public Role applyTarget(String applyTarget) {
        this.applyTarget = applyTarget;
        return this;
    }

    public void setApplyTarget(String applyTarget) {
        this.applyTarget = applyTarget;
    }

    public String getPermission() {
        return this.permission;
    }

    public Role permission(String permission) {
        this.permission = permission;
        return this;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Role createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Role createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Long getModifiedBy() {
        return this.modifiedBy;
    }

    public Role modifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedAt() {
        return this.modifiedAt;
    }

    public Role modifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public Role users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Role addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Role removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Resource> getResources() {
        return this.resources;
    }

    public Role resources(Set<Resource> resources) {
        this.setResources(resources);
        return this;
    }

    public Role addResource(Resource resource) {
        this.resources.add(resource);
        resource.getRoles().add(this);
        return this;
    }

    public Role removeResource(Resource resource) {
        this.resources.remove(resource);
        resource.getRoles().remove(this);
        return this;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public Set<Menu> getMenus() {
        return this.menus;
    }

    public Role menus(Set<Menu> menus) {
        this.setMenus(menus);
        return this;
    }

    public Role addMenu(Menu menu) {
        this.menus.add(menu);
        menu.getRoles().add(this);
        return this;
    }

    public Role removeMenu(Menu menu) {
        this.menus.remove(menu);
        menu.getRoles().remove(this);
        return this;
    }

    public void setMenus(Set<Menu> menus) {
        if (this.menus != null) {
            this.menus.forEach(i -> i.removeRole(this));
        }
        if (menus != null) {
            menus.forEach(i -> i.addRole(this));
        }
        this.menus = menus;
    }

    public Set<UserGroup> getUserGroups() {
        return this.userGroups;
    }

    public Role userGroups(Set<UserGroup> userGroups) {
        this.setUserGroups(userGroups);
        return this;
    }

    public Role addUserGroup(UserGroup userGroup) {
        this.userGroups.add(userGroup);
        userGroup.getRoles().add(this);
        return this;
    }

    public Role removeUserGroup(UserGroup userGroup) {
        this.userGroups.remove(userGroup);
        userGroup.getRoles().remove(this);
        return this;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        if (this.userGroups != null) {
            this.userGroups.forEach(i -> i.removeRole(this));
        }
        if (userGroups != null) {
            userGroups.forEach(i -> i.addRole(this));
        }
        this.userGroups = userGroups;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", applyTarget='" + getApplyTarget() + "'" +
            ", permission='" + getPermission() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            "}";
    }
}
