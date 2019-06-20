/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.GroupMappingDTO;
import entity.UsersDTO;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import session.SolutionManagedBeanRemote;

/**
 *
 * @author KeesTim
 */
//class deals will all user facing actions for the user entity
@Named(value = "usersManagedBean")
@ConversationScoped
public class usersManagedBean implements Serializable
{
    //field allows access to the solutionManagedBean stateless bean
    @EJB
    private SolutionManagedBeanRemote solutionManagedBean;
    @Inject
    private Conversation conversation;

    public String getCheck_password() {
        return check_password;
    }

    public void setCheck_password(String check_password) {
        this.check_password = check_password;
    }
    
    //very similar fields to UserDTO class
    private Integer id;
    private String name;
    private String email;
    private String check_password;
    private String password;
    private String confirm_password;
    private String address;
    private String phone;
    private GroupMappingDTO mapping_dto;
    private String user_role_name;
    
    public usersManagedBean()
    {
        this.id = null;
        this.name = null;
        this.email = null;
        this.password = null;
        this.address = null;
        this.phone = null;
        this.check_password = null;
    }

    //setters and getter for all of the class's private fields
    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public GroupMappingDTO getMapping_dto() {
        return mapping_dto;
    }

    public void setMapping_dto(GroupMappingDTO mapping_dto) {
        this.mapping_dto = mapping_dto;
    }

    public String getUser_role_name() {
        return user_role_name;
    }

    public void setUser_role_name(String user_role_name) {
        this.user_role_name = user_role_name;
    }

    public void startConversation() {
        conversation.begin();
    }

    public void endConversation() {
        conversation.end();
    }
    
    //used for adding user to database
    public String addUser()
    {
        this.phone = this.phone.replace(" ", "");
        UsersDTO user = new UsersDTO(this.name, this.email, this.getSHA(this.password), this.address, this.phone, this.user_role_name);
        //calls method in remote bean, that adds user to database

        if (solutionManagedBean.addUsers(user))
        {
            //the email address for the user that was created is sent a email, alerting them that an account was created that uses their email address
            MailActions a = new MailActions();
            String body = "Account created" + '\n' + "Email: " + this.email + '\n' + "Name: " + this.name;
            a.sendMail(email, "Cinema Solution Account Created", body);
            return "success";
        }
        else 
        {
          return "failure";  
        }
    }
        
    //used for all a 'general user' (i.e. a non cinema staff user)
    public String addGeneralUser()
    {
        this.phone = this.phone.replace(" ", "");
        //user type is hardcoded, to ensure that there is no risk of the user role being changed
        UsersDTO user = new UsersDTO(this.name, this.email, this.getSHA(this.password), this.address, this.phone, "User");
        //calls method in remote bean, that adds user to database

        if (solutionManagedBean.addUsers(user))
        {
            //the email address for the user that was created is sent a email, alerting them that an account was created that uses their email address
            MailActions a = new MailActions();
            String body = "Account created" + '\n' + "Email: " + this.email + '\n' + "Name: " + this.name;
            a.sendMail(email, "Cinema Solution Account Created", body);
            return "success";
        }
        else 
        {
          return "failure";  
        }
    }
    
    //updates user entry on database
    public String updateUser()
    {
        this.phone = this.phone.replace(" ", "");
        UsersDTO user = new UsersDTO(this.id, this.name, this.email, this.getSHA(this.password), 
                this.address, this.phone, this.user_role_name);
                
        //calls method on remote bean that updates db entry
        if (solutionManagedBean.updateUser(user))
        {
            //if the update action was reported as successful, then email is sent to user that was updated, alerting them that their account was updated
            MailActions a = new MailActions();
            String body = "Account Updated" + '\n' + "Email: " + this.email + '\n' + "Name: " + this.name;
            a.sendMail(email, "Cinema Solution Account Updated", body);
            return "success";
        }
        else 
        {
          return "failure";  
        }
    }
    
    //copies all of the values in a input DTO to all of the class's internal fields
    public void importUsersDTO(UsersDTO dto)
    {
        this.id = dto.getId();
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.address = dto.getAddress();
        this.phone = dto.getPhone();
        this.mapping_dto = dto.getGroup_mapping();
        this.user_role_name = mapping_dto.getGroupName();
    }
    
