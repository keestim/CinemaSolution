/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author KeesTim
 * DTO object that represents the ReservedBooking entity create from the solution's database
 */

//same fields as entity that it represents, with any entity type field being changed to DTO type
public class ReservedBookingDTO 
{
    private Integer id;
    private String reservationName;
    private String reservationPhone;
    private BookingDTO booking;
    
    public ReservedBookingDTO(Integer id, String res_name, String res_phone, BookingDTO booking)
    {
        this.id = id;
        this.reservationName = res_name;
        this.reservationPhone = res_phone;
        this.booking = booking;
    }
    
    public ReservedBookingDTO(String res_name, String res_phone, BookingDTO booking)
    {
        this.id = id;
        this.reservationName = res_name;
        this.reservationPhone = res_phone;
        this.booking = booking;
    }

    //getters for all of the class's private fields
    public Integer getId() {
        return id;
    }

    public String getReservationName() {
        return reservationName;
    }

    public String getReservationPhone() {
        return reservationPhone;
    }

    public BookingDTO getBooking() {
        return booking;
    }
}
