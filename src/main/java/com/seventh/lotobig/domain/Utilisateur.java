package com.seventh.lotobig.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seventh.lotobig.domain.enumeration.Sexe;
import com.seventh.lotobig.domain.enumeration.TypeUser;
import com.seventh.lotobig.domain.enumeration.UserStatut;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Utilisateur.
 */
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 4, max = 45)
    @Column(name = "user_name", length = 45, nullable = false)
    private String userName;

    @NotNull
    @Size(max = 100)
    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_user", nullable = false)
    private TypeUser typeUser;

    @NotNull
    @Size(min = 4, max = 40)
    @Column(name = "nom", length = 40, nullable = false)
    private String nom;

    @NotNull
    @Size(min = 4, max = 40)
    @Column(name = "prenom", length = 40, nullable = false)
    private String prenom;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexe", nullable = false)
    private Sexe sexe;

    @Size(max = 20)
    @Column(name = "nif_ou_cin", length = 20)
    private String nifOuCin;

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

    @JsonIgnoreProperties(value = { "utilisateur" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private CreditSolde creditSolde;

    @JsonIgnoreProperties(value = { "utilisateur" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private UserPaymentConf userPaymentConf;

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnoreProperties(value = { "membershipConf", "utilisateur" }, allowSetters = true)
    private Set<PointOfSaleMembership> pointOfSaleMemberships = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnoreProperties(value = { "pointOfSale", "utilisateur" }, allowSetters = true)
    private Set<PaiementBanque> paiementBanques = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnoreProperties(value = { "tickets", "utilisateur" }, allowSetters = true)
    private Set<UserSaleAccount> userSaleAccounts = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnoreProperties(value = { "utilisateur" }, allowSetters = true)
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnoreProperties(value = { "utilisateur" }, allowSetters = true)
    private Set<UserPayment> userPayments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "pointOfSaleConf", "limitConfPoints", "utilisateurs", "paiementBanques", "tickets" },
        allowSetters = true
    )
    private PointOfSale pointOfSale;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Utilisateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public Utilisateur userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public Utilisateur password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TypeUser getTypeUser() {
        return this.typeUser;
    }

    public Utilisateur typeUser(TypeUser typeUser) {
        this.setTypeUser(typeUser);
        return this;
    }

    public void setTypeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
    }

    public String getNom() {
        return this.nom;
    }

    public Utilisateur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Utilisateur prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public Utilisateur sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getNifOuCin() {
        return this.nifOuCin;
    }

    public Utilisateur nifOuCin(String nifOuCin) {
        this.setNifOuCin(nifOuCin);
        return this;
    }

    public void setNifOuCin(String nifOuCin) {
        this.nifOuCin = nifOuCin;
    }

    public UserStatut getStatut() {
        return this.statut;
    }

    public Utilisateur statut(UserStatut statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(UserStatut statut) {
        this.statut = statut;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Utilisateur createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Utilisateur createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Utilisateur lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Utilisateur lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public CreditSolde getCreditSolde() {
        return this.creditSolde;
    }

    public void setCreditSolde(CreditSolde creditSolde) {
        this.creditSolde = creditSolde;
    }

    public Utilisateur creditSolde(CreditSolde creditSolde) {
        this.setCreditSolde(creditSolde);
        return this;
    }

    public UserPaymentConf getUserPaymentConf() {
        return this.userPaymentConf;
    }

    public void setUserPaymentConf(UserPaymentConf userPaymentConf) {
        this.userPaymentConf = userPaymentConf;
    }

    public Utilisateur userPaymentConf(UserPaymentConf userPaymentConf) {
        this.setUserPaymentConf(userPaymentConf);
        return this;
    }

    public Set<PointOfSaleMembership> getPointOfSaleMemberships() {
        return this.pointOfSaleMemberships;
    }

    public void setPointOfSaleMemberships(Set<PointOfSaleMembership> pointOfSaleMemberships) {
        if (this.pointOfSaleMemberships != null) {
            this.pointOfSaleMemberships.forEach(i -> i.setUtilisateur(null));
        }
        if (pointOfSaleMemberships != null) {
            pointOfSaleMemberships.forEach(i -> i.setUtilisateur(this));
        }
        this.pointOfSaleMemberships = pointOfSaleMemberships;
    }

    public Utilisateur pointOfSaleMemberships(Set<PointOfSaleMembership> pointOfSaleMemberships) {
        this.setPointOfSaleMemberships(pointOfSaleMemberships);
        return this;
    }

    public Utilisateur addPointOfSaleMembership(PointOfSaleMembership pointOfSaleMembership) {
        this.pointOfSaleMemberships.add(pointOfSaleMembership);
        pointOfSaleMembership.setUtilisateur(this);
        return this;
    }

    public Utilisateur removePointOfSaleMembership(PointOfSaleMembership pointOfSaleMembership) {
        this.pointOfSaleMemberships.remove(pointOfSaleMembership);
        pointOfSaleMembership.setUtilisateur(null);
        return this;
    }

    public Set<PaiementBanque> getPaiementBanques() {
        return this.paiementBanques;
    }

    public void setPaiementBanques(Set<PaiementBanque> paiementBanques) {
        if (this.paiementBanques != null) {
            this.paiementBanques.forEach(i -> i.setUtilisateur(null));
        }
        if (paiementBanques != null) {
            paiementBanques.forEach(i -> i.setUtilisateur(this));
        }
        this.paiementBanques = paiementBanques;
    }

    public Utilisateur paiementBanques(Set<PaiementBanque> paiementBanques) {
        this.setPaiementBanques(paiementBanques);
        return this;
    }

    public Utilisateur addPaiementBanque(PaiementBanque paiementBanque) {
        this.paiementBanques.add(paiementBanque);
        paiementBanque.setUtilisateur(this);
        return this;
    }

    public Utilisateur removePaiementBanque(PaiementBanque paiementBanque) {
        this.paiementBanques.remove(paiementBanque);
        paiementBanque.setUtilisateur(null);
        return this;
    }

    public Set<UserSaleAccount> getUserSaleAccounts() {
        return this.userSaleAccounts;
    }

    public void setUserSaleAccounts(Set<UserSaleAccount> userSaleAccounts) {
        if (this.userSaleAccounts != null) {
            this.userSaleAccounts.forEach(i -> i.setUtilisateur(null));
        }
        if (userSaleAccounts != null) {
            userSaleAccounts.forEach(i -> i.setUtilisateur(this));
        }
        this.userSaleAccounts = userSaleAccounts;
    }

    public Utilisateur userSaleAccounts(Set<UserSaleAccount> userSaleAccounts) {
        this.setUserSaleAccounts(userSaleAccounts);
        return this;
    }

    public Utilisateur addUserSaleAccount(UserSaleAccount userSaleAccount) {
        this.userSaleAccounts.add(userSaleAccount);
        userSaleAccount.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeUserSaleAccount(UserSaleAccount userSaleAccount) {
        this.userSaleAccounts.remove(userSaleAccount);
        userSaleAccount.setUtilisateur(null);
        return this;
    }

    public Set<UserRole> getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        if (this.userRoles != null) {
            this.userRoles.forEach(i -> i.setUtilisateur(null));
        }
        if (userRoles != null) {
            userRoles.forEach(i -> i.setUtilisateur(this));
        }
        this.userRoles = userRoles;
    }

    public Utilisateur userRoles(Set<UserRole> userRoles) {
        this.setUserRoles(userRoles);
        return this;
    }

    public Utilisateur addUserRole(UserRole userRole) {
        this.userRoles.add(userRole);
        userRole.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeUserRole(UserRole userRole) {
        this.userRoles.remove(userRole);
        userRole.setUtilisateur(null);
        return this;
    }

    public Set<UserPayment> getUserPayments() {
        return this.userPayments;
    }

    public void setUserPayments(Set<UserPayment> userPayments) {
        if (this.userPayments != null) {
            this.userPayments.forEach(i -> i.setUtilisateur(null));
        }
        if (userPayments != null) {
            userPayments.forEach(i -> i.setUtilisateur(this));
        }
        this.userPayments = userPayments;
    }

    public Utilisateur userPayments(Set<UserPayment> userPayments) {
        this.setUserPayments(userPayments);
        return this;
    }

    public Utilisateur addUserPayment(UserPayment userPayment) {
        this.userPayments.add(userPayment);
        userPayment.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeUserPayment(UserPayment userPayment) {
        this.userPayments.remove(userPayment);
        userPayment.setUtilisateur(null);
        return this;
    }

    public PointOfSale getPointOfSale() {
        return this.pointOfSale;
    }

    public void setPointOfSale(PointOfSale pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public Utilisateur pointOfSale(PointOfSale pointOfSale) {
        this.setPointOfSale(pointOfSale);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Utilisateur)) {
            return false;
        }
        return id != null && id.equals(((Utilisateur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", typeUser='" + getTypeUser() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", nifOuCin='" + getNifOuCin() + "'" +
            ", statut='" + getStatut() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
