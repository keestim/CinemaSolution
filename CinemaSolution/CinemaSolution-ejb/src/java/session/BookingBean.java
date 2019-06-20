/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.BookingDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author KeesTim
 * 
 * Stateful EJB used for storing user bookings whilst they are in the booking process
 */
@Stateful
@SessionScoped
public class BookingBean implements BookingBeanRemote 
{
     private List<BookingDTO> bookings;
     
     //initializes bookings field with new list, when object is created form class
     public BookingBean()
     {
         bookings = new ArrayList();
     }
     
     //if the bookingdto dones't already exist in the bookings list, it is added
     @Override
     public void AddBooking(BookingDTO booking)
     {
         if (!bookings.contains(booking))
         {
            bookings.add(booking);
         }
     }
     
     //input booking is removed from the list
     @Override
     public void RemoveBooking(BookingDTO booking)
     {
         bookings.remove(booking);
     }
     
     
     //returns the list of bookings
     @Override
     public List<BookingDTO> GetBookings()
     {
         return bookings;
     }

     //gets the first booking
    @Override
    public BookingDTO getFirstBooking() 
    {
        if (bookings.size() > 0)
        {
            return bookings.get(bookings.size() - 1);
        }
        else
        {
            return null;
        }
    }

    //removes the booking from the start of the lsit
    @Override
    public void removeFirstBooking() 
    {
        bookings.remove(bookings.get(bookings.size() - 1));
    }
}
