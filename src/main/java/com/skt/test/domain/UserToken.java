package com.skt.test.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The UserToken entity.\n@author A true hipster
 */
@Entity
@Table(name = "user_token")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * acc_token
     */
    @Column(name = "acc_token")
    private String accToken;

    /**
     * acc_exp_time
     */
    @Column(name = "acc_exp_time")
    private LocalDate accExpTime;

    /**
     * ref_token
     */
    @Column(name = "ref_token")
    private String refToken;

    /**
     * ref_exp_time
     */
    @Column(name = "ref_exp_time")
    private LocalDate refExpTime;

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

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserToken id(Long id) {
        this.id = id;
        return this;
    }

    public String getAccToken() {
        return this.accToken;
    }

    public UserToken accToken(String accToken) {
        this.accToken = accToken;
        return this;
    }

    public void setAccToken(String accToken) {
        this.accToken = accToken;
    }

    public LocalDate getAccExpTime() {
        return this.accExpTime;
    }

    public UserToken accExpTime(LocalDate accExpTime) {
        this.accExpTime = accExpTime;
        return this;
    }

    public void setAccExpTime(LocalDate accExpTime) {
        this.accExpTime = accExpTime;
    }

    public String getRefToken() {
        return this.refToken;
    }

    public UserToken refToken(String refToken) {
        this.refToken = refToken;
        return this;
    }

    public void setRefToken(String refToken) {
        this.refToken = refToken;
    }

    public LocalDate getRefExpTime() {
        return this.refExpTime;
    }

    public UserToken refExpTime(LocalDate refExpTime) {
        this.refExpTime = refExpTime;
        return this;
    }

    public void setRefExpTime(LocalDate refExpTime) {
        this.refExpTime = refExpTime;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public UserToken createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public UserToken createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return this.user;
    }

    public UserToken user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserToken)) {
            return false;
        }
        return id != null && id.equals(((UserToken) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserToken{" +
            "id=" + getId() +
            ", accToken='" + getAccToken() + "'" +
            ", accExpTime='" + getAccExpTime() + "'" +
            ", refToken='" + getRefToken() + "'" +
            ", refExpTime='" + getRefExpTime() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
