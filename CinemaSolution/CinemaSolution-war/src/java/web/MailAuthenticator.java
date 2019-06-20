package web;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author KeesTim
 */

//byasic authenticator class for the MailActions class
public class MailAuthenticator extends Authenticator 
{
    PasswordAuthentication mypa;
    public MailAuthenticator(String username, String password) 
    {
        mypa = new PasswordAuthentication(username, password);
    }
    
    @Override
    public PasswordAuthentication getPasswordAuthentication() 
    {
        return mypa;
    }
}
