package converters;


import entity.MovieDTO;
import entity.TicketTypeDTO;
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

/**
 *
 * @author timke
 */
//converter class for the tickettypedto
@FacesConverter("TicketTypeConverter")
public class TicketTypeConverter implements Converter
{
    //gives class access to solutionManagedBean, which is a stateless bean
    SolutionManagedBeanRemote solutionManagedBean = lookupSolutionManagedBeanRemote();
    
    //returns the DTO that represents a given ticket type db entry, from an input id
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        return (TicketTypeDTO)solutionManagedBean.getTicketTypeByID(value.toString());
    }

    //returns the id for a given database ticket type entry
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        TicketTypeDTO tickettype = (TicketTypeDTO) value;
        return tickettype.getId().toString();
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

