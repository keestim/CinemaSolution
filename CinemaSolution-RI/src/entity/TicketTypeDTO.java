/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author KeesTim
 */
public class TicketTypeDTO {
    private Integer id;
    private String typeName;
    private double price;

    public TicketTypeDTO(Integer id, String typeName, double price) {
        this.id = id;
        this.typeName = typeName;
        this.price = price;
    }

    public TicketTypeDTO(String typeName, double price) {
        this.typeName = typeName;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public double getPrice() {
        return price;
    }
    
    public boolean equals(Object obj)
    {
        if (this == obj)
        return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TicketTypeDTO other = (TicketTypeDTO) obj;
        if (this.id != other.id)
            return false;
        return true;
    }
    
}
