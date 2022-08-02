/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hotdesk;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Booking {
    
    // Attributes
    Integer reference;
    Date date;
    Date requiredDate;
    BookingStatus status;
    
    // Associations
    Room room;
    User guest;
    User organiser;
    
    // Constructor
    public Booking(Date requiredDate, Room room, User guest, User organiser) throws ParseException{
        
        /* 
            Invariant: true
            Precondition: true        
        */
        
        /*
            In a complete system the reference would be set by an internal mechanism
            possibly driven by the max reference in the Database + 1
            For demo purposes this will be set as a random integer
        */
        int randomNum = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        this.reference  = randomNum;
        
        //formatter needed to remove the timestamp from todays Date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
        Date todayDate = formatter.parse(formatter.format(new Date()));
        
        this.date = todayDate;
        this.requiredDate = requiredDate;
        this.status = BookingStatus.BOOKED;    
        this.room = room;
        this.guest = guest;
        this.organiser = organiser;
        
               
        /*
            Postcondition:
            -- an instance of Booking is created and returned
            -- whose attributes are initialised
            -- and status is booked
            -- and date is today
            -- and which is linked to User instance role guest
            -- and to User instance role organiser
            -- and to Room instance room
        */
    }
}
