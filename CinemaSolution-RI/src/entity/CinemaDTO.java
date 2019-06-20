/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author KeesTim
 * DTO object that represents the Movie entity create from the solution's database
 */

//same fields as entity that it represents, with any entity type field being changed to DTO type
public class CinemaDTO {
    private Integer cinemaId;
    private int numRows;
    private int isleWidth;

    public CinemaDTO(Integer cinemaId, int numRows, int isleWidth) {
        this.cinemaId = cinemaId;
        this.numRows = numRows;
        this.isleWidth = isleWidth;
    }
    
    public CinemaDTO(Integer id)
    {
        this.cinemaId = id;
    }

    //getters for all of the class's private fields
    public Integer getCinemaId() {
        return cinemaId;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getIsleWidth() {
        return isleWidth;
    }
   
    //method checks if an input object is equal an object created from this class
    public boolean equals(Object obj)
    {
        if (this == obj)
        return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CinemaDTO other = (CinemaDTO) obj;
        if (this.cinemaId != other.cinemaId)
            return false;
        return true;
    }
    
    
}
