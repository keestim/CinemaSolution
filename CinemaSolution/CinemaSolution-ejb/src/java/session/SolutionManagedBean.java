/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Booking;
import entity.BookingDTO;
import entity.Cinema;
import entity.CinemaDTO;
import entity.GroupMapping;
import entity.GroupMappingDTO;
import entity.Movie;
import entity.MovieDTO;
import entity.ReservedBooking;
import entity.ReservedBookingDTO;
import entity.Showing;
import entity.ShowingDTO;
import entity.TicketType;
import entity.TicketTypeDTO;
import entity.Users;
import entity.UsersDTO;
import static java.lang.String.format;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author KeesTim
 */
@Stateless (mappedName = "ejb/SolutionManagedBean")
public class SolutionManagedBean implements SolutionManagedBeanRemote 
{

    @EJB
    private SolutionFacadeLocal solutionFacade;    
    
    //adds new cinema to database, from a given input dto
    @Override
    public boolean addCinema(CinemaDTO cinemaDTO) 
    {
        Cinema cinema = new Cinema(cinemaDTO.getCinemaId(), cinemaDTO.getNumRows(), cinemaDTO.getIsleWidth());
        return solutionFacade.addCinema(cinema);
    }

    //adds new movie to database, from a given input dto
    @Override
    public boolean addMovie(MovieDTO movieDTO) 
    {
                //make sure this value exist
                //movie id can't be null
        Movie movie = new Movie(0, 
                movieDTO.getName(), 
                movieDTO.getRunTime(), 
                movieDTO.getRating(), 
                movieDTO.getDescription());

        return solutionFacade.addMovie(movie);
    }

    //gets all of the movies in the database
    @Override
    public List<MovieDTO> getAllMovies() 
    {
        List<MovieDTO> return_list = new ArrayList<MovieDTO>();
        for (Movie movie : solutionFacade.getAllMovies())
        {
            //gets all of the entity objects returned and converts them to DTO form
            return_list.add(new MovieDTO(movie.getId(), movie.getName(), movie.getRunTime(), 
                    movie.getRating(), movie.getDescription()));
        }
        
        return return_list;
    }
    
    @Override
    //gets all of the movies in alphabetical order by name
    public List<MovieDTO> getAllMoviesSortAZ() 
    {
        List<MovieDTO> return_list = new ArrayList<MovieDTO>();
        for (Movie movie : solutionFacade.getAllMoviesSortAZ())
        {
            //gets all of the entity objects returned and converts them to DTO form
            return_list.add(new MovieDTO(movie.getId(), movie.getName(), movie.getRunTime(), 
                    movie.getRating(), movie.getDescription()));
        }
        
        return return_list;
    }
    
    @Override
    //gets all of the movies, but sorts them by the number of showings associated with them
    public List<MovieDTO> getAllMoviesSortNumShowings() 
    {
        List<MovieDTO> return_list = new ArrayList<MovieDTO>();
        for (Movie movie : solutionFacade.getAllMoviesSortNumShowings())
        {
            //gets all of the entity objects returned and converts them to DTO form
            return_list.add(new MovieDTO(movie.getId(), movie.getName(), movie.getRunTime(), 
                    movie.getRating(), movie.getDescription()));
        }
        
        return return_list;
    }
    
    @Override
    //gets all of the movies, but sorts them by the number of showings associated with them
    //restricts the number of showing by an input limit
    public List<MovieDTO> getAllMoviesSortNumShowings(int limit) 
    {
        List<MovieDTO> return_list = new ArrayList<MovieDTO>();
        for (Movie movie : solutionFacade.getAllMoviesSortNumShowings(limit))
        {
            //gets all of the entity objects returned and converts them to DTO form
            return_list.add(new MovieDTO(movie.getId(), movie.getName(), movie.getRunTime(), 
                    movie.getRating(), movie.getDescription()));
        }
        
        return return_list;
    }


