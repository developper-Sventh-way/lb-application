package com.seventh.lotobig.service.dto;

import com.seventh.lotobig.domain.enumeration.DeviceStatut;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.seventh.lotobig.domain.POSConfiguration} entity.
 */
public class POSConfigurationDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @Size(max = 50)
    private String pOSName;

    @NotNull
    @Size(min = 4, max = 60)
    private String iMEI;

    @NotNull
    private DeviceStatut deviceStatut;

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

    public String getpOSName() {
        return pOSName;
    }

    public void setpOSName(String pOSName) {
        this.pOSName = pOSName;
    }

    public String getiMEI() {
        return iMEI;
    }

    public void setiMEI(String iMEI) {
        this.iMEI = iMEI;
    }

    public DeviceStatut getDeviceStatut() {
        return deviceStatut;
    }

    public void setDeviceStatut(DeviceStatut deviceStatut) {
        this.deviceStatut = deviceStatut;
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
        if (!(o instanceof POSConfigurationDTO)) {
            return false;
        }

        POSConfigurationDTO pOSConfigurationDTO = (POSConfigurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pOSConfigurationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "POSConfigurationDTO{" +
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
