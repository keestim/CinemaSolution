/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author timke
 * DTO object that represents the TicketType entity create from the solution's database
 */

//same fields as entity that it represents, with any entity type field being changed to DTO type
public class GroupMappingDTO {
    private String email;
    private String groupName;
    
    public GroupMappingDTO(String email, String groupName)
    {
        this.email = email;
        this.groupName = groupName;
    }
    
    public GroupMappingDTO(String groupName)
    {
        this.groupName = groupName;
    }

    //getters for all of the class's private fields
    public String getEmail() {
        return email;
    }

    public String getGroupName() {
        return groupName;
    }
}