    //gets all of the cinemas in the database
    @Override
    public List<CinemaDTO> getAllCinemas() 
    {
        List<CinemaDTO> return_list = new ArrayList<CinemaDTO>();
        for (Cinema cinema : solutionFacade.getAllCinemas())
        {
            //gets all of the entity objects returned and converts them to DTO form
            return_list.add(this.cinemaEntity2DTO(cinema));
        }
        
        return return_list;
    }
    
    //adds new showing to database, from a given input dto
    @Override
    public boolean addShowing(ShowingDTO showingDTO) 
    {
        //converters input DTO to entity form, passes it into method in solutionFacade    
        return solutionFacade.addShowing(this.showingDTO2Entity(showingDTO));
    }

    //returns a given cinema dto for a given input id (a entry with that id exists)
    @Override
    public CinemaDTO getCinemaByID(String Id) 
    {
        //if the cinema returned isn't null (i.e. it actually exists)
        Cinema cinema = solutionFacade.readCinema(Id);
        if (cinema != null)
        {
            //gets all of the entity objects returned and converts them to DTO form    
            return this.cinemaEntity2DTO(cinema);
        }
        else 
        {
            return null;
        }
    }

    //returns a given movie dto for a given input id (a entry with that id exists)
    @Override
    public MovieDTO getMovieByID(String Id) 
    {
        //if the movie returned isn't null (i.e. it actually exists)
        Movie movie = solutionFacade.readMovie(Id.toString());
        if (movie != null)
        {
            //gets all of the entity objects returned and converts them to DTO form   
            return this.movieEntity2DTO(movie);
        }
        else 
        {
            return null;
        }
    }

    //returns a given showing dto for a given input id (a entry with that id exists)
    @Override
    public ShowingDTO getShowingByID(String id) 
    {
        //if the showing returned isn't null (i.e. it actually exists)
        Showing showing = solutionFacade.readShowing(id);
        if (showing != null)
        {
            //gets all of the entity objects returned and converts them to DTO form   
            return new ShowingDTO(showing.getId(), showing.getStartTime(), showing.getStartDate(), 
                    cinemaEntity2DTO(showing.getCinemaId()), movieEntity2DTO(showing.getMovieId()));
        }
        else 
        {
            return null;
        }
    }
    
    //returns a given tickettype dto for a given input id (a entry with that id exists)
    @Override
    public TicketTypeDTO getTicketTypeByID(String id) 
    {
        TicketType tickettype = solutionFacade.readTicketType(id);
       
        //if the tickettype returned isn't null (i.e. it actually exisit)
        if (tickettype != null)
        {
            //if it exists, then entity is converted to DTO form
            return new TicketTypeDTO(tickettype.getId(), tickettype.getTypeName(), tickettype.getPrice());
        }
        else 
        {
            return null;
        }
    }
    
    //adds new ticket type to database
    @Override
    public boolean addTicketType(TicketTypeDTO tickettypeDTO) 
    {
        //converts ticket type dto to entity form
        TicketType tickettype = new TicketType(tickettypeDTO.getId(), 
                tickettypeDTO.getTypeName(), tickettypeDTO.getPrice());
        //calls solutionFacade method to add the entity to the database
        return solutionFacade.addTicketType(tickettype);
    }

    //adds new user to database
    @Override
    public boolean addUsers(UsersDTO usersDTO) 
    {
        Users users = new Users(usersDTO.getId(), 
                usersDTO.getEmail(), 
                usersDTO.getName(), 
                usersDTO.getPassword(), 
                usersDTO.getAddress(), 
                usersDTO.getPhone());
        
        GroupMapping mapping = new GroupMapping(usersDTO.getEmail(), usersDTO.getGroup_mapping().getGroupName());
        users.setGroupMapping(mapping);
        
         if (!solutionFacade.addUser(users))
         {
             return false;
         }
         
         //get user id, then add group mapping!
         Users db_user = solutionFacade.getUserByEmail(usersDTO.getEmail());
         
         if (db_user == null)
         {
             return false;
         }
         
         return true;
    }

