/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.CinemaDTO;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import session.SolutionManagedBeanRemote;

/**
 *
 * @author KeesTim
 */
@Named(value = "cinemaManagedBean")
@ConversationScoped
public class cinemaManagedBean implements Serializable
{
    @EJB
    private SolutionManagedBeanRemote solutionManagedBean;
    @Inject
    private Conversation conversation;

    private String cinemaId;
    private String numRows;
    private String isleWidth;

    public cinemaManagedBean() 
    {
        cinemaId = null;
        numRows = null;
        isleWidth = null;
    }

    public String getCinemaId() {
        return cinemaId;
    }
    
    public void importCinemaDTO(CinemaDTO dto)
    {
        this.cinemaId = dto.getCinemaId().toString();
        
        System.out.println(this.cinemaId);
        
        this.isleWidth = String.valueOf(dto.getIsleWidth());
        this.numRows = String.valueOf(dto.getNumRows());
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getNumRows() {
        return numRows;
    }

    public void setNumRows(String numRows) {
        this.numRows = numRows;
    }

    public String getIsleWidth() {
        return isleWidth;
    }

    public void setIsleWidth(String isleWidth) {
        this.isleWidth = isleWidth;
    }
    
    //used for adding a new entry to the cinema table in the database
    //entry is crated from another create from this class, create a new DTO from these fields
    public String addCinema()
    {
        CinemaDTO cinemaDTO = new CinemaDTO(Integer.parseInt(this.cinemaId), 
                Integer.parseInt(this.numRows.toString()), 
                Integer.parseInt(this.isleWidth.toString()));
        Boolean result = solutionManagedBean.addCinema(cinemaDTO);

        if (result)
        {
            return "success";
        }
        else {
            return "failure";
        }
    }
    
    public void startConversation() {
        conversation.begin();
    }

    public void endConversation() {
        conversation.end();
    }
    
    //get the total capacity (i.e. number of seats) for the given cinema
    public Integer getTotalCapacity()
    {
        return (Integer.parseInt(this.numRows) * Integer.parseInt(this.isleWidth));
    }
    
    //used for updating corresponding cinema entry in the database
    public String updateCinema()
    {
        CinemaDTO cinemaDTO = new CinemaDTO(Integer.parseInt(this.cinemaId), 
                Integer.parseInt(this.numRows.toString()), 
                Integer.parseInt(this.isleWidth.toString()));
                
        Boolean result = solutionManagedBean.updateCinema(cinemaDTO);
        
        if (result)
        {
            return "success";
        }
        else {
            return "failure";
        }
    }
    
    //used for deleting corresponding cinema entry in the database
    public String deleteCinema()
    {
        Boolean result = solutionManagedBean.deleteCinema(cinemaId);

        if (result)
        {
            return "success";
        }
        else {
            return "failure";
        }
    }
}
