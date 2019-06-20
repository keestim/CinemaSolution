/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author KeesTim
 * DTO object that represents the TicketType entity create from the solution's database
 */

//same fields as entity that it represents, with any entity type field being changed to DTO type
public class ShowingDTO {
    private Integer id;
    private Date startTime;
    private Date startDate;
    private CinemaDTO cinema;
    private MovieDTO movieId;

    public ShowingDTO(Integer id, Date startTime, Date startDate, CinemaDTO cinema, MovieDTO movieId) {
        this.id = id;
        this.startTime = startTime;
        this.startDate = startDate;
        this.cinema = cinema;
        this.movieId = movieId;
    }

    public ShowingDTO(Integer id, Date startTime, Date startDate, MovieDTO movieId) {
        this.cinema = null;
        this.id = id;
        this.startTime = startTime;
        this.startDate = startDate;
        this.movieId = movieId;
    }

    public Integer getId() {
        return id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public CinemaDTO getCinema() {
        return cinema;
    }

    public MovieDTO getMovieId() {
        return movieId;
    }
    
    //getters for all of the class's private fields
    public String dateString()
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(startDate).toString();
    }
    
    public String timeString()
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(startTime).toString();
    }
    
    public boolean equals(Object obj)
    {
        if (this == obj)
        return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShowingDTO other = (ShowingDTO) obj;
        if (this.id != other.id)
            return false;
        return true;
    }
    
}
