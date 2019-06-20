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
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author KeesTim
 */
@DeclareRoles({"Admin", "Employee", "User"})
@Stateless
@TransactionManagement(value=TransactionManagementType.BEAN)
public class SolutionFacade implements SolutionFacadeLocal {

    @PersistenceContext(unitName = "CinemaSolution-ejbPU")
    private EntityManager em;
    
    @Resource
    private UserTransaction utx;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public SolutionFacade()
    {
    
    }
    
    //creates a database entry for a given object entity
    private void create (Object entity)
    {
        em.persist(entity);
    }
    
    //modifies a given database entry
    @Override
    public boolean edit(Object entity)
    {
        try 
        {
            utx.begin();
            em.merge(entity);
            utx.commit();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
    
    //deletes a given database entry
    @Override
    public void delete (Object entity)
    {
        try 
        {
            utx.begin();
            em.remove(em.merge(entity));
            utx.commit();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    @Override
    public Users readUser(String id)
    {
        return em.find(Users.class, id);
    }

    @Override
    public TicketType readTicketType(String id)
    {
        return em.find(TicketType.class, Integer.parseInt(id));
    }
    
    @Override
    public Showing readShowing(String id)
    {
        return em.find(Showing.class, Integer.parseInt(id));
    }

    @Override
    public Movie readMovie(String id)
    {        
        return em.find(Movie.class, Integer.parseInt(id));
    }
    
    @Override
    public GroupMapping readGroupMapping(String id){
        return em.find(GroupMapping.class, id);
    }

    @Override
    @RolesAllowed({"Admin"})
    public Cinema readCinema (String id)
    {
        return em.find(Cinema.class, Integer.parseInt(id));
    }
    
    @Override
    public Booking readBooking (String id)
    {
        return em.find(Booking.class, Integer.parseInt(id));
    }

    @Override
    @RolesAllowed({"Admin"})
    public boolean addCinema(Cinema cinema) 
    {
        try 
        {
            utx.begin();
            Cinema c = readCinema(cinema.getCinemaId().toString());

            if (c != null)
            {
                return false;
            }
            create(cinema);

            utx.commit();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public List<Cinema> getAllCinemas() 
    {
        return em.createQuery("SELECT c FROM Cinema c").getResultList();
    }

    @Override
    @RolesAllowed({"Admin", "Employee"})
    public boolean addMovie(Movie movie) 
    {
        try {
        utx.begin();
        Movie m = readMovie(movie.getId().toString());
        
        if (m != null)
        {
            return false;
        }
  
        create(movie);
        utx.commit();
        return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }
 
    @Override
    public List<Movie> getAllMovies() 
    {

        return em.createQuery("SELECT m FROM Movie m").getResultList();
    }
    
    @Override
    public List<Movie> getAllMoviesSortAZ() 
    {

        return em.createQuery("SELECT m FROM Movie m ORDER BY m.name").getResultList();
    }
    
    @Override
    public List<Movie> getAllMoviesSortNumShowings() 
    {

        return em.createQuery("SELECT m FROM Movie m, Showing s WHERE s.movieId = m GROUP BY m ORDER BY COUNT(s) DESC").getResultList();
    }
    
    @Override
    public List<Movie> getAllMoviesSortNumShowings(int limit) 
    {

        Query query = em.createQuery("SELECT m FROM Movie m, Showing s WHERE s.movieId = m GROUP BY m ORDER BY COUNT(s) DESC LIMIT :limit");
        query.setParameter("limit", limit);
        return query.getResultList();
    }

    @Override
    @RolesAllowed({"Admin", "Employee"})
    public boolean addShowing(Showing showing) {
        try 
        {
            utx.begin();
            //instead of this, check if there is a showing with the same
            //movie, time, etc
            Showing s = readShowing("0");

            if (s != null)
            {
                return false;
            }

            showing.setId(null);

            create(showing);
            utx.commit();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

    @Override
    @RolesAllowed({"Admin"})
    public boolean addTicketType(TicketType tickettype) 
    {
        try 
        {
            utx.begin();
            TicketType t = readTicketType("0");

            if (t != null)
            {
                return false;
            }

            tickettype.setId(null);
            create(tickettype);

            utx.commit();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean addUser(Users users) {
        try 
        {
            System.out.println(users.getPassword());
            utx.begin();
            create(users);
            
            utx.commit();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public List<Showing> getCurrentShowings() 
    {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.now();
            Date date = Date.valueOf(localDate);

        return em.createQuery("SELECT s FROM Showing s WHERE s.startDate >= '" + date.toString() + "' ORDER BY s.startDate, s.startTime").getResultList();
    }

    @Override
    @RolesAllowed({"Admin"})
    public Users getUserByEmail(String email) 
    {
        List<Users> return_users = em.createQuery("SELECT u FROM Users u WHERE u.email = '" + email + "'").getResultList();
        if (return_users.size() > 0)
        {
            return return_users.get(0);
        }
        else 
        {
            return null;
        }
    }

    @Override
    public boolean addGroupMapping(GroupMapping groupmapping) 
    {
        try 
        {
            utx.begin();    
            GroupMapping mapping = this.readGroupMapping(groupmapping.getEmail().toString());

            if (mapping != null)
            {
                return false;
            }

            create(groupmapping);
            utx.commit();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Integer showingNumBookings(String showing_id) {
        Showing showingId = this.readShowing(showing_id);
        Query query = em.createQuery("SELECT SUM(b.numTickets) FROM Booking b WHERE b.showingId = :showingId");
        query.setParameter("showingId", showingId);

        try 
        {
            if (!query.getSingleResult().toString().equals("0"))
            {
                return Integer.parseInt(query.getSingleResult().toString());
            }
            else 
            {
                return showingId.getCinemaId().getNumSeats();
            }
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    @Override
    public List<TicketType> getAllTicketTypes() {
        return em.createQuery("SELECT t FROM TicketType t").getResultList();
    }

    @Override
    public List<Showing> getAllShowings() {
        return em.createQuery("SELECT s FROM Showing s").getResultList();
    }

    @Override
    @RolesAllowed({"Admin"})
    public List<Users> getAllUsers() {
        return em.createQuery("SELECT u FROM Users u").getResultList();
    }
    
    @Override
    @RolesAllowed({"Admin"})
    public List<Users> getAllUsers(String query_string) 
    {
        Query query = em.createQuery("SELECT u FROM Users u WHERE u.name LIKE :user_attr OR u.email LIKE :user_attr");
        query.setParameter("user_attr", "%" + query_string + "%");
        
        return query.getResultList();
    }

    @Override
    @RolesAllowed({"Admin"})
    public List<Users> getUsersByGroupType(String user_type) 
    {
        Query query = em.createQuery("SELECT u FROM Users u, GroupMapping g WHERE u.groupMapping = g AND g.groupName = :usertype");
        query.setParameter("usertype", user_type.trim());
        
        System.out.println("SIZE: " + query.getResultList().size());
        
        return query.getResultList();
    }
    
    @Override
    @RolesAllowed({"Admin"})
    public List<Users> getUsersByGroupType(String user_type, String query_string) 
    {
        Query query = em.createQuery("SELECT u FROM Users u, GroupMapping g WHERE u.groupMapping = g AND g.groupName = :usertype AND (u.name LIKE :user_attr OR u.email LIKE :user_attr)");
        query.setParameter("usertype", user_type.trim());
        query.setParameter("user_attr", "%" + query_string + "%");
        System.out.println("SIZE: " + query.getResultList().size());
        
        return query.getResultList();
    }

    @Override
    public boolean addBooking(Booking booking_entity) 
    {
        try 
        {
            utx.begin();  
            create(booking_entity);
            utx.commit();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

    @Override
    @RolesAllowed({"Admin", "Employee"})
    public boolean addReservedBooking(Booking booking_entity, ReservedBooking reservation_entity) 
    {
        if (this.readBooking(booking_entity.getId().toString()) != null)
        {
            return false;
        }
        
        try 
        {
            utx.begin();
            
            create(booking_entity);
            
            Integer booking_id = Integer.parseInt(em.createQuery("SELECT b.id FROM Booking b ORDER BY b.id DESC").getResultList().get(0).toString());
            System.out.println(booking_id.toString());
            
            reservation_entity.setBookingId(this.readBooking(booking_id.toString()));
            create(reservation_entity);
    
            utx.commit();
        } 
        catch (Exception ex) 
        {
            System.out.println(ex);
        }
        
        return true;
    }

    @Override
    public List<ReservedBooking> getAllReservedBookings() 
    { 
        return em.createQuery("SELECT r FROM ReservedBooking r").getResultList();
    }

    @Override
    public Users getUserByID(String id) {
        List<Users> return_users = em.createQuery("SELECT u FROM Users u WHERE u.id = '" + id + "'").getResultList();
        if (return_users.size() > 0)
        {
            return return_users.get(0);
        }
        else 
        {
            return null;
        }
    }

    @Override
    @RolesAllowed({"Admin", "Employee"})
    public ReservedBooking readReservation(String id) 
    {
        return em.find(ReservedBooking.class, Integer.parseInt(id));
    }

    @Override
    public List<Booking> getAllBookings() 
    {
        return em.createQuery("SELECT b FROM Booking b").getResultList();
    }

    @Override
    public List<Showing> getShowingsForGivenTime(String time_frame) 
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);

        switch (time_frame)
        {
            case "past":
                return em.createQuery("SELECT s FROM Showing s WHERE s.startDate < '" + 
                        date.toString() + "' ORDER BY s.startDate, s.startTime").getResultList();

            case "future":
                return em.createQuery("SELECT s FROM Showing s WHERE s.startDate > '" + 
                        date.toString() + "' ORDER BY s.startDate, s.startTime").getResultList();
                
            case "today":
                return em.createQuery("SELECT s FROM Showing s WHERE s.startDate = '" + 
                        date.toString() + "' ORDER BY s.startDate, s.startTime").getResultList();
                
            default:
                return em.createQuery("SELECT s FROM Showing s").getResultList();
        }
    }

    @Override
    public List<Booking> getBookingsByUserEmail(String email) {
        Query query = em.createQuery("SELECT b FROM Booking b WHERE b.userEmail :userEmail");
        query.setParameter("userEmail", this.readUser(email));
        return query.getResultList();
    }

    @Override
    public List<Movie> getMovieByName(String film_name) 
    {
        Query query = em.createQuery("SELECT m FROM Movie m WHERE m.name LIKE :film_name");
        query.setParameter("film_name", "%" + film_name + "%");
        
        return query.getResultList();
    }
}
   
