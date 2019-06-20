package converters;


import entity.CinemaDTO;
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

//converter class for the cinemadto
@FacesConverter("CinemaConverter") 
public class CinemaConverter implements Converter{
 //gives class access to solutionManagedBean, which is a stateless bean
    SolutionManagedBeanRemote solutionManagedBean = lookupSolutionManagedBeanRemote();

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
    
    //returns the DTO that represents a given cinema db entry, from an input id
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
          return (CinemaDTO)solutionManagedBean.getCinemaByID(value.toString());
    }

    //returns the id for a given database cinema entry
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        CinemaDTO cinema = (CinemaDTO) value;
        return cinema.getCinemaId().toString();
    }



}
