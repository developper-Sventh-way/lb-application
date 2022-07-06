package com.seventh.lotobig.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seventh.lotobig.domain.enumeration.TypeOption;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LimitConfManager.
 */
@Entity
@Table(name = "limit_conf_manager")
public class LimitConfManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(min = 2, max = 5)
    @Column(name = "nombre_value", length = 5)
    private String nombreValue;

    @NotNull
    @Column(name = "jhi_limit", nullable = false)
    private Long limit;

    @Enumerated(EnumType.STRING)
    @Column(name = "limit_statut")
    private TypeOption limitStatut;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pointOfSaleMemberships", "limitConfManagers" }, allowSetters = true)
    private MembershipConf membershipConf;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LimitConfManager id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreValue() {
        return this.nombreValue;
    }

    public LimitConfManager nombreValue(String nombreValue) {
        this.setNombreValue(nombreValue);
        return this;
    }

    public void setNombreValue(String nombreValue) {
        this.nombreValue = nombreValue;
    }

    public Long getLimit() {
        return this.limit;
    }

    public LimitConfManager limit(Long limit) {
        this.setLimit(limit);
        return this;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public TypeOption getLimitStatut() {
        return this.limitStatut;
    }

    public LimitConfManager limitStatut(TypeOption limitStatut) {
        this.setLimitStatut(limitStatut);
        return this;
    }

    public void setLimitStatut(TypeOption limitStatut) {
        this.limitStatut = limitStatut;
    }

    public MembershipConf getMembershipConf() {
        return this.membershipConf;
    }

    public void setMembershipConf(MembershipConf membershipConf) {
        this.membershipConf = membershipConf;
    }

    public LimitConfManager membershipConf(MembershipConf membershipConf) {
        this.setMembershipConf(membershipConf);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LimitConfManager)) {
            return false;
        }
        return id != null && id.equals(((LimitConfManager) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LimitConfManager{" +
            "id=" + getId() +
            ", nombreValue='" + getNombreValue() + "'" +
            ", limit=" + getLimit() +
            ", limitStatut='" + getLimitStatut() + "'" +
            "}";
    }
}
