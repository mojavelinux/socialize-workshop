package com.socialize.support;

import org.jboss.seam.solder.messages.Message;
import org.jboss.seam.solder.messages.MessageBundle;

@MessageBundle
public interface SocializeMessages {
    
    @Message("Welcome to the conversation, %s!")
    String welcome(String name);
    
    @Message("You already said that!")
    String duplicateStatus();
    
    @Message("Wrong username and password combination")
    String invalidUsernamePassword();
    
    @Message("%s do not match")
    String inputsDoNotMatch(String label);

    @Message("Username or email conflicts with an existing account")
    String accountConflict();
    
    @Message("does not match %s")
    String valueDoesNotMatch(String source);
    
    @Message("username taken")
    String usernameTaken();
    
    @Message("email associated with existing account")
    String accountAlreadyExistsForEmail();
}
