package entity;

import entity.Booking;
import entity.Cinema;
import entity.Movie;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-14T13:35:31")
@StaticMetamodel(Showing.class)
public class Showing_ { 

    public static volatile SingularAttribute<Showing, Cinema> cinemaId;
    public static volatile SingularAttribute<Showing, Date> startTime;
    public static volatile SingularAttribute<Showing, Movie> movieId;
    public static volatile SingularAttribute<Showing, Integer> id;
    public static volatile CollectionAttribute<Showing, Booking> bookingCollection;
    public static volatile SingularAttribute<Showing, Date> startDate;

}