/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.CinemaDTO;
import entity.MovieDTO;
import entity.ShowingDTO;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import session.SolutionManagedBeanRemote;

/**
 *
 * @author KeesTim
 */
@Named(value = "showingManagedBean")
@ConversationScoped
public class showingManagedBean implements Serializable
{
    @EJB
    private SolutionManagedBeanRemote solutionManagedBean;
    
    @Inject
    private Conversation conversation;
    
    //very similar feilds to the ShowingDTO class
    private Integer id;
    private Date startTime;
    private Date startDate;
    private CinemaDTO cinema;
    private MovieDTO movie;
    
    public showingManagedBean()
    {
        id = null;
        startTime = null;
        startDate = null;
        cinema = null;
        movie = null;
    }

    //setters and getter for all of the class's private fields
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

    public CinemaDTO getCinema() {
        return cinema;
    }

    public void setCinema(CinemaDTO cinema) {
        this.cinema = cinema;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }

    public List<MovieDTO> getAllMovies()
    {
        return solutionManagedBean.getAllMovies();
    }
    
    public List<CinemaDTO> getAllCinemas()
    {
        return solutionManagedBean.getAllCinemas();
    }
    
    public void startConversation() {
        conversation.begin();
    }

    public void endConversation() {
        conversation.end();
    }
    
    //used for adding showings to the data base 
    public String addShowing()
    {
        ShowingDTO showingDTO = new ShowingDTO(this.id, this.startTime, this.startDate, cinema, movie);

        //recreate entity classes!
        Boolean result = solutionManagedBean.addShowing(showingDTO);
        if (result)
        {
            return "success";
        }
        else 
        {
            return "failure";
        }
    }
    
    //copies the fields from an input dto, to an exisiting object, created from this class
    public void importShowingDTO(ShowingDTO dto)
    {
        this.id = dto.getId();
        this.startDate = dto.getStartDate();
        this.startTime = dto.getStartTime();
        this.movie = dto.getMovieId();
        this.cinema = dto.getCinema();
    }
    
    //used for updating showing entries currently in the database
    public String updateShowing()
    {
        ShowingDTO showingDTO = new ShowingDTO(this.id, this.startTime, this.startDate, cinema, movie);

        //recreate entity classes!
        Boolean result = solutionManagedBean.updateShowing(showingDTO);
        if (result)
        {
            return "success";
        }
        else 
        {
            return "failure";
        }
    }
    
    //subtracts the number seat remain in the session
    public int getShowingSeatsAvalible()
    {
        //gets the total number of seats in the cinema
            Integer num_seats = this.cinema.getIsleWidth() * this.cinema.getNumRows();
            //and subtracts the number of tickets booked
            Integer num_bookings = solutionManagedBean.showingNumBookings(this.id.toString());
            return (num_seats - num_bookings);
    }
    
    
    //deletes showing that has the id that match the id field for this object
    public String deleteShowing()
    {
        Boolean result = solutionManagedBean.deleteSession(this.id.toString());
        if (result)
        {
            return "success";
        }
        else 
        {
            return "failure";
        }
    }
    
    //formats startDate field into dd/MM/yyyy form
    public String dateString()
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(startDate).toString();
    }
    
    //formats startTime field into HH:mm form 
    public String timeString()
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(startTime).toString();
    }
}
