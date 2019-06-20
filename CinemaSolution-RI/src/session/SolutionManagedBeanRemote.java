/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.BookingDTO;
import entity.CinemaDTO;
import entity.MovieDTO;
import entity.ReservedBookingDTO;
import entity.ShowingDTO;
import entity.TicketTypeDTO;
import entity.UsersDTO;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author KeesTim
 */

/*
REMOTE INTERFACE OF EJB SOLUTIONMANAGEDBEAN
*/

@Remote
public interface SolutionManagedBeanRemote 
{
    boolean addCinema(CinemaDTO cinemaDTO);

    boolean addMovie(MovieDTO movieDTO);

    List<MovieDTO> getAllMovies();

    List<CinemaDTO> getAllCinemas();

    boolean addShowing(ShowingDTO showingDTO);

    CinemaDTO getCinemaByID(String Id);

    MovieDTO getMovieByID(String Id);

    boolean addTicketType(TicketTypeDTO tickettypeDTO);

    boolean addUsers(UsersDTO usersDTO);

    List<ShowingDTO> getCurrentShowings();

    ShowingDTO getShowingByID(String id);
    
    List<UsersDTO> getAllUsers(String query_string);
    
    List<UsersDTO> getUsersByGroupType(String group_name, String query_string);

    Integer showingNumBookings(String showing_id);

    List<TicketTypeDTO> getAllTicketTypes();

    TicketTypeDTO getTicketTypeByID(String id);

    boolean updateCinema(CinemaDTO cinemaDTO);

    boolean deleteCinema(String id);
    
    boolean deleteMovie(String id);

    List<ShowingDTO> getAllShowings();

    List<UsersDTO> getAllUsers();

    boolean updateMovie(MovieDTO movieDTO);

    boolean updateShowing(ShowingDTO showingDTO);

    List<UsersDTO> getUsersByGroupType(String group_name);

    boolean updateTicketType(TicketTypeDTO tickettype);

    boolean deleteTicketType(String id);

    boolean deleteSession(String id);

    UsersDTO getUserByID(String id);

    boolean updateUser(UsersDTO user);

    boolean addBooking(BookingDTO dto);
    
    boolean addReservedBooking(BookingDTO booking_entity, ReservedBookingDTO reservation_entity);

    List<ReservedBookingDTO> getReservedBookings();

    boolean deleteUser(String id);

    UsersDTO getUserByEmail(String email);

    BookingDTO getBookingByID(String id);

    ReservedBookingDTO getReservedBookingByID(String id);

    boolean deleteBooking(String id);

    boolean deleteReservation(String id);

    boolean updateReservation(ReservedBookingDTO dto);

    List<BookingDTO> getAllBookings();

    boolean updateBooking(BookingDTO dto);

    List<ShowingDTO> getShowingsForGivenTime(String time_frame);

    List<BookingDTO> getBookingsByUserEmail(String email);

    List<MovieDTO> getMovieByName(String film_name);

    boolean checkInputPassword(String email, String password);

    boolean updatePassword(UsersDTO userDTO);
    
    List<MovieDTO> getAllMoviesSortAZ();
    
    List<MovieDTO> getAllMoviesSortNumShowings();
    
    List<MovieDTO> getAllMoviesSortNumShowings(int limit);

    List<ShowingDTO> getShowingsByMovieID(String MovieID);
}
