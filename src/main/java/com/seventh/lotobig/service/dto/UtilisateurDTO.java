package com.seventh.lotobig.service.dto;

import com.seventh.lotobig.domain.enumeration.Sexe;
import com.seventh.lotobig.domain.enumeration.TypeUser;
import com.seventh.lotobig.domain.enumeration.UserStatut;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.seventh.lotobig.domain.Utilisateur} entity.
 */
public class UtilisateurDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 4, max = 45)
    private String userName;

    @NotNull
    @Size(max = 100)
    private String password;

    @NotNull
    private TypeUser typeUser;

    @NotNull
    @Size(min = 4, max = 40)
    private String nom;

    @NotNull
    @Size(min = 4, max = 40)
    private String prenom;

    @NotNull
    private Sexe sexe;

    @Size(max = 20)
    private String nifOuCin;

    @NotNull
    private UserStatut statut;

    @NotNull
    @Size(min = 4, max = 45)
    private String createdBy;

    @NotNull
    private Instant createdDate;

    @Size(min = 4, max = 45)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private CreditSoldeDTO creditSolde;

    private UserPaymentConfDTO userPaymentConf;

    private PointOfSaleDTO pointOfSale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TypeUser getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getNifOuCin() {
        return nifOuCin;
    }

    public void setNifOuCin(String nifOuCin) {
        this.nifOuCin = nifOuCin;
    }

    public UserStatut getStatut() {
        return statut;
    }

    public void setStatut(UserStatut statut) {
        this.statut = statut;
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

    public CreditSoldeDTO getCreditSolde() {
        return creditSolde;
    }

    public void setCreditSolde(CreditSoldeDTO creditSolde) {
        this.creditSolde = creditSolde;
    }

    public UserPaymentConfDTO getUserPaymentConf() {
        return userPaymentConf;
    }

    public void setUserPaymentConf(UserPaymentConfDTO userPaymentConf) {
        this.userPaymentConf = userPaymentConf;
    }

    public PointOfSaleDTO getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(PointOfSaleDTO pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UtilisateurDTO)) {
            return false;
        }

        UtilisateurDTO utilisateurDTO = (UtilisateurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, utilisateurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UtilisateurDTO{" +
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
            ", creditSolde=" + getCreditSolde() +
            ", userPaymentConf=" + getUserPaymentConf() +
            ", pointOfSale=" + getPointOfSale() +
            "}";
    }
}
