package com.seventh.lotobig.service.dto;

import com.seventh.lotobig.domain.enumeration.TypeOption;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.seventh.lotobig.domain.LimitConfPoint} entity.
 */
public class LimitConfPointDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(min = 2, max = 5)
    private String nombreValue;

    @NotNull
    private Long limit;

    private TypeOption limitStatut;

    private PointOfSaleDTO pointOfSale;

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

    public PointOfSaleDTO getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(PointOfSaleDTO pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LimitConfPointDTO)) {
            return false;
        }

        LimitConfPointDTO limitConfPointDTO = (LimitConfPointDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, limitConfPointDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LimitConfPointDTO{" +
            "id=" + getId() +
            ", nombreValue='" + getNombreValue() + "'" +
            ", limit=" + getLimit() +
            ", limitStatut='" + getLimitStatut() + "'" +
            ", pointOfSale=" + getPointOfSale() +
            "}";
    }
}
