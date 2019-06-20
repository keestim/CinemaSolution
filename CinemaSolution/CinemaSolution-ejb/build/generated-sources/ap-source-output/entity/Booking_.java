package entity;

import entity.Showing;
import entity.TicketType;
import entity.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-14T13:35:31")
@StaticMetamodel(Booking.class)
public class Booking_ { 

    public static volatile SingularAttribute<Booking, Integer> numTickets;
    public static volatile SingularAttribute<Booking, Double> totalPrice;
    public static volatile SingularAttribute<Booking, Showing> showingId;
    public static volatile SingularAttribute<Booking, Boolean> paid;
    public static volatile SingularAttribute<Booking, TicketType> ticketType;
    public static volatile SingularAttribute<Booking, Users> userEmail;
    public static volatile SingularAttribute<Booking, Integer> id;

}