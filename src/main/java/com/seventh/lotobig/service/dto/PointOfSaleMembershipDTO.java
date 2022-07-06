package com.seventh.lotobig.service.dto;

import com.seventh.lotobig.domain.enumeration.TypeBanque;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.seventh.lotobig.domain.PointOfSaleMembership} entity.
 */
public class PointOfSaleMembershipDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private TypeBanque typePoint;

    @NotNull
    @Size(min = 4, max = 45)
    private String createdBy;

    @NotNull
    private Instant createdDate;

    @Size(min = 4, max = 45)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private MembershipConfDTO membershipConf;

    private UtilisateurDTO utilisateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeBanque getTypePoint() {
        return typePoint;
    }

    public void setTypePoint(TypeBanque typePoint) {
        this.typePoint = typePoint;
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

    public MembershipConfDTO getMembershipConf() {
        return membershipConf;
    }

    public void setMembershipConf(MembershipConfDTO membershipConf) {
        this.membershipConf = membershipConf;
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
        if (!(o instanceof PointOfSaleMembershipDTO)) {
            return false;
        }

        PointOfSaleMembershipDTO pointOfSaleMembershipDTO = (PointOfSaleMembershipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pointOfSaleMembershipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointOfSaleMembershipDTO{" +
            "id=" + getId() +
            ", typePoint='" + getTypePoint() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", membershipConf=" + getMembershipConf() +
            ", utilisateur=" + getUtilisateur() +
            "}";
    }
}
