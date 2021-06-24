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
 * The api entity.\n@author A true hipster
 */
@Entity
@Table(name = "api")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Api implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * name
     */
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * project_id
     */
    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    /**
     * manager_id
     */
    @NotNull
    @Column(name = "manager_id", nullable = false)
    private Long managerId;

    /**
     * api_type
     */
    @NotNull
    @Column(name = "api_type", nullable = false)
    private String apiType;

    /**
     * host
     */
    @Column(name = "host")
    private String host;

    /**
     * port
     */
    @Column(name = "port")
    private Integer port;

    /**
     * uri
     */
    @Column(name = "uri")
    private String uri;

    /**
     * version
     */
    @Column(name = "version")
    private String version;

    /**
     * created_by
     */
    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    /**
     * created_at
     */
    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    /**
     * modified_by
     */
    @Column(name = "modified_by")
    private Long modifiedBy;

    /**
     * modified_at
     */
    @Column(name = "modified_at")
    private LocalDate modifiedAt;

    @OneToMany(mappedBy = "api")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "api" }, allowSetters = true)
    private Set<ApiMeta> apiMetas = new HashSet<>();

    @ManyToMany(mappedBy = "apis")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apis" }, allowSetters = true)
    private Set<ApiRequest> apiRequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Api id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Api name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public Api projectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getManagerId() {
        return this.managerId;
    }

    public Api managerId(Long managerId) {
        this.managerId = managerId;
        return this;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getApiType() {
        return this.apiType;
    }

    public Api apiType(String apiType) {
        this.apiType = apiType;
        return this;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    public String getHost() {
        return this.host;
    }

    public Api host(String host) {
        this.host = host;
        return this;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public Api port(Integer port) {
        this.port = port;
        return this;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUri() {
        return this.uri;
    }

    public Api uri(String uri) {
        this.uri = uri;
        return this;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return this.version;
    }

    public Api version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Api createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Api createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Long getModifiedBy() {
        return this.modifiedBy;
    }

    public Api modifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedAt() {
        return this.modifiedAt;
    }

    public Api modifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Set<ApiMeta> getApiMetas() {
        return this.apiMetas;
    }

    public Api apiMetas(Set<ApiMeta> apiMetas) {
        this.setApiMetas(apiMetas);
        return this;
    }

    public Api addApiMeta(ApiMeta apiMeta) {
        this.apiMetas.add(apiMeta);
        apiMeta.setApi(this);
        return this;
    }

    public Api removeApiMeta(ApiMeta apiMeta) {
        this.apiMetas.remove(apiMeta);
        apiMeta.setApi(null);
        return this;
    }

    public void setApiMetas(Set<ApiMeta> apiMetas) {
        if (this.apiMetas != null) {
            this.apiMetas.forEach(i -> i.setApi(null));
        }
        if (apiMetas != null) {
            apiMetas.forEach(i -> i.setApi(this));
        }
        this.apiMetas = apiMetas;
    }

    public Set<ApiRequest> getApiRequests() {
        return this.apiRequests;
    }

    public Api apiRequests(Set<ApiRequest> apiRequests) {
        this.setApiRequests(apiRequests);
        return this;
    }

    public Api addApiRequest(ApiRequest apiRequest) {
        this.apiRequests.add(apiRequest);
        apiRequest.getApis().add(this);
        return this;
    }

    public Api removeApiRequest(ApiRequest apiRequest) {
        this.apiRequests.remove(apiRequest);
        apiRequest.getApis().remove(this);
        return this;
    }

    public void setApiRequests(Set<ApiRequest> apiRequests) {
        if (this.apiRequests != null) {
            this.apiRequests.forEach(i -> i.removeApi(this));
        }
        if (apiRequests != null) {
            apiRequests.forEach(i -> i.addApi(this));
        }
        this.apiRequests = apiRequests;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Api)) {
            return false;
        }
        return id != null && id.equals(((Api) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Api{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", projectId=" + getProjectId() +
            ", managerId=" + getManagerId() +
            ", apiType='" + getApiType() + "'" +
            ", host='" + getHost() + "'" +
            ", port=" + getPort() +
            ", uri='" + getUri() + "'" +
            ", version='" + getVersion() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            "}";
    }
}
