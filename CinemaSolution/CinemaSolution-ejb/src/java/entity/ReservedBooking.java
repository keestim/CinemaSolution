/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author KeesTim
 */
@Entity
@Table(name = "RESERVED_BOOKING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReservedBooking.findAll", query = "SELECT r FROM ReservedBooking r")
    , @NamedQuery(name = "ReservedBooking.findById", query = "SELECT r FROM ReservedBooking r WHERE r.id = :id")
    , @NamedQuery(name = "ReservedBooking.findByReservationName", query = "SELECT r FROM ReservedBooking r WHERE r.reservationName = :reservationName")
    , @NamedQuery(name = "ReservedBooking.findByReservationPhone", query = "SELECT r FROM ReservedBooking r WHERE r.reservationPhone = :reservationPhone")})
public class ReservedBooking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "RESERVATION_NAME")
    private String reservationName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "RESERVATION_PHONE")
    private String reservationPhone;
    @JoinColumn(name = "BOOKING_ID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private Booking bookingId;

    public ReservedBooking() {
    }

    public ReservedBooking(Integer id) {
        this.id = id;
    }

    public ReservedBooking(Integer id, String reservationName, String reservationPhone) {
        this.id = id;
        this.reservationName = reservationName;
        this.reservationPhone = reservationPhone;
    }
    
    public ReservedBooking(Integer id, String reservationName, String reservationPhone, Booking booking) {
        this.id = id;
        this.reservationName = reservationName;
        this.reservationPhone = reservationPhone;
        this.bookingId = booking;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    public String getReservationPhone() {
        return reservationPhone;
    }

    public void setReservationPhone(String reservationPhone) {
        this.reservationPhone = reservationPhone;
    }

    public Booking getBookingId() {
        return bookingId;
    }

    public void setBookingId(Booking bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservedBooking)) {
            return false;
        }
        ReservedBooking other = (ReservedBooking) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ReservedBooking[ id=" + id + " ]";
    }
    
}
