package com.seventh.lotobig.service.dto;

import com.seventh.lotobig.domain.enumeration.TirageName;
import com.seventh.lotobig.domain.enumeration.TirageType;
import com.seventh.lotobig.domain.enumeration.UserStatut;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.seventh.lotobig.domain.Tirage} entity.
 */
public class TirageDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private TirageName tirageName;

    @NotNull
    private TirageType type;

    @Size(min = 2, max = 2)
    private String premierLot;

    @Size(min = 2, max = 2)
    private String deuxiemeLot;

    @Size(min = 2, max = 2)
    private String troisiemeLot;

    @Size(min = 2, max = 2)
    private String loto3Chif;

    @NotNull
    private UserStatut statut;

    @NotNull
    @Size(min = 4, max = 45)
    private String createdBy;

    @NotNull
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

    public TirageName getTirageName() {
        return tirageName;
    }

    public void setTirageName(TirageName tirageName) {
        this.tirageName = tirageName;
    }

    public TirageType getType() {
        return type;
    }

    public void setType(TirageType type) {
        this.type = type;
    }

    public String getPremierLot() {
        return premierLot;
    }

    public void setPremierLot(String premierLot) {
        this.premierLot = premierLot;
    }

    public String getDeuxiemeLot() {
        return deuxiemeLot;
    }

    public void setDeuxiemeLot(String deuxiemeLot) {
        this.deuxiemeLot = deuxiemeLot;
    }

    public String getTroisiemeLot() {
        return troisiemeLot;
    }

    public void setTroisiemeLot(String troisiemeLot) {
        this.troisiemeLot = troisiemeLot;
    }

    public String getLoto3Chif() {
        return loto3Chif;
    }

    public void setLoto3Chif(String loto3Chif) {
        this.loto3Chif = loto3Chif;
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
        if (!(o instanceof TirageDTO)) {
            return false;
        }

        TirageDTO tirageDTO = (TirageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tirageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TirageDTO{" +
            "id=" + getId() +
            ", tirageName='" + getTirageName() + "'" +
            ", type='" + getType() + "'" +
            ", premierLot='" + getPremierLot() + "'" +
            ", deuxiemeLot='" + getDeuxiemeLot() + "'" +
            ", troisiemeLot='" + getTroisiemeLot() + "'" +
            ", loto3Chif='" + getLoto3Chif() + "'" +
            ", statut='" + getStatut() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", borletteConf=" + getBorletteConf() +
            "}";
    }
}
