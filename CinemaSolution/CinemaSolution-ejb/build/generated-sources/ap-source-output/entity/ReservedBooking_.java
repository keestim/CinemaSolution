package entity;

import entity.Booking;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-14T13:35:31")
@StaticMetamodel(ReservedBooking.class)
public class ReservedBooking_ { 

    public static volatile SingularAttribute<ReservedBooking, String> reservationPhone;
    public static volatile SingularAttribute<ReservedBooking, Integer> id;
    public static volatile SingularAttribute<ReservedBooking, String> reservationName;
    public static volatile SingularAttribute<ReservedBooking, Booking> bookingId;

}