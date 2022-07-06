package com.seventh.lotobig.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.seventh.lotobig.domain.PointOfSaleConf} entity.
 */
public class PointOfSaleConfDTO implements Serializable {

    @NotNull
    private Long id;

    private Integer pourcentageCaissier;

    private Integer pourcentageResponsable;

    @Size(min = 5, max = 5)
    private String startTime;

    @Size(min = 5, max = 5)
    private String endTime;

    @NotNull
    @Size(min = 4, max = 45)
    private String createdBy;

    @NotNull
    private Instant createdDate;

    @Size(min = 4, max = 45)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPourcentageCaissier() {
        return pourcentageCaissier;
    }

    public void setPourcentageCaissier(Integer pourcentageCaissier) {
        this.pourcentageCaissier = pourcentageCaissier;
    }

    public Integer getPourcentageResponsable() {
        return pourcentageResponsable;
    }

    public void setPourcentageResponsable(Integer pourcentageResponsable) {
        this.pourcentageResponsable = pourcentageResponsable;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointOfSaleConfDTO)) {
            return false;
        }

        PointOfSaleConfDTO pointOfSaleConfDTO = (PointOfSaleConfDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pointOfSaleConfDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointOfSaleConfDTO{" +
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
