package com.seventh.lotobig.service.dto;

import com.seventh.lotobig.domain.enumeration.UserSaleAccountStatut;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.seventh.lotobig.domain.UserSaleAccount} entity.
 */
public class UserSaleAccountDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private BigDecimal lastPayment;

    private UserSaleAccountStatut statut;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    @NotNull
    @Size(min = 4, max = 45)
    private String createdBy;

    @NotNull
    private Instant createdDate;

    @Size(min = 4, max = 45)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private UtilisateurDTO utilisateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getLastPayment() {
        return lastPayment;
    }

    public void setLastPayment(BigDecimal lastPayment) {
        this.lastPayment = lastPayment;
    }

    public UserSaleAccountStatut getStatut() {
        return statut;
    }

    public void setStatut(UserSaleAccountStatut statut) {
        this.statut = statut;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
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
        if (!(o instanceof UserSaleAccountDTO)) {
            return false;
        }

        UserSaleAccountDTO userSaleAccountDTO = (UserSaleAccountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userSaleAccountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserSaleAccountDTO{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", lastPayment=" + getLastPayment() +
            ", statut='" + getStatut() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", utilisateur=" + getUtilisateur() +
            "}";
    }
}
