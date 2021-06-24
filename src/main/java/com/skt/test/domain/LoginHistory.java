package com.skt.test.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The LoginHistory entity.\n@author A true hipster
 */
@Entity
@Table(name = "login_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LoginHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * created_at
     */
    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    /**
     * userAgent
     */
    @Column(name = "user_agent")
    private String userAgent;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoginHistory id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public LoginHistory createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public LoginHistory userAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public User getUser() {
        return this.user;
    }

    public LoginHistory user(User user) {
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
        if (!(o instanceof LoginHistory)) {
            return false;
        }
        return id != null && id.equals(((LoginHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoginHistory{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            "}";
    }
}