    @Override
    public List<ShowingDTO> getCurrentShowings() 
    {
        List<ShowingDTO> return_list = new ArrayList<ShowingDTO>();
        for (Showing showing : solutionFacade.getCurrentShowings())
        {
            return_list.add(new ShowingDTO(showing.getId(), showing.getStartTime(), showing.getStartDate(), 
                    cinemaEntity2DTO(showing.getCinemaId()), movieEntity2DTO(showing.getMovieId())));
        }
        
        return return_list;
    }
    
    @Override
    public List<ShowingDTO> getAllShowings() 
    {
        List<ShowingDTO> return_list = new ArrayList<ShowingDTO>();
        for (Showing showing : solutionFacade.getAllShowings())
        {
            return_list.add(new ShowingDTO(showing.getId(), showing.getStartTime(), showing.getStartDate(), 
                    cinemaEntity2DTO(showing.getCinemaId()), movieEntity2DTO(showing.getMovieId())));
        }
        
        return return_list;
    }
    
    //converts movie entity to movie dto object
    private MovieDTO movieEntity2DTO(Movie input_entity)
    {
        return new MovieDTO(input_entity.getId(), input_entity.getName(), input_entity.getRunTime(), 
                input_entity.getRating(), input_entity.getDescription());
    }
    
    //converts movie dto to movie entity object
    private Movie movieDTO2Entity(MovieDTO input_dto)
    {
        return new Movie(input_dto.getId(), input_dto.getName(), input_dto.getRunTime(), 
                input_dto.getRating(), input_dto.getDescription());
    }
    
    //converts cinema entity to cinema dto object
    private CinemaDTO cinemaEntity2DTO(Cinema input_entity)
    {
        return new CinemaDTO(input_entity.getCinemaId(), input_entity.getNumRows(), input_entity.getIsleWidth());
    }
    
    //converts cinema dto to cinema entity object
    private Cinema cinemaDTO2Entity(CinemaDTO input_dto)
    {
        return new Cinema(input_dto.getCinemaId(), input_dto.getNumRows(), input_dto.getIsleWidth());
    }
    
    //converts showing dto to showing enity object
    private Showing showingDTO2Entity(ShowingDTO input_dto)
    {
        return new Showing(input_dto.getId(), input_dto.getStartTime(), input_dto.getStartDate(), 
                solutionFacade.readCinema(input_dto.getCinema().getCinemaId().toString()), 
                solutionFacade.readMovie(input_dto.getMovieId().getId().toString()));
    }
    
    //convert tickettype dto to tickettype entity object
    private TicketType tickettypeDTO2Entity(TicketTypeDTO input_dto)
    {
        return new TicketType(input_dto.getId(), input_dto.getTypeName(), input_dto.getPrice());
    }

    @Override
    public Integer showingNumBookings(String showing_id) 
    {
        return solutionFacade.showingNumBookings(showing_id);
    }

    
    //gets all of the ticket type entries from the database
    @Override
    public List<TicketTypeDTO> getAllTicketTypes() 
    {
        List<TicketTypeDTO> return_list = new ArrayList<TicketTypeDTO>();
        
        for (TicketType tickettype : solutionFacade.getAllTicketTypes())
        {
            return_list.add(new TicketTypeDTO(tickettype.getId(), 
                    tickettype.getTypeName(), tickettype.getPrice()));
        }        
        
        return return_list;
    }

    //updates cinema database entry, from input dto
    @Override
    public boolean updateCinema(CinemaDTO cinemaDTO) 
    {
        Cinema cinema = solutionFacade.readCinema(cinemaDTO.getCinemaId().toString());
        if (cinema != null)
        {        
            solutionFacade.edit(this.cinemaDTO2Entity(cinemaDTO));
            return true;
        }
        else 
        {
            return false;
        }
    }
    
