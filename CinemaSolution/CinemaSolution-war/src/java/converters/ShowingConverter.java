package converters;


import entity.CinemaDTO;
import entity.ShowingDTO;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author timke
 */
//converter class for the showingdto
@FacesConverter("ShowingConverter") 
public class ShowingConverter implements Converter
{
    //gives class access to solutionManagedBean, which is a stateless bean
    SolutionManagedBeanRemote solutionManagedBean = lookupSolutionManagedBeanRemote();
    
    //returns the DTO that represents a given showing db entry, from an input id
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        return (ShowingDTO)solutionManagedBean.getShowingByID(value.toString());
    }

    //returns the id for a given database showing entry
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        ShowingDTO showing = (ShowingDTO) value;
        return showing.getId().toString();
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
