/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.BookingDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author KeesTim
 */

/*
REMOTE INTERFACE OF EJB BOOKINGBEAN
*/

@Remote
public interface BookingBeanRemote 
{    
    public void AddBooking(BookingDTO booking); 
     
    public void RemoveBooking(BookingDTO booking);
    
    public List<BookingDTO> GetBookings();     

    BookingDTO getFirstBooking();

    void removeFirstBooking();
}
