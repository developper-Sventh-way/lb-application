package com.seventh.lotobig.service.dto;

import com.seventh.lotobig.domain.enumeration.StatutFiche;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.seventh.lotobig.domain.Ticket} entity.
 */
public class TicketDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(min = 18, max = 18)
    private String ticketNo;

    @NotNull
    private StatutFiche statutFiche;

    @Size(min = 4, max = 45)
    private String closeBy;

    private Instant closeDate;

    private Boolean isClosed;

    private Long closeById;

    @Size(min = 4, max = 45)
    private String payBy;

    private Instant payDate;

    private Boolean isPay;

    private Long payById;

    @NotNull
    @Size(min = 4, max = 45)
    private String createdBy;

    @NotNull
    private Instant createdDate;

    @Size(min = 4, max = 45)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private PointOfSaleDTO pointOfSale;

    private TirageDTO tirage;

    private UserSaleAccountDTO userSaleAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public StatutFiche getStatutFiche() {
        return statutFiche;
    }

    public void setStatutFiche(StatutFiche statutFiche) {
        this.statutFiche = statutFiche;
    }

    public String getCloseBy() {
        return closeBy;
    }

    public void setCloseBy(String closeBy) {
        this.closeBy = closeBy;
    }

    public Instant getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Instant closeDate) {
        this.closeDate = closeDate;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Long getCloseById() {
        return closeById;
    }

    public void setCloseById(Long closeById) {
        this.closeById = closeById;
    }

    public String getPayBy() {
        return payBy;
    }

    public void setPayBy(String payBy) {
        this.payBy = payBy;
    }

    public Instant getPayDate() {
        return payDate;
    }

    public void setPayDate(Instant payDate) {
        this.payDate = payDate;
    }

    public Boolean getIsPay() {
        return isPay;
    }

    public void setIsPay(Boolean isPay) {
        this.isPay = isPay;
    }

    public Long getPayById() {
        return payById;
    }

    public void setPayById(Long payById) {
        this.payById = payById;
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

    public PointOfSaleDTO getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(PointOfSaleDTO pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public TirageDTO getTirage() {
        return tirage;
    }

    public void setTirage(TirageDTO tirage) {
        this.tirage = tirage;
    }

    public UserSaleAccountDTO getUserSaleAccount() {
        return userSaleAccount;
    }

    public void setUserSaleAccount(UserSaleAccountDTO userSaleAccount) {
        this.userSaleAccount = userSaleAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketDTO)) {
            return false;
        }

        TicketDTO ticketDTO = (TicketDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ticketDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketDTO{" +
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
            ", pointOfSale=" + getPointOfSale() +
            ", tirage=" + getTirage() +
            ", userSaleAccount=" + getUserSaleAccount() +
            "}";
    }
}
