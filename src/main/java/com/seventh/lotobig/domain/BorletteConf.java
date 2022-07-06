package com.seventh.lotobig.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seventh.lotobig.domain.enumeration.TirageName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A BorletteConf.
 */
@Entity
@Table(name = "borlette_conf")
public class BorletteConf implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private TirageName name;

    @NotNull
    @Column(name = "premier_lot", nullable = false)
    private Integer premierLot;

    @NotNull
    @Column(name = "deuxieme_lot", nullable = false)
    private Integer deuxiemeLot;

    @NotNull
    @Column(name = "troisieme_lot", nullable = false)
    private Integer troisiemeLot;

    @Column(name = "mariage_gratis_prix", precision = 21, scale = 2)
    private BigDecimal mariageGratisPrix;

    @NotNull
    @Column(name = "montant_minimum", nullable = false)
    private Long montantMinimum;

    @NotNull
    @Column(name = "montant_maximum", nullable = false)
    private Long montantMaximum;

    @NotNull
    @Size(min = 5, max = 5)
    @Column(name = "close_time_midi", length = 5, nullable = false)
    private String closeTimeMidi;

    @NotNull
    @Size(min = 5, max = 5)
    @Column(name = "close_time_soir", length = 5, nullable = false)
    private String closeTimeSoir;

    @OneToMany(mappedBy = "borletteConf")
    @JsonIgnoreProperties(value = { "borletteConf" }, allowSetters = true)
    private Set<LimitConfBorlette> limitConfBorlettes = new HashSet<>();

    @OneToMany(mappedBy = "borletteConf")
    @JsonIgnoreProperties(value = { "tickets", "borletteConf" }, allowSetters = true)
    private Set<Tirage> tirages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BorletteConf id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TirageName getName() {
        return this.name;
    }

    public BorletteConf name(TirageName name) {
        this.setName(name);
        return this;
    }

    public void setName(TirageName name) {
        this.name = name;
    }

    public Integer getPremierLot() {
        return this.premierLot;
    }

    public BorletteConf premierLot(Integer premierLot) {
        this.setPremierLot(premierLot);
        return this;
    }

    public void setPremierLot(Integer premierLot) {
        this.premierLot = premierLot;
    }

    public Integer getDeuxiemeLot() {
        return this.deuxiemeLot;
    }

    public BorletteConf deuxiemeLot(Integer deuxiemeLot) {
        this.setDeuxiemeLot(deuxiemeLot);
        return this;
    }

    public void setDeuxiemeLot(Integer deuxiemeLot) {
        this.deuxiemeLot = deuxiemeLot;
    }

    public Integer getTroisiemeLot() {
        return this.troisiemeLot;
    }

    public BorletteConf troisiemeLot(Integer troisiemeLot) {
        this.setTroisiemeLot(troisiemeLot);
        return this;
    }

    public void setTroisiemeLot(Integer troisiemeLot) {
        this.troisiemeLot = troisiemeLot;
    }

    public BigDecimal getMariageGratisPrix() {
        return this.mariageGratisPrix;
    }

    public BorletteConf mariageGratisPrix(BigDecimal mariageGratisPrix) {
        this.setMariageGratisPrix(mariageGratisPrix);
        return this;
    }

    public void setMariageGratisPrix(BigDecimal mariageGratisPrix) {
        this.mariageGratisPrix = mariageGratisPrix;
    }

    public Long getMontantMinimum() {
        return this.montantMinimum;
    }

    public BorletteConf montantMinimum(Long montantMinimum) {
        this.setMontantMinimum(montantMinimum);
        return this;
    }

    public void setMontantMinimum(Long montantMinimum) {
        this.montantMinimum = montantMinimum;
    }

    public Long getMontantMaximum() {
        return this.montantMaximum;
    }

    public BorletteConf montantMaximum(Long montantMaximum) {
        this.setMontantMaximum(montantMaximum);
        return this;
    }

    public void setMontantMaximum(Long montantMaximum) {
        this.montantMaximum = montantMaximum;
    }

    public String getCloseTimeMidi() {
        return this.closeTimeMidi;
    }

    public BorletteConf closeTimeMidi(String closeTimeMidi) {
        this.setCloseTimeMidi(closeTimeMidi);
        return this;
    }

    public void setCloseTimeMidi(String closeTimeMidi) {
        this.closeTimeMidi = closeTimeMidi;
    }

    public String getCloseTimeSoir() {
        return this.closeTimeSoir;
    }

    public BorletteConf closeTimeSoir(String closeTimeSoir) {
        this.setCloseTimeSoir(closeTimeSoir);
        return this;
    }

    public void setCloseTimeSoir(String closeTimeSoir) {
        this.closeTimeSoir = closeTimeSoir;
    }

    public Set<LimitConfBorlette> getLimitConfBorlettes() {
        return this.limitConfBorlettes;
    }

    public void setLimitConfBorlettes(Set<LimitConfBorlette> limitConfBorlettes) {
        if (this.limitConfBorlettes != null) {
            this.limitConfBorlettes.forEach(i -> i.setBorletteConf(null));
        }
        if (limitConfBorlettes != null) {
            limitConfBorlettes.forEach(i -> i.setBorletteConf(this));
        }
        this.limitConfBorlettes = limitConfBorlettes;
    }

    public BorletteConf limitConfBorlettes(Set<LimitConfBorlette> limitConfBorlettes) {
        this.setLimitConfBorlettes(limitConfBorlettes);
        return this;
    }

    public BorletteConf addLimitConfBorlette(LimitConfBorlette limitConfBorlette) {
        this.limitConfBorlettes.add(limitConfBorlette);
        limitConfBorlette.setBorletteConf(this);
        return this;
    }

    public BorletteConf removeLimitConfBorlette(LimitConfBorlette limitConfBorlette) {
        this.limitConfBorlettes.remove(limitConfBorlette);
        limitConfBorlette.setBorletteConf(null);
        return this;
    }

    public Set<Tirage> getTirages() {
        return this.tirages;
    }

    public void setTirages(Set<Tirage> tirages) {
        if (this.tirages != null) {
            this.tirages.forEach(i -> i.setBorletteConf(null));
        }
        if (tirages != null) {
            tirages.forEach(i -> i.setBorletteConf(this));
        }
        this.tirages = tirages;
    }

    public BorletteConf tirages(Set<Tirage> tirages) {
        this.setTirages(tirages);
        return this;
    }

    public BorletteConf addTirage(Tirage tirage) {
        this.tirages.add(tirage);
        tirage.setBorletteConf(this);
        return this;
    }

    public BorletteConf removeTirage(Tirage tirage) {
        this.tirages.remove(tirage);
        tirage.setBorletteConf(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BorletteConf)) {
            return false;
        }
        return id != null && id.equals(((BorletteConf) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BorletteConf{" +
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