    //due to JDBC requiring encryption on the password for the solution users, a SHA converter method is defined
    //code from: https://www.geeksforgeeks.org/sha-256-hash-in-java/
    public String getSHA(String input) 
    { 
        try 
        {
            // Static getInstance method is called with hashing SHA 
            MessageDigest md = MessageDigest.getInstance("SHA-256"); 
  
            // digest() method called 
            // to calculate message digest of an input 
            // and return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
    
            while (hashtext.length() < 64) 
            { 
                hashtext = "0" + hashtext; 
            } 
            
            hashtext = hashtext.toUpperCase();
            //hashtext = "0" + hashtext;
  
            return hashtext; 
        } 
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            System.out.println("Exception thrown"
                               + " for incorrect algorithm: " + e); 
  
            return null;
        } 
    }
    
    //method allows email to be validated on forms
    public void validateEmail(FacesContext context,
            UIComponent componentToValidate,
            Object newValue) throws ValidatorException
    {
        String input_email = (String) newValue;
        
        //regex from https://emailregex.com/
        
        //if input string doesn't match pattern, validation message is returned
        if (!input_email.matches("(?:[a-z0-9!#$%&'*+\\=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
        {
            FacesMessage message = new FacesMessage("Insure that input email is in the correct format");
            
            throw new ValidatorException(message);
        }    
    }
    
    //methods allow phone number to be validated on forms
    public void validatePhone(FacesContext context,
            UIComponent componentToValidate,
            Object newValue) throws ValidatorException
    {
        String input_phone = (String) newValue;
        input_phone = input_phone.replace(" ", "");
        
        if (!input_phone.matches("(\\([0-9]{2}\\))?([0-9]{8})"))
        { 
            FacesMessage message = new FacesMessage("Insure that input phone is in the correct format: (XX) XXXX XXXX OR XXXX XXXX");
            
            throw new ValidatorException(message);
        }    
    }
    
    //methods ensure that the password and confirm password field contain identical values
    public void validatePasswordMatch(FacesContext context,
            UIComponent componentToValidate,
            Object passwordValue) throws ValidatorException {

        // get password
        String password = (String) passwordValue;

        // get confirm password
        UIInput cnfPasswordComponent = (UIInput) componentToValidate.getAttributes().get("confirm_password");
        String cnfPassword = (String) cnfPasswordComponent.getSubmittedValue();

        System.out.println("password : " + password);
        System.out.println("confirm password : " + cnfPassword);

        if (!password.equals(cnfPassword)) {
            FacesMessage message = new FacesMessage(
                    "Password and Confirm Password are not the same! They must be the same.");
            throw new ValidatorException(message);
        }
    }
     
    //deletes user from database
    public boolean deleteUser()
    {
        //calls a message on the remote bean, to delete a user of a given ID
        return (solutionManagedBean.deleteUser(this.email));
    }
    
    //used for deletion of own account
    public String deleteSelf()
    {
        
        //due this method being for the user to delete themselve, they are required to input their passwod
        //this password validation method checks if the entered password is the correct password for the currently logged in account
        if (this.checkInputPassword())
        {
            if (this.deleteUser()) 
            {
                return "success";
            }
        }
        
        return "failure";
    }
    
    //use for updating for own account
    public String updateSelf()
    {
        //checks if entered password is correct for current account
        if (this.checkInputPassword())
        {
            //assigns internal field
            this.password = this.check_password;
            
            //calls the normal updateUser method
            return updateUser();
        }
        
        return "failure";
    }
    
    public boolean checkInputPassword()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();       
        //encrypted password is passed into the function since all the database password data is encrypted
        
        //request.getRemoteUser(), get primary key of currently logged in user account,
        //give the users table primary key is the email, then request.getRemoteUser() returns user email
        return solutionManagedBean.checkInputPassword(request.getRemoteUser(), this.getSHA(this.check_password));
    }
    
    //updates user password
    public boolean updatePassword()
    {
        if (this.checkInputPassword())
        {
            //encrypted password is passed into the DTO
            return solutionManagedBean.updatePassword(new UsersDTO(this.name, 
                this.email, this.getSHA(this.password), 
                this.address, this.phone, 
                this.user_role_name));
        }
        
        return false;
    }
}
