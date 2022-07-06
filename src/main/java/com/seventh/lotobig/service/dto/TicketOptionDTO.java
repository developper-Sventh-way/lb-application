package com.seventh.lotobig.service.dto;

import com.seventh.lotobig.domain.enumeration.StatutFiche;
import com.seventh.lotobig.domain.enumeration.TypeOption;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.seventh.lotobig.domain.TicketOption} entity.
 */
public class TicketOptionDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(min = 2, max = 7)
    private String contenu;

    @NotNull
    private BigDecimal playAmount;

    @NotNull
    private TypeOption typeOption;

    @NotNull
    private StatutFiche statutOption;

    @NotNull
    private Integer multiplicateur;

    @NotNull
    @Size(min = 4, max = 45)
    private String createdBy;

    @NotNull
    private Instant createdDate;

    @Size(min = 4, max = 45)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private TicketDTO ticket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public BigDecimal getPlayAmount() {
        return playAmount;
    }

    public void setPlayAmount(BigDecimal playAmount) {
        this.playAmount = playAmount;
    }

    public TypeOption getTypeOption() {
        return typeOption;
    }

    public void setTypeOption(TypeOption typeOption) {
        this.typeOption = typeOption;
    }

    public StatutFiche getStatutOption() {
        return statutOption;
    }

    public void setStatutOption(StatutFiche statutOption) {
        this.statutOption = statutOption;
    }

    public Integer getMultiplicateur() {
        return multiplicateur;
    }

    public void setMultiplicateur(Integer multiplicateur) {
        this.multiplicateur = multiplicateur;
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

    public TicketDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketDTO ticket) {
        this.ticket = ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketOptionDTO)) {
            return false;
        }

        TicketOptionDTO ticketOptionDTO = (TicketOptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ticketOptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketOptionDTO{" +
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
            ", ticket=" + getTicket() +
            "}";
    }
}
