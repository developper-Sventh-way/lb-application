package com.seventh.lotobig.service.dto;

import com.seventh.lotobig.domain.enumeration.TypeOption;
import com.seventh.lotobig.domain.enumeration.UserStatut;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.seventh.lotobig.domain.CouponGratuitConf} entity.
 */
public class CouponGratuitConfDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private TypeOption typeOption;

    @NotNull
    private Long maximumCount;

    @NotNull
    private BigDecimal obstinateAmount;

    @NotNull
    private UserStatut statut;

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

    public TypeOption getTypeOption() {
        return typeOption;
    }

    public void setTypeOption(TypeOption typeOption) {
        this.typeOption = typeOption;
    }

    public Long getMaximumCount() {
        return maximumCount;
    }

    public void setMaximumCount(Long maximumCount) {
        this.maximumCount = maximumCount;
    }

    public BigDecimal getObstinateAmount() {
        return obstinateAmount;
    }

    public void setObstinateAmount(BigDecimal obstinateAmount) {
        this.obstinateAmount = obstinateAmount;
    }

    public UserStatut getStatut() {
        return statut;
    }

    public void setStatut(UserStatut statut) {
        this.statut = statut;
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
        if (!(o instanceof CouponGratuitConfDTO)) {
            return false;
        }

        CouponGratuitConfDTO couponGratuitConfDTO = (CouponGratuitConfDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, couponGratuitConfDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CouponGratuitConfDTO{" +
            "id=" + getId() +
            ", typeOption='" + getTypeOption() + "'" +
            ", maximumCount=" + getMaximumCount() +
            ", obstinateAmount=" + getObstinateAmount() +
            ", statut='" + getStatut() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
