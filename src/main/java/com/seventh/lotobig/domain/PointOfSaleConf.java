package com.seventh.lotobig.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PointOfSaleConf.
 */
@Entity
@Table(name = "point_of_sale_conf")
public class PointOfSaleConf implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "pourcentage_caissier")
    private Integer pourcentageCaissier;

    @Column(name = "pourcentage_responsable")
    private Integer pourcentageResponsable;

    @Size(min = 5, max = 5)
    @Column(name = "start_time", length = 5)
    private String startTime;

    @Size(min = 5, max = 5)
    @Column(name = "end_time", length = 5)
    private String endTime;

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

    @JsonIgnoreProperties(
        value = { "pointOfSaleConf", "limitConfPoints", "utilisateurs", "paiementBanques", "tickets" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "pointOfSaleConf")
    private PointOfSale pointOfSale;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PointOfSaleConf id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPourcentageCaissier() {
        return this.pourcentageCaissier;
    }

    public PointOfSaleConf pourcentageCaissier(Integer pourcentageCaissier) {
        this.setPourcentageCaissier(pourcentageCaissier);
        return this;
    }

    public void setPourcentageCaissier(Integer pourcentageCaissier) {
        this.pourcentageCaissier = pourcentageCaissier;
    }

    public Integer getPourcentageResponsable() {
        return this.pourcentageResponsable;
    }

    public PointOfSaleConf pourcentageResponsable(Integer pourcentageResponsable) {
        this.setPourcentageResponsable(pourcentageResponsable);
        return this;
    }

    public void setPourcentageResponsable(Integer pourcentageResponsable) {
        this.pourcentageResponsable = pourcentageResponsable;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public PointOfSaleConf startTime(String startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public PointOfSaleConf endTime(String endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public PointOfSaleConf createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public PointOfSaleConf createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public PointOfSaleConf lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public PointOfSaleConf lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public PointOfSale getPointOfSale() {
        return this.pointOfSale;
    }

    public void setPointOfSale(PointOfSale pointOfSale) {
        if (this.pointOfSale != null) {
            this.pointOfSale.setPointOfSaleConf(null);
        }
        if (pointOfSale != null) {
            pointOfSale.setPointOfSaleConf(this);
        }
        this.pointOfSale = pointOfSale;
    }

    public PointOfSaleConf pointOfSale(PointOfSale pointOfSale) {
        this.setPointOfSale(pointOfSale);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointOfSaleConf)) {
            return false;
        }
        return id != null && id.equals(((PointOfSaleConf) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointOfSaleConf{" +
            "id=" + getId() +
            ", pourcentageCaissier=" + getPourcentageCaissier() +
            ", pourcentageResponsable=" + getPourcentageResponsable() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
