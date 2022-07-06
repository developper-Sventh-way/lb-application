package com.seventh.lotobig.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A MembershipConf.
 */
@Entity
@Table(name = "membership_conf")
public class MembershipConf implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(min = 4, max = 100)
    @Column(name = "nom_client", length = 100)
    private String nomClient;

    @Size(max = 60)
    @Column(name = "slogan", length = 60)
    private String slogan;

    @Size(max = 60)
    @Column(name = "telephones", length = 60)
    private String telephones;

    @Size(max = 60)
    @Column(name = "adresse", length = 60)
    private String adresse;

    @Size(max = 120)
    @Column(name = "infos", length = 120)
    private String infos;

    @Column(name = "logo_link")
    private String logoLink;

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

    @OneToMany(mappedBy = "membershipConf")
    @JsonIgnoreProperties(value = { "membershipConf", "utilisateur" }, allowSetters = true)
    private Set<PointOfSaleMembership> pointOfSaleMemberships = new HashSet<>();

    @OneToMany(mappedBy = "membershipConf")
    @JsonIgnoreProperties(value = { "membershipConf" }, allowSetters = true)
    private Set<LimitConfManager> limitConfManagers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MembershipConf id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomClient() {
        return this.nomClient;
    }

    public MembershipConf nomClient(String nomClient) {
        this.setNomClient(nomClient);
        return this;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getSlogan() {
        return this.slogan;
    }

    public MembershipConf slogan(String slogan) {
        this.setSlogan(slogan);
        return this;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getTelephones() {
        return this.telephones;
    }

    public MembershipConf telephones(String telephones) {
        this.setTelephones(telephones);
        return this;
    }

    public void setTelephones(String telephones) {
        this.telephones = telephones;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public MembershipConf adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getInfos() {
        return this.infos;
    }

    public MembershipConf infos(String infos) {
        this.setInfos(infos);
        return this;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public String getLogoLink() {
        return this.logoLink;
    }

    public MembershipConf logoLink(String logoLink) {
        this.setLogoLink(logoLink);
        return this;
    }

    public void setLogoLink(String logoLink) {
        this.logoLink = logoLink;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public MembershipConf createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public MembershipConf createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public MembershipConf lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public MembershipConf lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<PointOfSaleMembership> getPointOfSaleMemberships() {
        return this.pointOfSaleMemberships;
    }

    public void setPointOfSaleMemberships(Set<PointOfSaleMembership> pointOfSaleMemberships) {
        if (this.pointOfSaleMemberships != null) {
            this.pointOfSaleMemberships.forEach(i -> i.setMembershipConf(null));
        }
        if (pointOfSaleMemberships != null) {
            pointOfSaleMemberships.forEach(i -> i.setMembershipConf(this));
        }
        this.pointOfSaleMemberships = pointOfSaleMemberships;
    }

    public MembershipConf pointOfSaleMemberships(Set<PointOfSaleMembership> pointOfSaleMemberships) {
        this.setPointOfSaleMemberships(pointOfSaleMemberships);
        return this;
    }

    public MembershipConf addPointOfSaleMembership(PointOfSaleMembership pointOfSaleMembership) {
        this.pointOfSaleMemberships.add(pointOfSaleMembership);
        pointOfSaleMembership.setMembershipConf(this);
        return this;
    }

    public MembershipConf removePointOfSaleMembership(PointOfSaleMembership pointOfSaleMembership) {
        this.pointOfSaleMemberships.remove(pointOfSaleMembership);
        pointOfSaleMembership.setMembershipConf(null);
        return this;
    }

    public Set<LimitConfManager> getLimitConfManagers() {
        return this.limitConfManagers;
    }

    public void setLimitConfManagers(Set<LimitConfManager> limitConfManagers) {
        if (this.limitConfManagers != null) {
            this.limitConfManagers.forEach(i -> i.setMembershipConf(null));
        }
        if (limitConfManagers != null) {
            limitConfManagers.forEach(i -> i.setMembershipConf(this));
        }
        this.limitConfManagers = limitConfManagers;
    }

    public MembershipConf limitConfManagers(Set<LimitConfManager> limitConfManagers) {
        this.setLimitConfManagers(limitConfManagers);
        return this;
    }

    public MembershipConf addLimitConfManager(LimitConfManager limitConfManager) {
        this.limitConfManagers.add(limitConfManager);
        limitConfManager.setMembershipConf(this);
        return this;
    }

    public MembershipConf removeLimitConfManager(LimitConfManager limitConfManager) {
        this.limitConfManagers.remove(limitConfManager);
        limitConfManager.setMembershipConf(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MembershipConf)) {
            return false;
        }
        return id != null && id.equals(((MembershipConf) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MembershipConf{" +
            "id=" + getId() +
            ", nomClient='" + getNomClient() + "'" +
            ", slogan='" + getSlogan() + "'" +
            ", telephones='" + getTelephones() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", infos='" + getInfos() + "'" +
            ", logoLink='" + getLogoLink() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
