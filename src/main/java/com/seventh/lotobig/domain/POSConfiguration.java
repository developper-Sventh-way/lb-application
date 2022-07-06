package com.seventh.lotobig.domain;

import com.seventh.lotobig.domain.enumeration.DeviceStatut;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A POSConfiguration.
 */
@Entity
@Table(name = "pos_configuration")
public class POSConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "p_os_name", length = 50, nullable = false)
    private String pOSName;

    @NotNull
    @Size(min = 4, max = 60)
    @Column(name = "i_mei", length = 60, nullable = false)
    private String iMEI;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "device_statut", nullable = false)
    private DeviceStatut deviceStatut;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public POSConfiguration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getpOSName() {
        return this.pOSName;
    }

    public POSConfiguration pOSName(String pOSName) {
        this.setpOSName(pOSName);
        return this;
    }

    public void setpOSName(String pOSName) {
        this.pOSName = pOSName;
    }

    public String getiMEI() {
        return this.iMEI;
    }

    public POSConfiguration iMEI(String iMEI) {
        this.setiMEI(iMEI);
        return this;
    }

    public void setiMEI(String iMEI) {
        this.iMEI = iMEI;
    }

    public DeviceStatut getDeviceStatut() {
        return this.deviceStatut;
    }

    public POSConfiguration deviceStatut(DeviceStatut deviceStatut) {
        this.setDeviceStatut(deviceStatut);
        return this;
    }

    public void setDeviceStatut(DeviceStatut deviceStatut) {
        this.deviceStatut = deviceStatut;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public POSConfiguration createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public POSConfiguration createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public POSConfiguration lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public POSConfiguration lastModifiedDate(Instant lastModifiedDate) {
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
        if (!(o instanceof POSConfiguration)) {
            return false;
        }
        return id != null && id.equals(((POSConfiguration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "POSConfiguration{" +
            "id=" + getId() +
            ", pOSName='" + getpOSName() + "'" +
            ", iMEI='" + getiMEI() + "'" +
            ", deviceStatut='" + getDeviceStatut() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
