/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.MovieDTO;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import session.SolutionManagedBeanRemote;

/**
 *
 * @author KeesTim
 */
@Named(value = "movieManagedBean")
@ConversationScoped
public class movieManagedBean implements Serializable
{
    private String name;
    private double runTime;
    private String rating;
    private String description;
    private String id;
    
    @EJB
    private SolutionManagedBeanRemote solutionManagedBean;
    @Inject
    private Conversation conversation;
    
    //very similar fields to MovieDTO class
    public movieManagedBean()
    {
        this.name = null;
        this.runTime = 0.00;
        this.rating = null;
        this.description = "";
        this.id = "";
    }

    //setters and getter for all of the class's private fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRunTime() {
        return runTime;
    }

    public void setRunTime(double runTime) {
        this.runTime = runTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //used for adding a new entry to the movie table in the database
    //entry is crated from another create from this class, create a new DTO from these fields
    public String addMovie()
    {       
        //add MOVIE
        MovieDTO movieDTO = new MovieDTO(0, this.name, this.runTime, this.rating, this.description);
                
        Boolean result = solutionManagedBean.addMovie(movieDTO);

        if (result)
        {
            return "success";
        }
        else 
        {
          return "failure";  
        }
    }
    
    public void startConversation() {
        conversation.begin();
    }

    public void endConversation() {
        conversation.end();
    }
    
    //copies the fields from an input dto, to an exisiting object, created from this class
    public void importMovieDTO(MovieDTO dto)
    {
        this.id = dto.getId().toString();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.rating = dto.getRating();
        this.runTime = dto.getRunTime();
    }
    
    //used for updating the corresponmding mvoei entry in the database
    public String updateMovie()
    {
        MovieDTO movieDTO = new MovieDTO(Integer.parseInt(this.id), this.name, this.runTime, this.rating, this.description);
        Boolean result = solutionManagedBean.updateMovie(movieDTO);

        if (result)
        {
            return "success";
        }
        else {
            return "failure";
        }
    }
    
    //deletes corrseponding movie entry from the database
    public String deleteMovie()
    {
        Boolean result = solutionManagedBean.deleteMovie(this.id);

        if (result)
        {
            return "success";
        }
        else {
            return "failure";
        }
    }
}
