/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.BookingDTO;
import entity.CinemaDTO;
import entity.MovieDTO;
import entity.ReservedBookingDTO;
import entity.ShowingDTO;
import entity.TicketTypeDTO;
import entity.UsersDTO;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import session.SolutionManagedBeanRemote;

/**
 *
 * @author timke
 */

//class is used for common request that the solution may receive, 
//this class is more generalized that the other classes, due to these request only request data
//not removing, updating or delete information
@Named(value = "displayDataManagedBean")
@ConversationScoped
public class displayDataManagedBean implements Serializable
{
    private String ajax_selection = ""; 
    private String ajax_query = "";

    public String getAjax_query() {
        return ajax_query;
    }

    public void setAjax_query(String ajax_query) {
        this.ajax_query = ajax_query;
    }
    
    @EJB
    private SolutionManagedBeanRemote solutionManagedBean;
    @Inject
    private Conversation conversation;
    
    public displayDataManagedBean()
    {
    
    }

    public String getAjax_selection() {
        return ajax_selection;
    }

    public void setAjax_selection(String ajax_selection) {
        this.ajax_selection = ajax_selection;
    }

    public List<ShowingDTO> getCurrentShowings()
    {
        return solutionManagedBean.getCurrentShowings();
    }
    
    public ShowingDTO getShowingByID(String id)
    {
        return solutionManagedBean.getShowingByID(id);
    }
    
    public int getShowingSeatsAvalible(String showing_id)
    {
        ShowingDTO showing_data = solutionManagedBean.getShowingByID(showing_id);
        if (showing_data != null)
        {
            Integer num_seats = showing_data.getCinema().getIsleWidth() * showing_data.getCinema().getNumRows();
            Integer num_bookings = solutionManagedBean.showingNumBookings(showing_id);
            return (num_seats - num_bookings);
        }
        else 
        {
            return 0;
        }
    }
    
    public boolean seatsAvaliable(String showing_id, Integer num_tickets)
    {
        return this.getShowingSeatsAvalible(showing_id) - num_tickets >= 0;
    }
    
    public List<TicketTypeDTO> getTicketTypes()
    {
        return solutionManagedBean.getAllTicketTypes();
    }
   
    public List<CinemaDTO> getCinemas()
    {
        return solutionManagedBean.getAllCinemas();
    }
    
    public List<MovieDTO> getMovies()
    {
        if (this.ajax_selection.trim().length() == 0)
        {
            return solutionManagedBean.getAllMovies();
        }
        else 
        {
            return solutionManagedBean.getMovieByName(ajax_selection.trim());
        }
    }
    
    public List<MovieDTO> getMoviesSortAZ()
    {        if (this.ajax_selection.trim().length() == 0)
        {
            return solutionManagedBean.getAllMoviesSortAZ();
        }
        else 
        {
            return solutionManagedBean.getMovieByName(ajax_selection.trim());
        }
    }
    
    public List<MovieDTO> getMoviesSortNumShowings()
    {        if (this.ajax_selection.trim().length() == 0)
        {
            return solutionManagedBean.getAllMoviesSortNumShowings();
        }
        else 
        {
            return solutionManagedBean.getMovieByName(ajax_selection.trim());
        }
    }    
    
    public List<ShowingDTO> getShowing()
    {
        return solutionManagedBean.getShowingsForGivenTime(ajax_selection);
    }
    
    public List<UsersDTO> getUsers()
    {
        if (this.ajax_query.trim().length() == 0)
        {
            switch (this.ajax_selection)
            {
                case "all":
                    return solutionManagedBean.getAllUsers();

                default:
                    return solutionManagedBean.getUsersByGroupType(ajax_selection);
            }
        }
        
        switch (this.ajax_selection)
        {
            case "all":
                return solutionManagedBean.getAllUsers(this.ajax_query.trim());

            default:
                return solutionManagedBean.getUsersByGroupType(ajax_selection, this.ajax_query.trim());
        }
    }
    
    public List<ReservedBookingDTO> getReservedBookings()
    {
        return solutionManagedBean.getReservedBookings();
    }
    
    public MovieDTO getMovieByID(String id)
    {
        return solutionManagedBean.getMovieByID(id);
    }
    
    public CinemaDTO getCinemaByID(String id)
    {
        return solutionManagedBean.getCinemaByID(id);
    }
    
    public TicketTypeDTO getTicketTypeByID(String id)
    {
        return solutionManagedBean.getTicketTypeByID(id);
    }
    
    public UsersDTO getUserByID(String id)
    {
        return solutionManagedBean.getUserByID(id);
    }
    
    public void startConversation() {
        conversation.begin();
    }

    public void endConversation() {
        conversation.end();
    }
    
    public BookingDTO getBookingByID(String id)
    {
        return solutionManagedBean.getBookingByID(id);
    }
    
    public ReservedBookingDTO getReservationByID(String id)
    {
        return solutionManagedBean.getReservedBookingByID(id);
    }
    
    public UsersDTO getUserByEmail(String email)
    {
        return solutionManagedBean.getUserByEmail(email);
    }
    
    public String getUserEmail()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();       
        //WILL ONLY WORK IF ITS LOGGED IN!!!!!
        return request.getRemoteUser();
    }
    
    public void redirect(String url)
    {
        try {
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    public List<BookingDTO> getBookings()
    {
        return solutionManagedBean.getAllBookings();
    }
    
    public List<BookingDTO> getUserBookings()
    {
        return solutionManagedBean.getBookingsByUserEmail(this.getUserEmail());
    }
    
    
    public void validateDouble (FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException 
    {
            String price = (String) value.toString();
        
        try 
        {
            Double.parseDouble(price.toString());
        }
        catch (Exception e)
        {
            FacesMessage message = new FacesMessage(
                "Input must be a number");
            throw new ValidatorException(message);
          
        }
    }
    
    public void validateInt (FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException 
    {
            String price = (String) value;
        
        try 
        {
            Integer.parseInt(price);
        }
        catch (Exception e)
        {
            FacesMessage message = new FacesMessage(
                "Input must be a number");
            throw new ValidatorException(message);
          
        }
    }
    
    
    public List<ShowingDTO> getShowingsByMovieID(String MovieID)
    {
        return solutionManagedBean.getShowingsByMovieID(MovieID);
    }

}
