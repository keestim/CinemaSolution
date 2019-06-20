package entity;

import entity.Booking;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-14T13:35:31")
@StaticMetamodel(TicketType.class)
public class TicketType_ { 

    public static volatile SingularAttribute<TicketType, Double> price;
    public static volatile SingularAttribute<TicketType, String> typeName;
    public static volatile SingularAttribute<TicketType, Integer> id;
    public static volatile CollectionAttribute<TicketType, Booking> bookingCollection;

}