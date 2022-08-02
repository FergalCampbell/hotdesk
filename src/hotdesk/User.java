/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotdesk;

import java.util.*;

public class User {
    
    // Attributes
    String name;
    String email;
    
    // Associations
    Map<Integer,Booking> bookings = new HashMap<>();
    
    // Constructor
    public User(String name, String email){
        this.name = name;
        this.email = email;
    }
}