    //deletes cinema database entry, from input id
    @Override
    public boolean deleteCinema(String id) 
    {
        Cinema cinema = solutionFacade.readCinema(id);
        if (cinema != null)
        {        
            solutionFacade.delete(cinema);
            return true;
        }
        else 
        {
            return false;
        }
    }
    
    //deletes movie database entry, from input id
    @Override
    public boolean deleteMovie(String id) 
    {
        Movie movie = solutionFacade.readMovie(id);
        if (movie != null)
        {        
            solutionFacade.delete(movie);
            return true;
        }
        else 
        {
            return false;
        }
    } 
    
    //converts input group mapping entity to group mapping DTO
    private GroupMappingDTO groupmappingEntity2DTO(GroupMapping input_entity)
    {
        return new GroupMappingDTO(input_entity.getEmail(), input_entity.getGroupName());
    }

    //gets all users from the database
    @Override
    public List<UsersDTO> getAllUsers() 
    {
        List<UsersDTO> return_list = new ArrayList<UsersDTO>();
        for (Users user : solutionFacade.getAllUsers())
        {
            return_list.add(new UsersDTO(user.getId(), user.getName(), user.getEmail(), 
                    user.getPassword(), user.getAddress(), user.getPhone(), 
                    user.getGroupMapping().getGroupName()));
        }
        
        return return_list;
    }
    
    
    //gets all user from the database, where the user name or email is like the input query_string
    @Override
    public List<UsersDTO> getAllUsers(String query_string) 
    {
        List<UsersDTO> return_list = new ArrayList<UsersDTO>();
        for (Users user : solutionFacade.getAllUsers(query_string))
        {
            return_list.add(new UsersDTO(user.getId(), user.getName(), user.getEmail(), 
                    user.getPassword(), user.getAddress(), user.getPhone(), 
                    user.getGroupMapping().getGroupName()));
        }
        
        return return_list;
    }

    //updates movie entry in the database, from a movieDTO entry 
    @Override
    public boolean updateMovie(MovieDTO movieDTO) 
    {
        Movie movie = solutionFacade.readMovie(movieDTO.getId().toString());
        //ensures the return entity exisits (i.e. isn't null)
        if (movie != null)
        {        
            solutionFacade.edit(this.movieDTO2Entity(movieDTO));
            return true;
        }
        else 
        {
            return false;
        }
    }
    
    //updates showing entry in the database, from a showingDTO entry 
    @Override
    public boolean updateShowing(ShowingDTO showingDTO) 
    {
        Showing showing = solutionFacade.readShowing(showingDTO.getId().toString());
        //ensures the return entity exisits (i.e. isn't null)
        if (showing != null)
        {        
            solutionFacade.edit(this.showingDTO2Entity(showingDTO));
            return true;
        }
        else 
        {
            return false;
        }
    }

    //gets all users that a group type that matches the input group type
    @Override
    public List<UsersDTO> getUsersByGroupType(String group_name) 
    {
        List<UsersDTO> return_list = new ArrayList<UsersDTO>();
        for (Users user : solutionFacade.getUsersByGroupType(group_name))
        {
            return_list.add(new UsersDTO(user.getId(),user.getName(), user.getEmail(), 
                    user.getPassword(), user.getAddress(), user.getPhone(), 
                    user.getGroupMapping().getGroupName()));
        }
        
        return return_list;
    }
    
    //retuns all the user entries in the database that have a name or email that matches the query string input
    //and have a group type that matches the group_name
    @Override
    public List<UsersDTO> getUsersByGroupType(String group_name, String query_string) 
    {
        List<UsersDTO> return_list = new ArrayList<UsersDTO>();
        for (Users user : solutionFacade.getUsersByGroupType(group_name, query_string))
        {
            return_list.add(new UsersDTO(user.getId(),user.getName(), user.getEmail(), 
                    user.getPassword(), user.getAddress(), user.getPhone(), 
                    user.getGroupMapping().getGroupName()));
        }
        
        return return_list;
    }

