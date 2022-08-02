/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package hotdesk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class OfficeTest {
    
    //Fixture declarations
    Address address;
    Office myOffice;
    OfficeClosure officeClosureDate;
    User organiser;
    User guest;    
    User guest2;
    User guest3;
    Room room;
    Room room2;
    
    SimpleDateFormat formatter;
    Date requiredDate;
    
    public OfficeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws ParseException {
        
        //Fixture setup
        address = new Address("Main St", "Big Town", "Small Country", "BTX XXX");
        myOffice = new Office("Workplace", address);
        organiser = new User("Fergal", "fergal@test.com");
        guest = new User("Fergal", "fergal@test.com");
        
        //For testing purposes, rooms have smaller desk capacity
        room = new Room(1, "Support Room", 2, myOffice);
        room2 = new Room(2, "Dev Room", 1, myOffice);    
        
        //Formatter needed to remove the timestamp from Date
        formatter = new SimpleDateFormat("yyyy-MM-dd");
               
        //Setup requiredDate between 1 -7 days in the future 
        //and is not a Saturday, Sunday or an OfficeClosure date
        LocalDate ld = LocalDate.now();
        requiredDate = formatter.parse(ld.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).toString());
        
        //Setup arbitrary Office closure date between 1 -7 days in the future
        Date closureDate = formatter.parse(ld.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)).toString());
        myOffice.closureDates.add(closureDate);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * TC1
     */
    @Test
    public void testIsBookingRequestValid_ReturnsTrue() {
        /*
        Postcondition:
        True is returned if…
        -- the guest doesn't have an existing booking for this date with status “booked”. 
        -- and existing bookings count for this room on this date with status “booked” 
           are below the rooms preset max capacity number (numberOfDesks). 
        
        … otherwise, False is returned.
        
        */
        
        //Test when user doesn't have an existing booking for this date with status “booked”.
        //and existing bookings count for this room on this date with status “booked” 
        //are below the rooms preset max capacity number (numberOfDesks).
        assertTrue(myOffice.isBookingRequestValid(guest, room, requiredDate));

    }
    
     /**
     * TC2
     * @throws ParseException
     */
    @Test
    public void testIsBookingRequestValid_ExistingBooking_ReturnsFalse() throws ParseException{

        /*
        Postcondition:
        True is returned if…
        -- the guest doesn't have an existing booking for this date with status “booked”. 
        -- and existing bookings count for this room on this date with status “booked” 
           are below the rooms preset max capacity number (numberOfDesks).
        
        … otherwise, False is returned.
        
        */
        
        //Test when user has an existing booking for this date with status “booked”.       
        Booking booking = new Booking(requiredDate, room, guest, organiser);          
        myOffice.bookings.put(booking.reference, booking);
               
        assertFalse(myOffice.isBookingRequestValid(guest, room, requiredDate));
        
        //Clear bookings for next test
        myOffice.bookings.clear();

    }

     /**
     * TC3
     * @throws ParseException
     * 
     */
    @Test
    public void testIsBookingRequestValid_RoomMaxCapacity_ReturnsFalse() throws ParseException{

        /*
        Postcondition:
        True is returned if…
        -- the guest doesn't have an existing booking for this date with status “booked”. 
        -- and existing bookings count for this room on this date with status “booked” 
           are below the rooms preset max capacity number (numberOfDesks).
        
        … otherwise, False is returned.
        
        */
        
        //Test when existing bookings count for this room on this date with status “booked” 
        //are greater than or equal to the rooms preset max capacity number (numberOfDesks).
        
        // Capacity is 2, and we will set up 2 bookings in advance, 
        // and check isBookingRequestValid for a third guest
        guest2 = new User("Joe", "joe@test.com");
        guest3 = new User("Ted", "ted@test.com");
        
        
        Booking booking = new Booking(requiredDate, room, guest, organiser);        
        myOffice.bookings.put(booking.reference, booking);
        
        Booking booking2 = new Booking(requiredDate, room, guest2, organiser); 
        myOffice.bookings.put(booking2.reference, booking2);
               
        assertFalse(myOffice.isBookingRequestValid(guest3, room, requiredDate));
        
        //Clear bookings for next test
        myOffice.bookings.clear();

    }
    
    

    /**
     * TC4
     * @throws ParseException
     */
    @Test
    public void testCreateBooking_Valid() throws ParseException {
        /*
       Postcondition:
       -- an instance of Booking is created and returned
       -- whose attributes are initialised
       -- and status is booked
       -- and date is today      
       */   
               
        Booking booking = myOffice.createBooking(requiredDate, room, guest, organiser);          

        BookingStatus expResult1 = BookingStatus.BOOKED;
        BookingStatus result1 = booking.status;
        assertEquals(expResult1, result1);
        
        Date expResult2 = formatter.parse(formatter.format(new Date()));
        Date result2 = formatter.parse(formatter.format(booking.date));
        assertEquals(expResult2, result2);
        
        
        /*
        -- and which is linked to User instance role guest
        -- and to User instance role organiser
        -- and to Room instance room  
        */
        
        User expResult3 = guest;
        User result3 = booking.guest;
        assertEquals(expResult3, result3); 
        
        User expResult4 = organiser;
        User result4 = booking.organiser;
        assertEquals(expResult4, result4); 
        
        Room expResult5 = room;
        Room result5 = booking.room;
        assertEquals(expResult5, result5); 
    }
    
    
    /**
     * TC5
     * @throws ParseException
     */
    @Test
    public void testCreateBooking_OrganiserIsAlsoGuest_Valid() throws ParseException {
        /*
       Postcondition:
       -- an instance of Booking is created and returned
       -- whose attributes are initialised
       -- and status is booked
       -- and date is today      
       */   
               
        Booking booking = myOffice.createBooking(requiredDate, room, organiser, organiser);          

        BookingStatus expResult1 = BookingStatus.BOOKED;
        BookingStatus result1 = booking.status;
        assertEquals(expResult1, result1);
        
        Date expResult2 = formatter.parse(formatter.format(new Date()));
        Date result2 = formatter.parse(formatter.format(booking.date));
        assertEquals(expResult2, result2);
        
        
        /*
        -- and which is linked to User instance role guest
        -- and to User instance role organiser
        -- and to Room instance room  
        */
        
        User expResult3 = organiser;
        User result3 = booking.guest;
        assertEquals(expResult3, result3); 
        
        User expResult4 = organiser;
        User result4 = booking.organiser;
        assertEquals(expResult4, result4); 
        
        Room expResult5 = room;
        Room result5 = booking.room;
        assertEquals(expResult5, result5); 
    }
     
}
