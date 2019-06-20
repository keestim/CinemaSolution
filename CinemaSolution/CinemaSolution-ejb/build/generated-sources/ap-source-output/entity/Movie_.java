package entity;

import entity.Showing;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-14T13:35:31")
@StaticMetamodel(Movie.class)
public class Movie_ { 

    public static volatile SingularAttribute<Movie, String> name;
    public static volatile SingularAttribute<Movie, String> rating;
    public static volatile SingularAttribute<Movie, String> description;
    public static volatile CollectionAttribute<Movie, Showing> showingCollection;
    public static volatile SingularAttribute<Movie, Integer> id;
    public static volatile SingularAttribute<Movie, Double> runTime;

}