package converters;


import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KeesTim
 */
@Named(value = "loginActions")
@SessionScoped
public class LoginActions implements Serializable
{
    private static final String LOGOUT = "logout";
    
    public LoginActions()
    {
    
    }
    
    //MUST EDIT AND MODIFY!
    //TAKEN FROM ED-SECURE PROJ
    
    //logs out user
    public String logoutResult() {
        // terminate the session by invalidating the session context
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        try 
        {
            //logs out current user
            request.logout();
            
            System.out.print(request.getRemoteUser() + " logging out!");
        }
        catch (Exception ex) 
        {
            // do nothing
            System.out.println("LOG OUT ...");
        }
        // terminate the session by invalidating the session context
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        // terminate the user's login credentials
        return LOGOUT;
    }
    
    //depending on the user type of the currently logged in user, a given user will be redirected to their appropiate page
    public String return2MainMenu()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        
        //if user is in admin role
        if (request.isUserInRole("Admin"))
        {
            return "../admin/mainmenu.xhtml";
        }
        else if (request.isUserInRole("Employee"))
        {
            //if user is in employee role
            return "../employee/mainmenu.xhtml";
        }
        else if (request.isUserInRole("User"))
        {
            //if user is in user role
            return "../user/mainmenu.xhtml";
        }
        else 
        {
            //there is no logged in user
            return "http://localhost:8080/CinemaSolution-war/faces/";
        }
    }    
    
        public void CheckUserRole() throws IOException
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        
        if (request.isUserInRole("Admin"))
        {
            System.out.println("User is ADMIN");
            fc.getExternalContext().redirect("../admin/mainmenu.xhtml");
        }
        
        if (request.isUserInRole("User"))
        {
            System.out.println("User is USER");
            fc.getExternalContext().redirect("../user/mainmenu.xhtml");
        }
        
        if (request.isUserInRole("Employee"))
        {
            System.out.println("User is EMPLOYEE");
            fc.getExternalContext().redirect("../employee/mainmenu.xhtml");
        }
    }
}
