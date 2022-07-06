package com.seventh.lotobig.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.seventh.lotobig.domain.PaiementBanque} entity.
 */
public class PaiementBanqueDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private BigDecimal montant;

    @NotNull
    private BigDecimal balance;

    @Size(max = 120)
    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @Size(min = 4, max = 45)
    private String createdBy;

    @NotNull
    private Instant createdDate;

    @Size(min = 4, max = 45)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private PointOfSaleDTO pointOfSale;

    private UtilisateurDTO utilisateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public PointOfSaleDTO getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(PointOfSaleDTO pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaiementBanqueDTO)) {
            return false;
        }

        PaiementBanqueDTO paiementBanqueDTO = (PaiementBanqueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paiementBanqueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaiementBanqueDTO{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", balance=" + getBalance() +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", pointOfSale=" + getPointOfSale() +
            ", utilisateur=" + getUtilisateur() +
            "}";
    }
}
