/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.BookingDTO;
import entity.ReservedBookingDTO;
import entity.ShowingDTO;
import entity.TicketTypeDTO;
import entity.UsersDTO;
import java.io.Serializable;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import session.BookingBeanRemote;
import session.SolutionManagedBeanRemote;

/**
 *
 * @author KeesTim
 */
@Named(value = "bookingManagedBean")
@SessionScoped
public class bookingManagedBean implements Serializable
{
    @EJB
    private BookingBeanRemote bookingBean;
    @EJB
    private SolutionManagedBeanRemote solutionManagedBean;
    
    @Inject
    private Conversation conversation;
    
    //very similar fields to MovieDTO class
    public bookingManagedBean()
    {
        this.id = null;
        this.paid = false;
        this.ticket_type = null;
        this.showing = null;
        this.user = null;
        this.output_value = null;
        this.num_tickets = 0;
        this.price = 0.0;
    }
    
    private Integer id;
    private Boolean paid;    
    private String output_value;
    
    private String paid_input;


    private Integer num_tickets;
    
    private TicketTypeDTO ticket_type;
    private ShowingDTO showing;
    private UsersDTO user;
    
    private double price;
    
    private String reservation_name;
    private String reservation_phone;

    //setters and getter for all of the class's private fields
    public String getReservation_name() {
        return reservation_name;
    }

    public void setReservation_name(String reservation_name) {
        this.reservation_name = reservation_name;
    }

    public String getReservation_phone() {
        return reservation_phone;
    }

    public void setReservation_phone(String reservation_phone) {
        this.reservation_phone = reservation_phone;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public String getOutput_value() {
        return output_value;
    }
    
    public String getPaid_input() {
        return paid_input;
    }

    public void setPaid_input(String paid_input) {
        this.paid_input = paid_input;
    }
    
    public void setOutput_value(String output_value) {
        this.output_value = output_value;
    }

    public TicketTypeDTO getTicket_type() {
        return ticket_type;
    }

    public void setTicket_type(TicketTypeDTO ticket_type) {
        this.ticket_type = ticket_type;
    }

    public ShowingDTO getShowing() {
        return showing;
    }

    public Integer getNum_tickets() {
        return num_tickets;
    }

    public void setNum_tickets(Integer num_tickets) {
        this.num_tickets = num_tickets;
    }

    public void setShowing(ShowingDTO showing) {
        this.showing = showing;
    }

    public UsersDTO getUser() {
        return user;
    }

    public void setUser(UsersDTO user) {
        this.user = user;
    }
    
    public void startConversation() {
        conversation.begin();
    }

    public void endConversation() {
        conversation.end();
    }
    
    public void setPrice()
    {
        System.out.println("Test");
        System.out.println(this.price);
        this.price = this.ticket_type.getPrice() * this.num_tickets;
        System.out.println(this.price);
        this.price = (double)Math.round(price * 100)/100;
        System.out.println(this.price);
    }
    
    public double getPrice()
    {
        return this.price;
    }
    
    //used for display the current price of the booking in real time to the user
    public void getCurrentPrice(FacesContext fc, UIComponent component, Object value)
    { 
        TicketTypeDTO ticket_type = (TicketTypeDTO) value;
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //gets the number of tickets values for the form field
        Integer form_number_tickets = Integer.parseInt(request.getParameter("booking_form:num_tickets"));
        
        if (this.showing != null)
        {
            Double current_price = ticket_type.getPrice() * form_number_tickets;
            this.price = current_price;
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage("Total Price: $" + (double)Math.round(current_price * 100)/100));   
        }
    }
    
    //used for display the current price of the booking in real time to the user
    public void getNumTickets(FacesContext fc, UIComponent component, Object value)
    {
        Integer form_number_tickets = Integer.parseInt(value.toString());
        
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        String url = request.getRequestURL().toString();
        System.out.println(url);
        
        //use this return values to ticket type dto by id!        
        //gets the ticket type input dto
        TicketTypeDTO ticket = solutionManagedBean.getTicketTypeByID(request.getParameter("booking_form:ticket"));
        if (this.showing != null)
        {
            Double current_price = form_number_tickets * ticket.getPrice();
            this.price = current_price;
            FacesContext.getCurrentInstance().addMessage (null, 
                new FacesMessage("Total Price: $: " + (double)Math.round(current_price * 100)/100));
        }
    }

