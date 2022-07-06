package com.seventh.lotobig.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seventh.lotobig.domain.enumeration.UserSaleAccountStatut;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A UserSaleAccount.
 */
@Entity
@Table(name = "user_sale_account")
public class UserSaleAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "balance", precision = 21, scale = 2, nullable = false)
    private BigDecimal balance;

    @NotNull
    @Column(name = "last_payment", precision = 21, scale = 2, nullable = false)
    private BigDecimal lastPayment;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private UserSaleAccountStatut statut;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

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

    @OneToMany(mappedBy = "userSaleAccount")
    @JsonIgnoreProperties(value = { "ticketOptions", "pointOfSale", "tirage", "userSaleAccount" }, allowSetters = true)
    private Set<Ticket> tickets = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "creditSolde",
            "userPaymentConf",
            "pointOfSaleMemberships",
            "paiementBanques",
            "userSaleAccounts",
            "userRoles",
            "userPayments",
            "pointOfSale",
        },
        allowSetters = true
    )
    private Utilisateur utilisateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserSaleAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public UserSaleAccount balance(BigDecimal balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getLastPayment() {
        return this.lastPayment;
    }

    public UserSaleAccount lastPayment(BigDecimal lastPayment) {
        this.setLastPayment(lastPayment);
        return this;
    }

    public void setLastPayment(BigDecimal lastPayment) {
        this.lastPayment = lastPayment;
    }

    public UserSaleAccountStatut getStatut() {
        return this.statut;
    }

    public UserSaleAccount statut(UserSaleAccountStatut statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(UserSaleAccountStatut statut) {
        this.statut = statut;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public UserSaleAccount startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public UserSaleAccount endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public UserSaleAccount createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public UserSaleAccount createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public UserSaleAccount lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public UserSaleAccount lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<Ticket> getTickets() {
        return this.tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        if (this.tickets != null) {
            this.tickets.forEach(i -> i.setUserSaleAccount(null));
        }
        if (tickets != null) {
            tickets.forEach(i -> i.setUserSaleAccount(this));
        }
        this.tickets = tickets;
    }

    public UserSaleAccount tickets(Set<Ticket> tickets) {
        this.setTickets(tickets);
        return this;
    }

    public UserSaleAccount addTicket(Ticket ticket) {
        this.tickets.add(ticket);
        ticket.setUserSaleAccount(this);
        return this;
    }

    public UserSaleAccount removeTicket(Ticket ticket) {
        this.tickets.remove(ticket);
        ticket.setUserSaleAccount(null);
        return this;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public UserSaleAccount utilisateur(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSaleAccount)) {
            return false;
        }
        return id != null && id.equals(((UserSaleAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserSaleAccount{" +
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
            "}";
    }
}
