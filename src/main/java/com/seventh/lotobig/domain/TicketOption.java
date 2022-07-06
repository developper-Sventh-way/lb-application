package com.seventh.lotobig.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seventh.lotobig.domain.enumeration.StatutFiche;
import com.seventh.lotobig.domain.enumeration.TypeOption;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TicketOption.
 */
@Entity
@Table(name = "ticket_option")
public class TicketOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(min = 2, max = 7)
    @Column(name = "contenu", length = 7)
    private String contenu;

    @NotNull
    @Column(name = "play_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal playAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_option", nullable = false)
    private TypeOption typeOption;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut_option", nullable = false)
    private StatutFiche statutOption;

    @NotNull
    @Column(name = "multiplicateur", nullable = false)
    private Integer multiplicateur;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "ticketOptions", "pointOfSale", "tirage", "userSaleAccount" }, allowSetters = true)
    private Ticket ticket;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TicketOption id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return this.contenu;
    }

    public TicketOption contenu(String contenu) {
        this.setContenu(contenu);
        return this;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public BigDecimal getPlayAmount() {
        return this.playAmount;
    }

    public TicketOption playAmount(BigDecimal playAmount) {
        this.setPlayAmount(playAmount);
        return this;
    }

    public void setPlayAmount(BigDecimal playAmount) {
        this.playAmount = playAmount;
    }

    public TypeOption getTypeOption() {
        return this.typeOption;
    }

    public TicketOption typeOption(TypeOption typeOption) {
        this.setTypeOption(typeOption);
        return this;
    }

    public void setTypeOption(TypeOption typeOption) {
        this.typeOption = typeOption;
    }

    public StatutFiche getStatutOption() {
        return this.statutOption;
    }

    public TicketOption statutOption(StatutFiche statutOption) {
        this.setStatutOption(statutOption);
        return this;
    }

    public void setStatutOption(StatutFiche statutOption) {
        this.statutOption = statutOption;
    }

    public Integer getMultiplicateur() {
        return this.multiplicateur;
    }

    public TicketOption multiplicateur(Integer multiplicateur) {
        this.setMultiplicateur(multiplicateur);
        return this;
    }

    public void setMultiplicateur(Integer multiplicateur) {
        this.multiplicateur = multiplicateur;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public TicketOption createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public TicketOption createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public TicketOption lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public TicketOption lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public TicketOption ticket(Ticket ticket) {
        this.setTicket(ticket);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketOption)) {
            return false;
        }
        return id != null && id.equals(((TicketOption) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketOption{" +
            "id=" + getId() +
            ", contenu='" + getContenu() + "'" +
            ", playAmount=" + getPlayAmount() +
            ", typeOption='" + getTypeOption() + "'" +
            ", statutOption='" + getStatutOption() + "'" +
            ", multiplicateur=" + getMultiplicateur() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
