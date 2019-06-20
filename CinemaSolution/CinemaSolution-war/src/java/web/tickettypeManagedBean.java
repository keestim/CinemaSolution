/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.TicketTypeDTO;
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
//class deals will all user facing actions for the tickettype entity
@Named(value = "tickettypeanagedBean")
@ConversationScoped
public class tickettypeManagedBean implements Serializable
{
    //very similar fields to TicketTypeDTO class
    private Integer id;
    private String typeName;
    private double price;
    
    //field allows access to the solutionManagedBean stateless bean
    @Inject
    private Conversation conversation;
    @EJB
    private SolutionManagedBeanRemote solutionManagedBean;
    
    public tickettypeManagedBean()
    {
        this.id = null;
        this.typeName = null;
        this.price = 0.00;
    }

    //setters and getter for all of the class's private fields
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    //used for adding ticket type to the database
    public String addTicketType()
    {
        TicketTypeDTO tickettypeDTO = new TicketTypeDTO(this.id, this.typeName, this.price);
        
        //calls method in remote bean, that adds new ticket type to the database 
        if (solutionManagedBean.addTicketType(tickettypeDTO))
        {
            return "success";
        }
        else 
        {
            return "failure";
        }
    }
    
    public void startConversation() {
        conversation.begin();
    }

    public void endConversation() {
        conversation.end();
    }
    
    //copies all of the values in a input DTO to all of the class's internal fields
    public void importTicketTypeDTO(TicketTypeDTO dto)
    {
        this.id = dto.getId();
        this.price = dto.getPrice();
        this.typeName = dto.getTypeName();
    }
 
    //updates ticket type entry on database
    public String updateTicketType()
    {
        TicketTypeDTO tickettypeDTO = new TicketTypeDTO(this.id, this.typeName, this.price);
        
        //calls method on remote bean that updates db entry
        if (solutionManagedBean.updateTicketType(tickettypeDTO))
        {
            return "success";
        }
        else 
        {
            return "failure";
        }
    }
    
    //deletes user from database
    public String deleteTicketType()
    {
        if (solutionManagedBean.deleteTicketType(this.id.toString()))
        {
            return "success";
        }
        else 
        {
            return "failure";
        }
    }
    

}
