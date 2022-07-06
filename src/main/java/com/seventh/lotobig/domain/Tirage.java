package com.seventh.lotobig.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seventh.lotobig.domain.enumeration.TirageName;
import com.seventh.lotobig.domain.enumeration.TirageType;
import com.seventh.lotobig.domain.enumeration.UserStatut;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Tirage.
 */
@Entity
@Table(name = "tirage")
public class Tirage implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tirage_name", nullable = false)
    private TirageName tirageName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TirageType type;

    @Size(min = 2, max = 2)
    @Column(name = "premier_lot", length = 2)
    private String premierLot;

    @Size(min = 2, max = 2)
    @Column(name = "deuxieme_lot", length = 2)
    private String deuxiemeLot;

    @Size(min = 2, max = 2)
    @Column(name = "troisieme_lot", length = 2)
    private String troisiemeLot;

    @Size(min = 2, max = 2)
    @Column(name = "loto_3_chif", length = 2)
    private String loto3Chif;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private UserStatut statut;

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

    @OneToMany(mappedBy = "tirage")
    @JsonIgnoreProperties(value = { "ticketOptions", "pointOfSale", "tirage", "userSaleAccount" }, allowSetters = true)
    private Set<Ticket> tickets = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "limitConfBorlettes", "tirages" }, allowSetters = true)
    private BorletteConf borletteConf;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tirage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TirageName getTirageName() {
        return this.tirageName;
    }

    public Tirage tirageName(TirageName tirageName) {
        this.setTirageName(tirageName);
        return this;
    }

    public void setTirageName(TirageName tirageName) {
        this.tirageName = tirageName;
    }

    public TirageType getType() {
        return this.type;
    }

    public Tirage type(TirageType type) {
        this.setType(type);
        return this;
    }

    public void setType(TirageType type) {
        this.type = type;
    }

    public String getPremierLot() {
        return this.premierLot;
    }

    public Tirage premierLot(String premierLot) {
        this.setPremierLot(premierLot);
        return this;
    }

    public void setPremierLot(String premierLot) {
        this.premierLot = premierLot;
    }

    public String getDeuxiemeLot() {
        return this.deuxiemeLot;
    }

    public Tirage deuxiemeLot(String deuxiemeLot) {
        this.setDeuxiemeLot(deuxiemeLot);
        return this;
    }

    public void setDeuxiemeLot(String deuxiemeLot) {
        this.deuxiemeLot = deuxiemeLot;
    }

    public String getTroisiemeLot() {
        return this.troisiemeLot;
    }

    public Tirage troisiemeLot(String troisiemeLot) {
        this.setTroisiemeLot(troisiemeLot);
        return this;
    }

    public void setTroisiemeLot(String troisiemeLot) {
        this.troisiemeLot = troisiemeLot;
    }

    public String getLoto3Chif() {
        return this.loto3Chif;
    }

    public Tirage loto3Chif(String loto3Chif) {
        this.setLoto3Chif(loto3Chif);
        return this;
    }

    public void setLoto3Chif(String loto3Chif) {
        this.loto3Chif = loto3Chif;
    }

    public UserStatut getStatut() {
        return this.statut;
    }

    public Tirage statut(UserStatut statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(UserStatut statut) {
        this.statut = statut;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Tirage createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Tirage createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Tirage lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Tirage lastModifiedDate(Instant lastModifiedDate) {
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
            this.tickets.forEach(i -> i.setTirage(null));
        }
        if (tickets != null) {
            tickets.forEach(i -> i.setTirage(this));
        }
        this.tickets = tickets;
    }

    public Tirage tickets(Set<Ticket> tickets) {
        this.setTickets(tickets);
        return this;
    }

    public Tirage addTicket(Ticket ticket) {
        this.tickets.add(ticket);
        ticket.setTirage(this);
        return this;
    }

    public Tirage removeTicket(Ticket ticket) {
        this.tickets.remove(ticket);
        ticket.setTirage(null);
        return this;
    }

    public BorletteConf getBorletteConf() {
        return this.borletteConf;
    }

    public void setBorletteConf(BorletteConf borletteConf) {
        this.borletteConf = borletteConf;
    }

    public Tirage borletteConf(BorletteConf borletteConf) {
        this.setBorletteConf(borletteConf);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tirage)) {
            return false;
        }
        return id != null && id.equals(((Tirage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tirage{" +
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
            "}";
    }
}
