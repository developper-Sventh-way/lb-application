package com.seventh.lotobig.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seventh.lotobig.domain.enumeration.StatutFiche;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(min = 18, max = 18)
    @Column(name = "ticket_no", length = 18)
    private String ticketNo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut_fiche", nullable = false)
    private StatutFiche statutFiche;

    @Size(min = 4, max = 45)
    @Column(name = "close_by", length = 45)
    private String closeBy;

    @Column(name = "close_date")
    private Instant closeDate;

    @Column(name = "is_closed")
    private Boolean isClosed;

    @Column(name = "close_by_id")
    private Long closeById;

    @Size(min = 4, max = 45)
    @Column(name = "pay_by", length = 45)
    private String payBy;

    @Column(name = "pay_date")
    private Instant payDate;

    @Column(name = "is_pay")
    private Boolean isPay;

    @Column(name = "pay_by_id")
    private Long payById;

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

    @OneToMany(mappedBy = "ticket")
    @JsonIgnoreProperties(value = { "ticket" }, allowSetters = true)
    private Set<TicketOption> ticketOptions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "pointOfSaleConf", "limitConfPoints", "utilisateurs", "paiementBanques", "tickets" },
        allowSetters = true
    )
    private PointOfSale pointOfSale;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tickets", "borletteConf" }, allowSetters = true)
    private Tirage tirage;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tickets", "utilisateur" }, allowSetters = true)
    private UserSaleAccount userSaleAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ticket id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketNo() {
        return this.ticketNo;
    }

    public Ticket ticketNo(String ticketNo) {
        this.setTicketNo(ticketNo);
        return this;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public StatutFiche getStatutFiche() {
        return this.statutFiche;
    }

    public Ticket statutFiche(StatutFiche statutFiche) {
        this.setStatutFiche(statutFiche);
        return this;
    }

    public void setStatutFiche(StatutFiche statutFiche) {
        this.statutFiche = statutFiche;
    }

    public String getCloseBy() {
        return this.closeBy;
    }

    public Ticket closeBy(String closeBy) {
        this.setCloseBy(closeBy);
        return this;
    }

    public void setCloseBy(String closeBy) {
        this.closeBy = closeBy;
    }

    public Instant getCloseDate() {
        return this.closeDate;
    }

    public Ticket closeDate(Instant closeDate) {
        this.setCloseDate(closeDate);
        return this;
    }

    public void setCloseDate(Instant closeDate) {
        this.closeDate = closeDate;
    }

    public Boolean getIsClosed() {
        return this.isClosed;
    }

    public Ticket isClosed(Boolean isClosed) {
        this.setIsClosed(isClosed);
        return this;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Long getCloseById() {
        return this.closeById;
    }

    public Ticket closeById(Long closeById) {
        this.setCloseById(closeById);
        return this;
    }

    public void setCloseById(Long closeById) {
        this.closeById = closeById;
    }

    public String getPayBy() {
        return this.payBy;
    }

    public Ticket payBy(String payBy) {
        this.setPayBy(payBy);
        return this;
    }

    public void setPayBy(String payBy) {
        this.payBy = payBy;
    }

    public Instant getPayDate() {
        return this.payDate;
    }

    public Ticket payDate(Instant payDate) {
        this.setPayDate(payDate);
        return this;
    }

    public void setPayDate(Instant payDate) {
        this.payDate = payDate;
    }

    public Boolean getIsPay() {
        return this.isPay;
    }

    public Ticket isPay(Boolean isPay) {
        this.setIsPay(isPay);
        return this;
    }

    public void setIsPay(Boolean isPay) {
        this.isPay = isPay;
    }

    public Long getPayById() {
        return this.payById;
    }

    public Ticket payById(Long payById) {
        this.setPayById(payById);
        return this;
    }

    public void setPayById(Long payById) {
        this.payById = payById;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Ticket createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Ticket createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Ticket lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Ticket lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<TicketOption> getTicketOptions() {
        return this.ticketOptions;
    }

    public void setTicketOptions(Set<TicketOption> ticketOptions) {
        if (this.ticketOptions != null) {
            this.ticketOptions.forEach(i -> i.setTicket(null));
        }
        if (ticketOptions != null) {
            ticketOptions.forEach(i -> i.setTicket(this));
        }
        this.ticketOptions = ticketOptions;
    }

    public Ticket ticketOptions(Set<TicketOption> ticketOptions) {
        this.setTicketOptions(ticketOptions);
        return this;
    }

    public Ticket addTicketOption(TicketOption ticketOption) {
        this.ticketOptions.add(ticketOption);
        ticketOption.setTicket(this);
        return this;
    }

    public Ticket removeTicketOption(TicketOption ticketOption) {
        this.ticketOptions.remove(ticketOption);
        ticketOption.setTicket(null);
        return this;
    }

    public PointOfSale getPointOfSale() {
        return this.pointOfSale;
    }

    public void setPointOfSale(PointOfSale pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public Ticket pointOfSale(PointOfSale pointOfSale) {
        this.setPointOfSale(pointOfSale);
        return this;
    }

    public Tirage getTirage() {
        return this.tirage;
    }

    public void setTirage(Tirage tirage) {
        this.tirage = tirage;
    }

    public Ticket tirage(Tirage tirage) {
        this.setTirage(tirage);
        return this;
    }

    public UserSaleAccount getUserSaleAccount() {
        return this.userSaleAccount;
    }

    public void setUserSaleAccount(UserSaleAccount userSaleAccount) {
        this.userSaleAccount = userSaleAccount;
    }

    public Ticket userSaleAccount(UserSaleAccount userSaleAccount) {
        this.setUserSaleAccount(userSaleAccount);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        return id != null && id.equals(((Ticket) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + getId() +
            ", ticketNo='" + getTicketNo() + "'" +
            ", statutFiche='" + getStatutFiche() + "'" +
            ", closeBy='" + getCloseBy() + "'" +
            ", closeDate='" + getCloseDate() + "'" +
            ", isClosed='" + getIsClosed() + "'" +
            ", closeById=" + getCloseById() +
            ", payBy='" + getPayBy() + "'" +
            ", payDate='" + getPayDate() + "'" +
            ", isPay='" + getIsPay() + "'" +
            ", payById=" + getPayById() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
