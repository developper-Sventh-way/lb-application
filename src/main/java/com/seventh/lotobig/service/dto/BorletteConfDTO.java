package com.seventh.lotobig.service.dto;

import com.seventh.lotobig.domain.enumeration.TirageName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.seventh.lotobig.domain.BorletteConf} entity.
 */
public class BorletteConfDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private TirageName name;

    @NotNull
    private Integer premierLot;

    @NotNull
    private Integer deuxiemeLot;

    @NotNull
    private Integer troisiemeLot;

    private BigDecimal mariageGratisPrix;

    @NotNull
    private Long montantMinimum;

    @NotNull
    private Long montantMaximum;

    @NotNull
    @Size(min = 5, max = 5)
    private String closeTimeMidi;

    @NotNull
    @Size(min = 5, max = 5)
    private String closeTimeSoir;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TirageName getName() {
        return name;
    }

    public void setName(TirageName name) {
        this.name = name;
    }

    public Integer getPremierLot() {
        return premierLot;
    }

    public void setPremierLot(Integer premierLot) {
        this.premierLot = premierLot;
    }

    public Integer getDeuxiemeLot() {
        return deuxiemeLot;
    }

    public void setDeuxiemeLot(Integer deuxiemeLot) {
        this.deuxiemeLot = deuxiemeLot;
    }

    public Integer getTroisiemeLot() {
        return troisiemeLot;
    }

    public void setTroisiemeLot(Integer troisiemeLot) {
        this.troisiemeLot = troisiemeLot;
    }

    public BigDecimal getMariageGratisPrix() {
        return mariageGratisPrix;
    }

    public void setMariageGratisPrix(BigDecimal mariageGratisPrix) {
        this.mariageGratisPrix = mariageGratisPrix;
    }

    public Long getMontantMinimum() {
        return montantMinimum;
    }

    public void setMontantMinimum(Long montantMinimum) {
        this.montantMinimum = montantMinimum;
    }

    public Long getMontantMaximum() {
        return montantMaximum;
    }

    public void setMontantMaximum(Long montantMaximum) {
        this.montantMaximum = montantMaximum;
    }

    public String getCloseTimeMidi() {
        return closeTimeMidi;
    }

    public void setCloseTimeMidi(String closeTimeMidi) {
        this.closeTimeMidi = closeTimeMidi;
    }

    public String getCloseTimeSoir() {
        return closeTimeSoir;
    }

    public void setCloseTimeSoir(String closeTimeSoir) {
        this.closeTimeSoir = closeTimeSoir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BorletteConfDTO)) {
            return false;
        }

        BorletteConfDTO borletteConfDTO = (BorletteConfDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, borletteConfDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BorletteConfDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", premierLot=" + getPremierLot() +
            ", deuxiemeLot=" + getDeuxiemeLot() +
            ", troisiemeLot=" + getTroisiemeLot() +
            ", mariageGratisPrix=" + getMariageGratisPrix() +
            ", montantMinimum=" + getMontantMinimum() +
            ", montantMaximum=" + getMontantMaximum() +
            ", closeTimeMidi='" + getCloseTimeMidi() + "'" +
            ", closeTimeSoir='" + getCloseTimeSoir() + "'" +
            "}";
    }
}
