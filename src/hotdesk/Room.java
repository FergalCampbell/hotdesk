/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotdesk;

public class Room {
    
    // Attributes
    Integer id;
    String name;
    Integer numberOfDesks;
    
    // Associations
    Office office;
    
    // Constructor
    public Room(Integer id, String name, Integer numberOfDesks, Office office){
        this.id = id;
        this.name = name;
        this.numberOfDesks = numberOfDesks;
        this.office = office;
    }
}
