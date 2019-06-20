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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author KeesTim
 */
@Entity
@Table(name = "BOOKING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b")
    , @NamedQuery(name = "Booking.findById", query = "SELECT b FROM Booking b WHERE b.id = :id")
    , @NamedQuery(name = "Booking.findByPaid", query = "SELECT b FROM Booking b WHERE b.paid = :paid")
    , @NamedQuery(name = "Booking.findByNumTickets", query = "SELECT b FROM Booking b WHERE b.numTickets = :numTickets")
    , @NamedQuery(name = "Booking.findByTotalPrice", query = "SELECT b FROM Booking b WHERE b.totalPrice = :totalPrice")})
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAID")
    private Boolean paid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUM_TICKETS")
    private int numTickets;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_PRICE")
    private double totalPrice;
    @JoinColumn(name = "SHOWING_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Showing showingId;
    @JoinColumn(name = "TICKET_TYPE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TicketType ticketType;
    @JoinColumn(name = "USER_EMAIL", referencedColumnName = "EMAIL")
    @ManyToOne
    private Users userEmail;
        
    public Booking() 
    {
    }

    public Booking(Integer id) 
    {
        this.id = id;
    }

    public Booking(Integer id, Boolean paid, int numTickets, double totalPrice) 
    {
        this.id = id;
        this.paid = paid;
        this.numTickets = numTickets;
        this.totalPrice = totalPrice;
    }

    public Booking(Integer id, Boolean paid, int numTickets, Showing showing, TicketType tickettype, double totalPrice) 
    {
        this.id = id;
        this.paid = paid;
        this.numTickets = numTickets;
        this.showingId = showing;
        this.ticketType = tickettype;
        this.totalPrice = totalPrice;
    }
    
    public Booking(Integer id, Boolean paid, int numTickets, Showing showing, TicketType tickettype, double totalPrice, Users user) 
    {
        this.id = id;
        this.paid = paid;
        this.numTickets = numTickets;
        this.showingId = showing;
        this.ticketType = tickettype;
        this.totalPrice = totalPrice;
        this.userEmail = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public void setNumTickets(int numTickets) {
        this.numTickets = numTickets;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Showing getShowingId() {
        return showingId;
    }

    public void setShowingId(Showing showingId) {
        this.showingId = showingId;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Users getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(Users userEmail) {
        this.userEmail = userEmail;
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
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Booking[ id=" + id + " ]";
    }
    
}
