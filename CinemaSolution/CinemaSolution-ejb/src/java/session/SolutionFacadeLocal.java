/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Booking;
import entity.Cinema;
import entity.GroupMapping;
import entity.Movie;
import entity.ReservedBooking;
import entity.Showing;
import entity.TicketType;
import entity.Users;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author KeesTim
 */

/*
LOCAL INTERFACE OF EJB BOOKINGBEAN
*/
@Local
public interface SolutionFacadeLocal 
{
    boolean edit(Object entity);
    
    void delete (Object entity);

    boolean addCinema(Cinema cinema);

    List<Cinema> getAllCinemas();

    boolean addMovie(Movie movie);

    List<Movie> getAllMovies();
    
    List<Movie> getAllMoviesSortAZ();
    
    List<Movie> getAllMoviesSortNumShowings() ;
    
    Movie readMovie(String id);
    
    Cinema readCinema (String id);

    boolean addShowing(Showing showing);
    
    Showing readShowing(String id);

    boolean addTicketType(TicketType tickettype);
    
    TicketType readTicketType(String id);
    
    Users readUser(String id);

    boolean addUser(Users users);

    List<Showing> getCurrentShowings();
    
    public GroupMapping readGroupMapping(String id);

    Users getUserByEmail(String email);

    boolean addGroupMapping(GroupMapping groupmapping);

    Integer showingNumBookings(String showing_id);

    List<Movie> getAllMoviesSortNumShowings(int limit);
    
    List<TicketType> getAllTicketTypes();

    List<Showing> getAllShowings();

    List<Users> getAllUsers();

    List<Users> getUsersByGroupType(String user_type);

    boolean addBooking(Booking booking_entity);
    
    Booking readBooking (String id);

    boolean addReservedBooking(Booking booking_entity, ReservedBooking reservation_entity);

    List<ReservedBooking> getAllReservedBookings(); 

    Users getUserByID(String id);

    ReservedBooking readReservation(String id);

    List<Booking> getAllBookings();

    List<Showing> getShowingsForGivenTime(String time_frame);

    List<Booking> getBookingsByUserEmail(String email);

    List<Movie> getMovieByName(String film_name);
    
    List<Users> getAllUsers(String query_string);
    
    List<Users> getUsersByGroupType(String user_type, String query_string);
}
