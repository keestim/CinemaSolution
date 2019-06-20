package converters;


import entity.MovieDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import session.SolutionManagedBeanRemote;

//converter class for the moviedto
@FacesConverter("MovieConverter") 
public class MovieConverter implements Converter
{
    //gives class access to solutionManagedBean, which is a stateless bean
    SolutionManagedBeanRemote solutionManagedBean = lookupSolutionManagedBeanRemote();

    //returns the DTO that represents a given movie db entry, from an input id
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
          return (MovieDTO)solutionManagedBean.getMovieByID(value.toString());
    }

    //returns the id for a given database movie entry
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        MovieDTO movie = (MovieDTO) value;
        return movie.getId().toString();
    }

    //finds the dto for database entity, remote EJB look is require, due to it's role in the solution
    private SolutionManagedBeanRemote lookupSolutionManagedBeanRemote() {
        try {
            Context c = new InitialContext();

            return (SolutionManagedBeanRemote) c.lookup("ejb/SolutionManagedBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
