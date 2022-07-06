package com.seventh.lotobig.domain;

import com.seventh.lotobig.domain.enumeration.TypeOption;
import com.seventh.lotobig.domain.enumeration.UserStatut;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CouponGratuitConf.
 */
@Entity
@Table(name = "coupon_gratuit_conf")
public class CouponGratuitConf implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_option", nullable = false)
    private TypeOption typeOption;

    @NotNull
    @Column(name = "maximum_count", nullable = false)
    private Long maximumCount;

    @NotNull
    @Column(name = "obstinate_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal obstinateAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private UserStatut statut;

    @Size(min = 4, max = 45)
    @Column(name = "created_by", length = 45)
    private String createdBy;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Size(min = 4, max = 45)
    @Column(name = "last_modified_by", length = 45)
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CouponGratuitConf id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOption getTypeOption() {
        return this.typeOption;
    }

    public CouponGratuitConf typeOption(TypeOption typeOption) {
        this.setTypeOption(typeOption);
        return this;
    }

    public void setTypeOption(TypeOption typeOption) {
        this.typeOption = typeOption;
    }

    public Long getMaximumCount() {
        return this.maximumCount;
    }

    public CouponGratuitConf maximumCount(Long maximumCount) {
        this.setMaximumCount(maximumCount);
        return this;
    }

    public void setMaximumCount(Long maximumCount) {
        this.maximumCount = maximumCount;
    }

    public BigDecimal getObstinateAmount() {
        return this.obstinateAmount;
    }

    public CouponGratuitConf obstinateAmount(BigDecimal obstinateAmount) {
        this.setObstinateAmount(obstinateAmount);
        return this;
    }

    public void setObstinateAmount(BigDecimal obstinateAmount) {
        this.obstinateAmount = obstinateAmount;
    }

    public UserStatut getStatut() {
        return this.statut;
    }

    public CouponGratuitConf statut(UserStatut statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(UserStatut statut) {
        this.statut = statut;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public CouponGratuitConf createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public CouponGratuitConf createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public CouponGratuitConf lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public CouponGratuitConf lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CouponGratuitConf)) {
            return false;
        }
        return id != null && id.equals(((CouponGratuitConf) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CouponGratuitConf{" +
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
