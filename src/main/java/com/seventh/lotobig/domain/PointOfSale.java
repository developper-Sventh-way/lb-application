package com.seventh.lotobig.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seventh.lotobig.domain.enumeration.StatutBanque;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PointOfSale.
 */
@Entity
@Table(name = "point_of_sale")
public class PointOfSale implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(min = 4, max = 80)
    @Column(name = "adresse_point", length = 80)
    private String adressePoint;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutBanque statut;

    @Size(min = 6, max = 30)
    @Column(name = "phone_1", length = 30)
    private String phone1;

    @Size(min = 6, max = 30)
    @Column(name = "phone_2", length = 30)
    private String phone2;

    @NotNull
    @Column(name = "pourcentage_point", nullable = false)
    private Integer pourcentagePoint;

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

    @JsonIgnoreProperties(value = { "pointOfSale" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private PointOfSaleConf pointOfSaleConf;

    @OneToMany(mappedBy = "pointOfSale")
    @JsonIgnoreProperties(value = { "pointOfSale" }, allowSetters = true)
    private Set<LimitConfPoint> limitConfPoints = new HashSet<>();

    @OneToMany(mappedBy = "pointOfSale")
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
    private Set<Utilisateur> utilisateurs = new HashSet<>();

    @OneToMany(mappedBy = "pointOfSale")
    @JsonIgnoreProperties(value = { "pointOfSale", "utilisateur" }, allowSetters = true)
    private Set<PaiementBanque> paiementBanques = new HashSet<>();

    @OneToMany(mappedBy = "pointOfSale")
    @JsonIgnoreProperties(value = { "ticketOptions", "pointOfSale", "tirage", "userSaleAccount" }, allowSetters = true)
    private Set<Ticket> tickets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PointOfSale id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdressePoint() {
        return this.adressePoint;
    }

    public PointOfSale adressePoint(String adressePoint) {
        this.setAdressePoint(adressePoint);
        return this;
    }

    public void setAdressePoint(String adressePoint) {
        this.adressePoint = adressePoint;
    }

    public StatutBanque getStatut() {
        return this.statut;
    }

    public PointOfSale statut(StatutBanque statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutBanque statut) {
        this.statut = statut;
    }

    public String getPhone1() {
        return this.phone1;
    }

    public PointOfSale phone1(String phone1) {
        this.setPhone1(phone1);
        return this;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return this.phone2;
    }

    public PointOfSale phone2(String phone2) {
        this.setPhone2(phone2);
        return this;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public Integer getPourcentagePoint() {
        return this.pourcentagePoint;
    }

    public PointOfSale pourcentagePoint(Integer pourcentagePoint) {
        this.setPourcentagePoint(pourcentagePoint);
        return this;
    }

    public void setPourcentagePoint(Integer pourcentagePoint) {
        this.pourcentagePoint = pourcentagePoint;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public PointOfSale createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public PointOfSale createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public PointOfSale lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public PointOfSale lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public PointOfSaleConf getPointOfSaleConf() {
        return this.pointOfSaleConf;
    }

    public void setPointOfSaleConf(PointOfSaleConf pointOfSaleConf) {
        this.pointOfSaleConf = pointOfSaleConf;
    }

    public PointOfSale pointOfSaleConf(PointOfSaleConf pointOfSaleConf) {
        this.setPointOfSaleConf(pointOfSaleConf);
        return this;
    }

    public Set<LimitConfPoint> getLimitConfPoints() {
        return this.limitConfPoints;
    }

    public void setLimitConfPoints(Set<LimitConfPoint> limitConfPoints) {
        if (this.limitConfPoints != null) {
            this.limitConfPoints.forEach(i -> i.setPointOfSale(null));
        }
        if (limitConfPoints != null) {
            limitConfPoints.forEach(i -> i.setPointOfSale(this));
        }
        this.limitConfPoints = limitConfPoints;
    }

    public PointOfSale limitConfPoints(Set<LimitConfPoint> limitConfPoints) {
        this.setLimitConfPoints(limitConfPoints);
        return this;
    }

    public PointOfSale addLimitConfPoint(LimitConfPoint limitConfPoint) {
        this.limitConfPoints.add(limitConfPoint);
        limitConfPoint.setPointOfSale(this);
        return this;
    }

    public PointOfSale removeLimitConfPoint(LimitConfPoint limitConfPoint) {
        this.limitConfPoints.remove(limitConfPoint);
        limitConfPoint.setPointOfSale(null);
        return this;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return this.utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        if (this.utilisateurs != null) {
            this.utilisateurs.forEach(i -> i.setPointOfSale(null));
        }
        if (utilisateurs != null) {
            utilisateurs.forEach(i -> i.setPointOfSale(this));
        }
        this.utilisateurs = utilisateurs;
    }

    public PointOfSale utilisateurs(Set<Utilisateur> utilisateurs) {
        this.setUtilisateurs(utilisateurs);
        return this;
    }

    public PointOfSale addUtilisateur(Utilisateur utilisateur) {
        this.utilisateurs.add(utilisateur);
        utilisateur.setPointOfSale(this);
        return this;
    }

    public PointOfSale removeUtilisateur(Utilisateur utilisateur) {
        this.utilisateurs.remove(utilisateur);
        utilisateur.setPointOfSale(null);
        return this;
    }

    public Set<PaiementBanque> getPaiementBanques() {
        return this.paiementBanques;
    }

    public void setPaiementBanques(Set<PaiementBanque> paiementBanques) {
        if (this.paiementBanques != null) {
            this.paiementBanques.forEach(i -> i.setPointOfSale(null));
        }
        if (paiementBanques != null) {
            paiementBanques.forEach(i -> i.setPointOfSale(this));
        }
        this.paiementBanques = paiementBanques;
    }

    public PointOfSale paiementBanques(Set<PaiementBanque> paiementBanques) {
        this.setPaiementBanques(paiementBanques);
        return this;
    }

    public PointOfSale addPaiementBanque(PaiementBanque paiementBanque) {
        this.paiementBanques.add(paiementBanque);
        paiementBanque.setPointOfSale(this);
        return this;
    }

    public PointOfSale removePaiementBanque(PaiementBanque paiementBanque) {
        this.paiementBanques.remove(paiementBanque);
        paiementBanque.setPointOfSale(null);
        return this;
    }

    public Set<Ticket> getTickets() {
        return this.tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        if (this.tickets != null) {
            this.tickets.forEach(i -> i.setPointOfSale(null));
        }
        if (tickets != null) {
            tickets.forEach(i -> i.setPointOfSale(this));
        }
        this.tickets = tickets;
    }

    public PointOfSale tickets(Set<Ticket> tickets) {
        this.setTickets(tickets);
        return this;
    }

    public PointOfSale addTicket(Ticket ticket) {
        this.tickets.add(ticket);
        ticket.setPointOfSale(this);
        return this;
    }

    public PointOfSale removeTicket(Ticket ticket) {
        this.tickets.remove(ticket);
        ticket.setPointOfSale(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointOfSale)) {
            return false;
        }
        return id != null && id.equals(((PointOfSale) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointOfSale{" +
            "id=" + getId() +
            ", adressePoint='" + getAdressePoint() + "'" +
            ", statut='" + getStatut() + "'" +
            ", phone1='" + getPhone1() + "'" +
            ", phone2='" + getPhone2() + "'" +
            ", pourcentagePoint=" + getPourcentagePoint() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
