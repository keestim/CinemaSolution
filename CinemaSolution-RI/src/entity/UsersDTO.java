/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author KeesTim
 * DTO object that represents the Users entity create from the solution's database
 */

//same fields as entity that it represents, with any entity type field being changed to DTO type
public class UsersDTO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
    private GroupMappingDTO group_mapping;

    public UsersDTO(Integer id, String name, String email, String password, String address, String phone, 
            GroupMappingDTO group_mapping) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.group_mapping = group_mapping;
    }

    public UsersDTO(String name, String email, String password, String address, String phone, GroupMappingDTO group_mapping) 
    {
        this.id = null;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.group_mapping = group_mapping;
    }
    
    public UsersDTO(Integer id, String name, String email, String password, String address, String phone, 
        String group_mapping) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.group_mapping = new GroupMappingDTO(null, group_mapping);
    }

    public UsersDTO(String name, String email, String password, String address, String phone, String group_mapping) 
    {
        this.id = 0;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.group_mapping = new GroupMappingDTO(null, group_mapping);
    }

    //getters for all of the class's private fields
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GroupMappingDTO getGroup_mapping() {
        return group_mapping;
    }
    
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
    
    
}
