package com.seventh.lotobig.service.dto;

import com.seventh.lotobig.domain.enumeration.StatutBanque;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.seventh.lotobig.domain.PointOfSale} entity.
 */
public class PointOfSaleDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(min = 4, max = 80)
    private String adressePoint;

    @NotNull
    private StatutBanque statut;

    @Size(min = 6, max = 30)
    private String phone1;

    @Size(min = 6, max = 30)
    private String phone2;

    @NotNull
    private Integer pourcentagePoint;

    @NotNull
    @Size(min = 4, max = 45)
    private String createdBy;

    @NotNull
    private Instant createdDate;

    @Size(min = 4, max = 45)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private PointOfSaleConfDTO pointOfSaleConf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdressePoint() {
        return adressePoint;
    }

    public void setAdressePoint(String adressePoint) {
        this.adressePoint = adressePoint;
    }

    public StatutBanque getStatut() {
        return statut;
    }

    public void setStatut(StatutBanque statut) {
        this.statut = statut;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public Integer getPourcentagePoint() {
        return pourcentagePoint;
    }

    public void setPourcentagePoint(Integer pourcentagePoint) {
        this.pourcentagePoint = pourcentagePoint;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public PointOfSaleConfDTO getPointOfSaleConf() {
        return pointOfSaleConf;
    }

    public void setPointOfSaleConf(PointOfSaleConfDTO pointOfSaleConf) {
        this.pointOfSaleConf = pointOfSaleConf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointOfSaleDTO)) {
            return false;
        }

        PointOfSaleDTO pointOfSaleDTO = (PointOfSaleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pointOfSaleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointOfSaleDTO{" +
            "id=" + getId() +
            ", adressePoint='" + getAdressePoint() + "'" +
            ", statut='" + getStatut() + "'" +
            ", phone1='" + getPhone1() + "'" +
            ", phone2='" + getPhone2() + "'" +
            ", pourcentagePoint=" + getPourcentagePoint() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", pointOfSaleConf=" + getPointOfSaleConf() +
            "}";
    }
}