    //updates ticket type entry in data base, from using the input ticket type dto to do so
    @Override
    public boolean updateTicketType(TicketTypeDTO dto) 
    {
        TicketType tickettype = solutionFacade.readTicketType(dto.getId().toString());
       //ensures the return entity exisits (i.e. isn't null)
        if (tickettype != null)
        {        
            solutionFacade.edit(this.tickettypeDTO2Entity(dto));
            return true;
        }
        else 
        {
            return false;
        }
    }

    @Override
    public boolean deleteTicketType(String id) {
        TicketType tickettype = solutionFacade.readTicketType(id);
        //ensures the return entity exisits (i.e. isn't null)
        if (tickettype != null)
        {        
            solutionFacade.delete(tickettype);
            return true;
        }
        else 
        {
            return false;
        }
    }

    //deletes session where session id matches input id, from the database
    @Override
    public boolean deleteSession(String id) {
        Showing showing = solutionFacade.readShowing(id);
        //ensures the return entity exisits (i.e. isn't null)
        if (showing != null)
        {        
            solutionFacade.delete(showing);
            return true;
        }
        else 
        {
            return false;
        }
    }

    //converts an input user entity object to a users dto object
    private UsersDTO userEntity2DTO(Users user)
    {
        return new UsersDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), 
                user.getAddress(), user.getPhone(), this.groupmappingEntity2DTO(user.getGroupMapping()));
    }
    
    @Override
    public UsersDTO getUserByID(String id)
    {
        Users user = solutionFacade.getUserByID(id);
        //ensures the return entity exisits (i.e. isn't null)
        if (user != null)
        {
            return this.userEntity2DTO(user);
        }
        else 
        {
            return null;
        } 
    }    

    //converts input users dto to a users entity object
    private Users userDTO2Entity(UsersDTO dto)
    {
        return new Users(dto.getId(), dto.getEmail(), dto.getName(),
                dto.getPassword(), dto.getAddress(), dto.getPhone(), 
                new GroupMapping(dto.getGroup_mapping().getEmail(), 
                        dto.getGroup_mapping().getGroupName()));
    }
    
    //updates user using the data from the provided input dto
    @Override
    public boolean updateUser(UsersDTO dto) 
    {
        Users return_user = solutionFacade.readUser(dto.getEmail().toString());
        
        //cannot edit user if a user with the ID doesn't exist
        if (return_user != null)
        {                    
            Users new_obj = this.userDTO2Entity(dto);
            GroupMapping new_mapping = return_user.getGroupMapping();
            new_mapping.setGroupName(dto.getGroup_mapping().getGroupName());
            new_obj.setGroupMapping(new_mapping);
            
            return solutionFacade.edit(new_obj);
        }
        else 
        {
            return false;
        }
    }

    //adds booking to database from input dto
    @Override
    public boolean addBooking(BookingDTO dto) 
    {        
        Booking booking_entity = new Booking(0, dto.getPaid(), dto.getNumTickets(), 
                solutionFacade.readShowing(dto.getShowing().getId().toString()), 
                solutionFacade.readTicketType(dto.getTicket_type().getId().toString()), 
                dto.getTotalPrice(), solutionFacade.readUser(dto.getUser().getEmail()));
        
        System.out.println(booking_entity.getShowingId().getId().toString());
        System.out.println(this.showingNumBookings(booking_entity.getShowingId().getId().toString()));
        
        //checks if there is enough seats avalible as the seats requested
        if (booking_entity.getShowingId().getCinemaId().getNumSeats() - this.showingNumBookings(booking_entity.getShowingId().getId().toString()) - booking_entity.getNumTickets() >= 0)
        {
            //if there are enough seats avalible, then booking is added
            if (solutionFacade.addBooking(booking_entity))
            {
                return true;
            }
            else 
            {
                return false;
            }
        }
        else 
        {
            return false;
        }
    }
    
    //adds a reserved booking to the solution database
    @Override
    public boolean addReservedBooking(BookingDTO dto, ReservedBookingDTO reservation_dto)
    {
        Booking booking_entity = new Booking(0, dto.getPaid(), dto.getNumTickets(), 
                solutionFacade.readShowing(dto.getShowing().getId().toString()), 
                solutionFacade.readTicketType(dto.getTicket_type().getId().toString()), dto.getTotalPrice());
        
        ReservedBooking reservation_entity = new ReservedBooking(0, reservation_dto.getReservationName(), 
                reservation_dto.getReservationPhone(), null);
        
        //calls method in solutionFacade that will add both the booking entity and reservated 
        //booking entity to the database
        Boolean result = solutionFacade.addReservedBooking(booking_entity, reservation_entity);
        
        if (result)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    
    //converts input ticket type entity to ticket type dto object
    private TicketTypeDTO tickettypeEntity2DTO(TicketType entity)
    {
        return new TicketTypeDTO(entity.getId(), entity.getTypeName(), entity.getPrice());
    }
    
    //converts input showing entity to showing dto object
    private ShowingDTO showingEntity2DTO(Showing entity)
    {
        return new ShowingDTO(entity.getId(), entity.getStartTime(), entity.getStartDate(), this.cinemaEntity2DTO(entity.getCinemaId()), this.movieEntity2DTO(entity.getMovieId()));
    }
    
    
    //converts input booking entity to booking dto objet
    private BookingDTO bookingEntity2DTO(Booking entity)
    {
        return new BookingDTO(entity.getId(), entity.getPaid().booleanValue(), this.tickettypeEntity2DTO(entity.getTicketType()), 
                this.showingEntity2DTO(entity.getShowingId()), this.userEntity2DTO(entity.getUserEmail()), 
                entity.getNumTickets(), entity.getTotalPrice());
    }
    
    
    //converts input booking dto to booking entity dto
    private Booking bookingDTO2Entity(BookingDTO dto)
    {
        return new Booking(dto.getId(), dto.getPaid(), dto.getNumTickets(), 
                this.showingDTO2Entity(dto.getShowing()), this.tickettypeDTO2Entity(dto.getTicket_type()), 
                dto.getTotalPrice(), this.userDTO2Entity(dto.getUser()));
    }

    //gets all of the reserved bookings
    @Override
    public List<ReservedBookingDTO> getReservedBookings() {
        List<ReservedBookingDTO> return_list = new ArrayList<ReservedBookingDTO>();
        
        for (ReservedBooking reservation : solutionFacade.getAllReservedBookings())
        {
            Booking booking_entity = solutionFacade.readBooking(reservation.getBookingId().getId().toString());
            
            BookingDTO booking_dto = new BookingDTO(booking_entity.getId(), booking_entity.getPaid(), this.tickettypeEntity2DTO(booking_entity.getTicketType()), 
                    this.showingEntity2DTO(booking_entity.getShowingId()), booking_entity.getNumTickets(), booking_entity.getTotalPrice());
            
            return_list.add(new ReservedBookingDTO(reservation.getId(), reservation.getReservationName(), 
                reservation.getReservationPhone(), booking_dto));
        }
        
        return return_list;
    }

    //deletes user by id
    @Override
    public boolean deleteUser(String id) {
        Users user = solutionFacade.readUser(id);
        //checks that the returned object isn't null
        if (user != null)
        {        
            solutionFacade.delete(user);
            return true;
        }
        else 
        {
            return false;
        }
    }

    //gets user entry where user email matches the input email string
    @Override
    public UsersDTO getUserByEmail(String email) {
        Users user = solutionFacade.getUserByEmail(email);
         //checks that the returned object isn't null
        if (user != null)
        {
            return this.userEntity2DTO(user);
        }
        else 
        {
            return null;
        } 
    }

     //gets user entry where user id matches the input id string
    @Override
    public BookingDTO getBookingByID(String id) 
    {
        Booking booking = solutionFacade.readBooking(id);
        return this.bookingEntity2DTO(booking);
    }

    //get reserved booking entity for database, which matches the input id
    @Override
    public ReservedBookingDTO getReservedBookingByID(String id) 
    {
        ReservedBooking reservation = solutionFacade.readReservation(id);
        if (reservation != null)
        {
            Booking booking_entity = solutionFacade.readBooking(reservation.getBookingId().getId().toString());
            BookingDTO booking_dto = new BookingDTO(booking_entity.getId(), booking_entity.getPaid(), 
                this.tickettypeEntity2DTO(booking_entity.getTicketType()), 
                this.showingEntity2DTO(booking_entity.getShowingId()), booking_entity.getNumTickets(), 
                    booking_entity.getTotalPrice());

            
            return new ReservedBookingDTO(reservation.getId(), reservation.getReservationName(), 
                reservation.getReservationPhone(), booking_dto);
        }
        else 
        {
            return null;
        }
    }
    
    //converts input reserved booking entity to reserved booking dto
    private ReservedBookingDTO reservedBookingEntity2DTO(ReservedBooking entity)
    {
        return new ReservedBookingDTO(entity.getId(), entity.getReservationName(), entity.getReservationPhone(), 
                this.bookingEntity2DTO(entity.getBookingId()));
    }
    
    //converts input reserved booking dto to reserved booking entity
    private ReservedBooking reservedBookingDTO2Entity(ReservedBookingDTO dto)
    {
        return new ReservedBooking(dto.getId(), dto.getReservationName(), dto.getReservationPhone(), 
            new Booking(dto.getBooking().getId(), 
            dto.getBooking().getPaid(), 
            dto.getBooking().getNumTickets(), 
            this.showingDTO2Entity(dto.getBooking().getShowing()), 
            this.tickettypeDTO2Entity(dto.getBooking().getTicket_type()), 
            dto.getBooking().getTotalPrice(), null));
    } 

    //deletes booking entry where reserved booking id matches the input id 
    @Override
    public boolean deleteBooking(String id) {
        Booking booking = solutionFacade.readBooking(id);

        if (booking != null)
        {        
            solutionFacade.delete(booking);
            return true;
        }
        else 
        {
            return false;
        }
    }

    
    //deletes reservation entry where reserved booking id matches the input id 
    @Override
    public boolean deleteReservation(String id) {
        ReservedBooking reservation = solutionFacade.readReservation(id);

        if (reservation != null)
        {        
            solutionFacade.delete(reservation);
            
            this.deleteBooking(reservation.getBookingId().getId().toString());
            
            return true;
        }
        else 
        {
            return false;
        }
    }

    
    //ABSTRACT FURTHER!
    @Override
    public boolean updateReservation(ReservedBookingDTO dto) 
    {
        ReservedBooking reservation = solutionFacade.readReservation(dto.getId().toString());
        if (reservation != null)
        {
            solutionFacade.edit(this.reservedBookingDTO2Entity(dto));
            solutionFacade.edit(new Booking(dto.getBooking().getId(), 
                dto.getBooking().getPaid(), 
                dto.getBooking().getNumTickets(), 
                this.showingDTO2Entity(dto.getBooking().getShowing()), 
                this.tickettypeDTO2Entity(dto.getBooking().getTicket_type()), 
                dto.getBooking().getTotalPrice(), null));
            
            return true;
        }
        else 
        {
            return false;
        }
    }
    
    //gets all of the bookings from the solution database
    @Override
    public List<BookingDTO> getAllBookings() 
    {
        List<BookingDTO> return_list = new ArrayList<BookingDTO>();
        
        for (Booking booking : solutionFacade.getAllBookings())
        {
            //if the email field for the stirng isn't null, then it will be added to the return list
            if (booking.getUserEmail() != null)
            {
                return_list.add(this.bookingEntity2DTO(booking));
            }
        }
        
        return return_list;
    }

    
    //update booking entry in database from input dto
    @Override
    public boolean updateBooking(BookingDTO dto) 
    {
        Booking booking = solutionFacade.readBooking(dto.getId().toString());
        
        if (booking != null)
        {
            solutionFacade.edit(this.bookingDTO2Entity(dto));
            return true;
        }
        else 
        {        
            return false;
        }
    }

    @Override
    public List<ShowingDTO> getShowingsForGivenTime(String time_frame) 
    {
        List<ShowingDTO> return_list = new ArrayList<ShowingDTO>();
        for (Showing showing : solutionFacade.getShowingsForGivenTime(time_frame))
        {
            return_list.add(new ShowingDTO(showing.getId(), showing.getStartTime(), showing.getStartDate(), 
                    cinemaEntity2DTO(showing.getCinemaId()), movieEntity2DTO(showing.getMovieId())));
        }
        
        return return_list;
    }

    //gets all of the booking that have been booked by a user with the given input email
    @Override
    public List<BookingDTO> getBookingsByUserEmail(String email) 
    {
        List<BookingDTO> return_list = new ArrayList<BookingDTO>();
        
        for (Booking booking : solutionFacade.getBookingsByUserEmail(email))
        {
            if (booking.getUserEmail() != null)
            {
                return_list.add(this.bookingEntity2DTO(booking));
            }
            else 
            {
            
            }
        }
        return return_list;
    }

    
    //queries the database for a movie/s that has a name like the input string
    @Override
    public List<MovieDTO> getMovieByName(String film_name) 
    {
        List<MovieDTO> return_list = new ArrayList<MovieDTO>();
        
        for (Movie movie : solutionFacade.getMovieByName(film_name))
        {
            //converts returned movie entity to movie dto
            return_list.add(this.movieEntity2DTO(movie));
        }
        
        return return_list;
    }

    //checks that an input email and password exist in the database, for a user with the input email
    @Override
    public boolean checkInputPassword(String email, String password) 
    {
        //gets user by the input email    
        Users return_user = solutionFacade.getUserByEmail(email);
        
        //check that the return_user's password mathces the input password
        if (return_user != null)
        {
            if (return_user.getPassword().equals(password))
            {
                return true;
            }
        }
        
        return false;
    }

    //updates password on database
    @Override
    public boolean updatePassword(UsersDTO userDTO) 
    {
        //updates user password, calling method in the solutionFacade EJB
        Users current_user = solutionFacade.getUserByEmail(userDTO.getEmail());
        
        if (current_user != null)
        {
            //given that only attribute that is being update is password
            //it is easier to just get the exisitng entity, and just modify the password field
            current_user.setPassword(userDTO.getPassword());
            solutionFacade.edit(current_user);
        }
        
        return false;       
    }

    
    //gets all showings for a given movie, with movie being found via an input id
    @Override
    public List<ShowingDTO> getShowingsByMovieID(String MovieID) 
    {
        List<ShowingDTO> return_list = new ArrayList<ShowingDTO>();
        //gets movie entity, by input id, then gets all the showings that use the movie id, in the movie id foreign key
        for (Showing showing : solutionFacade.readMovie(MovieID).getShowingCollection())
        {
            if (showing.getId() != null)
            {
                LocalDate localDate = LocalDate.now();
                Date date = Date.valueOf(localDate);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");   

                String showing_time = format.format(showing.getStartDate()).toString();
                String current_date = format.format(date).toString();

                if (current_date.compareTo(showing_time) <= 0)
                {
                    //check that the date of the showing is today or in the future
                    return_list.add(this.showingEntity2DTO(showing));
                }
            }
        }
        
        return return_list;
    }
}