    public BookingBeanRemote getBookingBean() {
        return bookingBean;
    }

    public SolutionManagedBeanRemote getSolutionManagedBean() {
        return solutionManagedBean;
    }
    
    //adds a entry to the database, from the values in an object created from this type
    public String addUserBooking()
    {
        Double current_price = this.num_tickets * this.ticket_type.getPrice();
        current_price = (double)Math.round(current_price * 100)/100;
                
        BookingDTO booking = new BookingDTO(0, false, this.ticket_type, this.showing, this.num_tickets, current_price);
        
        //removes FacesMessages from pages once addBooking Method is run
        //https://stackoverflow.com/questions/8215720/faces-messages-are-not-cleared-on-subsequent-requests
        Iterator<FacesMessage> msgIterator = FacesContext.getCurrentInstance().getMessages();
        while (msgIterator.hasNext())
        {
            FacesMessage facesMessage = msgIterator.next();
            msgIterator.remove();
        }
      
        try 
        {
            bookingBean.AddBooking(booking);
        }
        catch (Exception e)
        {
            return "failure";
        }

        return "success";
    }
    
    //imports data from an input dto
    public void importBookingBeanDTO(BookingDTO dto)
    {
        this.paid = false;
        this.id = dto.getId();
        this.paid = dto.getPaid();
        this.output_value = "";
        this.paid_input = "";
        this.num_tickets = dto.getNumTickets();
        this.ticket_type = dto.getTicket_type();
        this.showing = dto.getShowing();
        this.user = dto.getUser();
        this.price = dto.getTotalPrice();
    }
    
    //imports data from booking bean
    public void importBookingBeanDTO()
    {
        this.paid = false;
        BookingDTO import_dto = this.bookingBean.getFirstBooking();
        
        //this.id = import_dto.getId();
        this.showing = import_dto.getShowing();
        this.ticket_type = import_dto.getTicket_type();
        this.num_tickets = import_dto.getNumTickets();
        this.paid = import_dto.getPaid();
        this.user = import_dto.getUser();
        this.output_value = import_dto.getTicket_type().getTypeName();
        
        this.price = import_dto.getTotalPrice();
        this.setPrice();
    }
    
    //imports data from an input dto
    public void importReservationBeanDTO(ReservedBookingDTO dto)
    {
        System.out.println(dto.getBooking().getPaid());
        this.importBookingBeanDTO(dto.getBooking());
        this.reservation_name = dto.getReservationName();
        this.reservation_phone = dto.getReservationPhone();
        this.id = dto.getId();
    }
    
    //imports data from booking bean
    public void importReservationBeanDTO()
    {
        this.paid = false;
        BookingDTO import_dto = this.bookingBean.getFirstBooking();
        
        this.id = import_dto.getId();
        this.showing = import_dto.getShowing();
        this.ticket_type = import_dto.getTicket_type();
        this.num_tickets = import_dto.getNumTickets();
        this.paid = import_dto.getPaid();
        this.user = null;
        this.output_value = import_dto.getTicket_type().getTypeName();
        
        this.price = import_dto.getTotalPrice();
        this.setPrice();
    }
    
    //completes the booking, either adding booking to database, for users that want to pay later
    //or sends users that want to pay online, to the online payment page
    public String completeBooking()
    {
        this.paid = false;
        BookingDTO import_dto = this.bookingBean.getFirstBooking();
        
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();       
        //WILL ONLY WORK IF ITS LOGGED IN!!!!!
        String email = request.getRemoteUser();
        
        this.paid = false;
        if (this.paid_input.trim().contains("true"))
        {
            System.out.println("PAY NOW");
            return "pay now";
        }
        else 
        {
            //add usr id, once implemented!
            BookingDTO submit_dto = new BookingDTO(0, this.paid, 
                    import_dto.getTicket_type(), 
                    import_dto.getShowing(), 
                    solutionManagedBean.getUserByEmail(email),
                    import_dto.getNumTickets(), import_dto.getTotalPrice());

            boolean result = solutionManagedBean.addBooking(submit_dto);

            if (result)
            {
                this.bookingBean.removeFirstBooking();
                MailActions a = new MailActions();
                String body = this.showing.dateString() + " " + this.showing.timeString() + '\n' + this.showing.getMovieId().getName();
                a.sendMail(email, "Cinema Booking Successful", body);

                return "pay later";
            }
            else 
            {
                return "failure";    
            }
        }
    }
    
