/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotdesk;

import java.text.ParseException;
import java.util.*;

public class Office {
    
    // Attributes    
    String name; 
    Address address;
    
    // Associations
    //P6 ‘Program to interfaces, not implementations’
    Map<Integer,Booking> bookings = new HashMap<>();
    Map<Integer,Room> rooms = new HashMap<>();
    
    //Use HashSet for associations not identified uniquely among all others by its value identity    
    Set<Date> closureDates = new HashSet<>();
    
    // Constructor
    public Office(String name, Address address){
        this.name = name;
        this.address = address;
    }
    
    // Methods
    Boolean isBookingRequestValid(User guest, Room room, Date requiredDate) {
        /*
        Invariant: true
        Precondition:
        -- the requiredDate is between 1 and 7 days in the future and is not a Saturday, Sunday or an OfficeClosure date
        Postcondition:
        True is returned if…
        -- the guest doesn't have an existing booking for this date with status “booked”.
        -- and existing bookings count for this room on this date with status “booked” are below the rooms preset max capacity number (numberOfDesks).
        … otherwise, False is returned.
         */        
            
        return !doesBookingExist(guest, requiredDate) && !isRoomFullyBooked(room, requiredDate);     
        
    }
    
    Boolean doesBookingExist(User guest, Date requiredDate) {
        for (Booking b : bookings.values()) {
            if (b.guest == guest && b.requiredDate == requiredDate && b.status == BookingStatus.BOOKED) {     
                return true;                
            }
        }
        
        return false;
            
        /*
            Invariant: true
            Precondition:
            -- the requiredDate is between 1 and 7 days in the future and is not a Saturday, Sunday or an OfficeClosure date
            Postcondition:
            True is returned if…
            -- the guest has an existing booking for this date with status “booked”.
            … otherwise, False is returned.
         */
                
    }
    
    Boolean isRoomFullyBooked(Room room, Date requiredDate) {
        
        var roomBookingCount = 0;
        
        for (Booking b : bookings.values()) {
            if (Objects.equals(b.room.id, room.id) && b.requiredDate == requiredDate && b.status == BookingStatus.BOOKED) {                
                roomBookingCount++;                
            }
        }      
                    
        return roomBookingCount >= room.numberOfDesks;
        
        /*
        Invariant: true
        Precondition:
        -- the requiredDate is between 1 and 7 days in the future and is not a Saturday, Sunday or an OfficeClosure date
        Postcondition:
        True is returned if…
        -- the existing bookings count for this room on this date with status “booked” are greater than or equal to the rooms preset max capacity number (numberOfDesks).
        … otherwise, False is returned.

         */
    }
    
    

    Booking createBooking(Date requiredDate, Room room, User guest, User organiser) throws ParseException{
        
        /*
            Invariant: true
            Precondition:
            -- the requiredDate is between 1 and 7 days in the future and is not a Saturday, Sunday or an OfficeClosure date
            -- and Booking request is valid (isBookingRequestValid returns true)
            Postcondition:
            -- an instance of Booking is created and returned
            -- whose attributes are initialised
            -- and status is booked
            -- and date is today
            -- and which is linked to User instance role guest
            -- and to User instance role organiser
            -- and to Room instance room
            -- and to self
        */
               
        Booking booking = new Booking(requiredDate, room, guest, organiser);   
        
        bookings.put(booking.reference, booking);
        
        return booking;

    }

}