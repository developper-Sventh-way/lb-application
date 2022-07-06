package com.seventh.lotobig.service.dto;

import com.seventh.lotobig.domain.enumeration.TypeOption;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.seventh.lotobig.domain.LimitConfBorlette} entity.
 */
public class LimitConfBorletteDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(min = 2, max = 5)
    private String nombreValue;

    @NotNull
    private Long limit;

    private TypeOption limitStatut;

    @Size(min = 4, max = 45)
    private String createdBy;

    private Instant createdDate;

    @Size(min = 4, max = 45)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private BorletteConfDTO borletteConf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreValue() {
        return nombreValue;
    }

    public void setNombreValue(String nombreValue) {
        this.nombreValue = nombreValue;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public TypeOption getLimitStatut() {
        return limitStatut;
    }

    public void setLimitStatut(TypeOption limitStatut) {
        this.limitStatut = limitStatut;
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

    public BorletteConfDTO getBorletteConf() {
        return borletteConf;
    }

    public void setBorletteConf(BorletteConfDTO borletteConf) {
        this.borletteConf = borletteConf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LimitConfBorletteDTO)) {
            return false;
        }

        LimitConfBorletteDTO limitConfBorletteDTO = (LimitConfBorletteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, limitConfBorletteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LimitConfBorletteDTO{" +
            "id=" + getId() +
            ", nombreValue='" + getNombreValue() + "'" +
            ", limit=" + getLimit() +
            ", limitStatut='" + getLimitStatut() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", borletteConf=" + getBorletteConf() +
            "}";
    }
}
