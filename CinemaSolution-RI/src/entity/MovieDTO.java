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
public class MovieDTO {
    private Integer id;
    private String name;
    private double runTime;
    private String rating;
    private String description;

    public MovieDTO()
    {
    
    }
    
    public MovieDTO(Integer id)
    {
        this.id = id;
    }
    
    public MovieDTO(String name, double runTime, String rating, String description) {
        this.id = null;
        this.name = name;
        this.runTime = runTime;
        this.rating = rating;
        this.description = description;
    }

    public MovieDTO(Integer id, String name, double runTime, String rating, String description) {
        this.id = id;
        this.name = name;
        this.runTime = runTime;
        this.rating = rating;
        this.description = description;
    }

    //getters for all of the class's private fields
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRunTime() {
        return runTime;
    }

    public String getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
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
        MovieDTO other = (MovieDTO) obj;
        if (this.id != other.id)
            return false;
        return true;
    }
}
