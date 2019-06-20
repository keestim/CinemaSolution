package entity;

import entity.Showing;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-21T09:32:11")
@StaticMetamodel(Cinema.class)
public class Cinema_ { 

    public static volatile SingularAttribute<Cinema, Integer> numRows;
    public static volatile SingularAttribute<Cinema, Integer> cinemaId;
    public static volatile CollectionAttribute<Cinema, Showing> showingCollection;
    public static volatile SingularAttribute<Cinema, Integer> isleWidth;

}