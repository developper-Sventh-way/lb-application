package com.seventh.lotobig.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seventh.lotobig.domain.enumeration.TypeBanque;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PointOfSaleMembership.
 */
@Entity
@Table(name = "point_of_sale_membership")
public class PointOfSaleMembership implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_point", nullable = false)
    private TypeBanque typePoint;

    @NotNull
    @Size(min = 4, max = 45)
    @Column(name = "created_by", length = 45, nullable = false)
    private String createdBy;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Size(min = 4, max = 45)
    @Column(name = "last_modified_by", length = 45)
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pointOfSaleMemberships", "limitConfManagers" }, allowSetters = true)
    private MembershipConf membershipConf;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "creditSolde",
            "userPaymentConf",
            "pointOfSaleMemberships",
            "paiementBanques",
            "userSaleAccounts",
            "userRoles",
            "userPayments",
            "pointOfSale",
        },
        allowSetters = true
    )
    private Utilisateur utilisateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PointOfSaleMembership id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeBanque getTypePoint() {
        return this.typePoint;
    }

    public PointOfSaleMembership typePoint(TypeBanque typePoint) {
        this.setTypePoint(typePoint);
        return this;
    }

    public void setTypePoint(TypeBanque typePoint) {
        this.typePoint = typePoint;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public PointOfSaleMembership createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public PointOfSaleMembership createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public PointOfSaleMembership lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public PointOfSaleMembership lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public MembershipConf getMembershipConf() {
        return this.membershipConf;
    }

    public void setMembershipConf(MembershipConf membershipConf) {
        this.membershipConf = membershipConf;
    }

    public PointOfSaleMembership membershipConf(MembershipConf membershipConf) {
        this.setMembershipConf(membershipConf);
        return this;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public PointOfSaleMembership utilisateur(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointOfSaleMembership)) {
            return false;
        }
        return id != null && id.equals(((PointOfSaleMembership) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointOfSaleMembership{" +
            "id=" + getId() +
            ", typePoint='" + getTypePoint() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
