package com.seventh.lotobig.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seventh.lotobig.domain.enumeration.TypeOption;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LimitConfBorlette.
 */
@Entity
@Table(name = "limit_conf_borlette")
public class LimitConfBorlette implements Serializable {

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

    @Size(min = 4, max = 45)
    @Column(name = "created_by", length = 45)
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Size(min = 4, max = 45)
    @Column(name = "last_modified_by", length = 45)
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "limitConfBorlettes", "tirages" }, allowSetters = true)
    private BorletteConf borletteConf;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LimitConfBorlette id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreValue() {
        return this.nombreValue;
    }

    public LimitConfBorlette nombreValue(String nombreValue) {
        this.setNombreValue(nombreValue);
        return this;
    }

    public void setNombreValue(String nombreValue) {
        this.nombreValue = nombreValue;
    }

    public Long getLimit() {
        return this.limit;
    }

    public LimitConfBorlette limit(Long limit) {
        this.setLimit(limit);
        return this;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public TypeOption getLimitStatut() {
        return this.limitStatut;
    }

    public LimitConfBorlette limitStatut(TypeOption limitStatut) {
        this.setLimitStatut(limitStatut);
        return this;
    }

    public void setLimitStatut(TypeOption limitStatut) {
        this.limitStatut = limitStatut;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public LimitConfBorlette createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public LimitConfBorlette createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LimitConfBorlette lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public LimitConfBorlette lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public BorletteConf getBorletteConf() {
        return this.borletteConf;
    }

    public void setBorletteConf(BorletteConf borletteConf) {
        this.borletteConf = borletteConf;
    }

    public LimitConfBorlette borletteConf(BorletteConf borletteConf) {
        this.setBorletteConf(borletteConf);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LimitConfBorlette)) {
            return false;
        }
        return id != null && id.equals(((LimitConfBorlette) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LimitConfBorlette{" +
            "id=" + getId() +
            ", nombreValue='" + getNombreValue() + "'" +
            ", limit=" + getLimit() +
            ", limitStatut='" + getLimitStatut() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
