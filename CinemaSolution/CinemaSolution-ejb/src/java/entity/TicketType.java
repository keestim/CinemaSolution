/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author KeesTim
 */
@Entity
@Table(name = "TICKET_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TicketType.findAll", query = "SELECT t FROM TicketType t")
    , @NamedQuery(name = "TicketType.findById", query = "SELECT t FROM TicketType t WHERE t.id = :id")
    , @NamedQuery(name = "TicketType.findByTypeName", query = "SELECT t FROM TicketType t WHERE t.typeName = :typeName")
    , @NamedQuery(name = "TicketType.findByPrice", query = "SELECT t FROM TicketType t WHERE t.price = :price")})
public class TicketType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "TYPE_NAME")
    private String typeName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE")
    private double price;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketType")
    private Collection<Booking> bookingCollection;

    public TicketType() {
    }

    public TicketType(Integer id) {
        this.id = id;
    }

    public TicketType(Integer id, String typeName, double price) {
        this.id = id;
        this.typeName = typeName;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @XmlTransient
    public Collection<Booking> getBookingCollection() {
        return bookingCollection;
    }

    public void setBookingCollection(Collection<Booking> bookingCollection) {
        this.bookingCollection = bookingCollection;
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
        if (!(object instanceof TicketType)) {
            return false;
        }
        TicketType other = (TicketType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TicketType[ id=" + id + " ]";
    }
    
}
