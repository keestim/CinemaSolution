/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author KeesTim
 * DTO object that represents the Movie entity create from the solution's database
 */

//same fields as entity that it represents, with any entity type field being changed to DTO type
public class BookingDTO 
{
    private Integer id;
    private Boolean paid;
    private int numTickets;
    private TicketTypeDTO ticket_type;
    private ShowingDTO showing;
    private UsersDTO user;
    private double totalPrice;
    
    public BookingDTO(Integer id, Boolean paid, TicketTypeDTO ticket_type, ShowingDTO showing, 
            UsersDTO user, int num_tickets, double price) 
    {
        this.id = id;
        this.paid = paid;
        this.numTickets = num_tickets;
        this.ticket_type = ticket_type;
        this.showing = showing;
        this.user = user;
        this.totalPrice = price;
    }
    
    public BookingDTO(Integer id, Boolean paid, TicketTypeDTO ticket_type, ShowingDTO showing, int num_tickets, double price) 
    {
        this.id = id;
        this.paid = paid;
        this.numTickets = num_tickets;
        this.ticket_type = ticket_type;
        this.showing = showing;
        this.user = null;
        this.totalPrice = price;
    }
    
    //getters for all of the class's private fields
    public Integer getId() {
        return id;
    }

    public Boolean getPaid() {
        return paid;
    }

    public TicketTypeDTO getTicket_type() {
        return ticket_type;
    }

    public ShowingDTO getShowing() {
        return showing;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public UsersDTO getUser() {
        return user;
    }

    //method checks if an input object is equal an object created from this class
    public boolean equals(Object obj)
    {
        if (this == obj)
        return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BookingDTO other = (BookingDTO) obj;
        if (this.id != other.id)
            return false;
        return true;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public void setNumTickets(int numTickets) {
        this.numTickets = numTickets;
    }
    
    
}
