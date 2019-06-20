
package web;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KeesTim
 */
@Named(value = "mailActions")
@SessionScoped
public class MailActions implements Serializable
{
    public MailActions()
    {
    
    }
    
    //allows mail to be sent through java mail
    //method takes a number of arguements
    //these being:
    //- to (the destination address)
    //- subject (the subject of the email that is to be sent)
    //- body (what is the body of the email that is to sent)
    public void sendMail(String to, String subject, String body)
    {
        String smtpServer = "smtp.gmail.com";
        String from = "user@gmail.com";
        String emailUser = from;
        String password = "PASSWORD";
        try 
        {
            Properties props = System.getProperties();
            
            //sets a variety of properties for the java mail connection
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.port", 465);
            props.put("mail.smtp.auth", true);
             props.put("mail.debug", "true");
            //sets security and encryption settings
            props.put("mail.smtp.starttls.enable", true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            // -- prepare a password authenticator --
            MailAuthenticator myPA = new MailAuthenticator(emailUser, password); // see MyAuthenticator class
            // get a session
            Session session = Session.getInstance(props, myPA);
            // -- Create a new message --
            Message msg = new MimeMessage(session);
            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            // -- Set the subject and body text --
            msg.setSubject(subject);
            msg.setText(body);
            // -- Set some other header information --
            msg.setHeader("X-Mailer", "Gmail");
            msg.setSentDate(new Date());
            // -- Send the message --
            Transport.send(msg, emailUser, password);
            System.out.println("Message sent OK.");
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
}
