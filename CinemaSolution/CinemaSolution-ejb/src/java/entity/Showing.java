/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author KeesTim
 */
@Entity
@Table(name = "SHOWING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Showing.findAll", query = "SELECT s FROM Showing s")
    , @NamedQuery(name = "Showing.findById", query = "SELECT s FROM Showing s WHERE s.id = :id")
    , @NamedQuery(name = "Showing.findByStartTime", query = "SELECT s FROM Showing s WHERE s.startTime = :startTime")
    , @NamedQuery(name = "Showing.findByStartDate", query = "SELECT s FROM Showing s WHERE s.startDate = :startDate")})
public class Showing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @JoinColumn(name = "CINEMA_ID", referencedColumnName = "CINEMA_ID")
    @ManyToOne(optional = false)
    private Cinema cinemaId;
    @JoinColumn(name = "MOVIE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Movie movieId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "showingId")
    private Collection<Booking> bookingCollection;

    public Showing() {
    }

    public Showing(Integer id) {
        this.id = id;
    }

    public Showing(Integer id, Date startTime, Date startDate) {
        this.id = id;
        this.startTime = startTime;
        this.startDate = startDate;
    }
    
    public Showing(Integer id, Date startTime, Date startDate, Cinema cinema, Movie movie) {
        this.id = id;
        this.startTime = startTime;
        this.startDate = startDate;
        this.cinemaId = cinema;
        this.movieId = movie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Cinema getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Cinema cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Movie getMovieId() {
        return movieId;
    }

    public void setMovieId(Movie movieId) {
        this.movieId = movieId;
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
        if (!(object instanceof Showing)) {
            return false;
        }
        Showing other = (Showing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Showing[ id=" + id + " ]";
    }
    
}