    public String payForBooking()
    {
        BookingDTO import_dto = this.bookingBean.getFirstBooking();
        
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();       
        //WILL ONLY WORK IF ITS LOGGED IN!!!!!
        String email = request.getRemoteUser();
        
                    //add usr id, once implemented!
            BookingDTO submit_dto = new BookingDTO(0, true, 
                    import_dto.getTicket_type(), 
                    import_dto.getShowing(), 
                    solutionManagedBean.getUserByEmail(email),
                    import_dto.getNumTickets(), import_dto.getTotalPrice());

            boolean result = solutionManagedBean.addBooking(submit_dto);

            if (result)
            {
                this.bookingBean.removeFirstBooking();
                MailActions a = new MailActions();
                String body = this.showing.dateString() + " " + this.showing.timeString() + '\n' + this.showing.getMovieId().getName();
                a.sendMail(email, "Cinema Booking Successful", body);

                return "success";
            }
            else 
            {
                return "failure";    
            }
    }
    
    //users are only able to view a ticket if
    //they have paid for it and it is their ticket
    public boolean ableToView()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();       
        //WILL ONLY WORK IF ITS LOGGED IN!!!!!
        String email = request.getRemoteUser();
        
        //checks if email address for ticket is the same as the currently logged in users address
        return (email.trim().contains(this.user.getEmail().trim()) && this.paid);
    }
    
    //creates appropiate objects with class field and then submits them to the database
    public String completeReservation()
    {
        BookingDTO import_dto = this.getBookingBean().getFirstBooking();
        
        //add usr id, once implemented!
        
        BookingDTO submit_dto = new BookingDTO(0, false, 
                import_dto.getTicket_type(), 
                import_dto.getShowing(), 
                import_dto.getNumTickets(), import_dto.getTotalPrice());
        
        ReservedBookingDTO reservation = new ReservedBookingDTO(0, this.reservation_name, this.reservation_phone, null);
        
        boolean result = this.getSolutionManagedBean().addReservedBooking(submit_dto, reservation);
        
        if (result)
        {
            this.getBookingBean().removeFirstBooking();
            
            return "success";
        }
        else 
        {
            return "failure";    
        }   
    }
    
    //deletes booking, which has an id that matches the id field
    public String deleteBooking()
    {
        Boolean result = solutionManagedBean.deleteBooking(this.id.toString());
        if (result)
        {
            return "success";
        }
        else 
        {
            return "failure";
        }
    }
    
    //deletes reservation, which has an id that matches the id field
    public String deleteReservation()
    {
        Boolean result = solutionManagedBean.deleteReservation(this.id.toString());
        if (result)
        {
            return "success";
        }
        else 
        {
            return "failure";
        }
    }
    
    //updates entry in database, by id, from the current field data
    public String updateReservation()
    {
        ReservedBookingDTO reservation_source = solutionManagedBean.getReservedBookingByID(this.id.toString());
                
        BookingDTO booking_source = reservation_source.getBooking();
        BookingDTO booking_submit = new BookingDTO(booking_source.getId(), this.paid, booking_source.getTicket_type(),
        booking_source.getShowing(), null, booking_source.getNumTickets(), booking_source.getTotalPrice());
        
        ReservedBookingDTO submission_dto = new ReservedBookingDTO(reservation_source.getId(), 
            this.reservation_name,
            this.reservation_phone, 
            booking_submit);
        
        if (solutionManagedBean.updateReservation(submission_dto))
        {
            return "success";
        }
        else
        {
            return "failure";
        }
    }
    
    //updates entry in database, by id, from the current field data
    public String updateBooking()
    {
        BookingDTO booking_source = solutionManagedBean.getBookingByID(this.id.toString());
        BookingDTO booking_submit = new BookingDTO(booking_source.getId(), this.paid, booking_source.getTicket_type(),
        booking_source.getShowing(), booking_source.getUser(), booking_source.getNumTickets(), booking_source.getTotalPrice());
        
        if (solutionManagedBean.updateBooking(booking_submit))
        {
            return "success";
        }
        else
        {
            return "failure";
        }
    }
}
