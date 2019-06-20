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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author KeesTim
 */
@Entity
@Table(name = "CINEMA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cinema.findAll", query = "SELECT c FROM Cinema c")
    , @NamedQuery(name = "Cinema.findByCinemaId", query = "SELECT c FROM Cinema c WHERE c.cinemaId = :cinemaId")
    , @NamedQuery(name = "Cinema.findByNumRows", query = "SELECT c FROM Cinema c WHERE c.numRows = :numRows")
    , @NamedQuery(name = "Cinema.findByIsleWidth", query = "SELECT c FROM Cinema c WHERE c.isleWidth = :isleWidth")})
public class Cinema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CINEMA_ID")
    private Integer cinemaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUM_ROWS")
    private int numRows;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISLE_WIDTH")
    private int isleWidth;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cinemaId")
    private Collection<Showing> showingCollection;

    public Cinema() {
    }

    public Cinema(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Cinema(Integer cinemaId, int numRows, int isleWidth) {
        this.cinemaId = cinemaId;
        this.numRows = numRows;
        this.isleWidth = isleWidth;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getIsleWidth() {
        return isleWidth;
    }

    public void setIsleWidth(int isleWidth) {
        this.isleWidth = isleWidth;
    }

    @XmlTransient
    public Collection<Showing> getShowingCollection() {
        return showingCollection;
    }

    public void setShowingCollection(Collection<Showing> showingCollection) {
        this.showingCollection = showingCollection;
    }
    
    public int getNumSeats()
    {
        return this.isleWidth * this.numRows;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cinemaId != null ? cinemaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cinema)) {
            return false;
        }
        Cinema other = (Cinema) object;
        if ((this.cinemaId == null && other.cinemaId != null) || (this.cinemaId != null && !this.cinemaId.equals(other.cinemaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Cinema[ cinemaId=" + cinemaId + " ]";
    }
    
}
