package com.socialize.support;

import org.jboss.logging.Logger.Level;
import org.jboss.seam.solder.logging.Log;
import org.jboss.seam.solder.logging.MessageLogger;
import org.jboss.seam.solder.messages.Cause;
import org.jboss.seam.solder.messages.Message;

import com.socialize.domain.Member;

@MessageLogger
public interface SocializeLogger {
    @Log @Message("Registering %s...")
    void registeringMember(Member m);
    
    @Log @Message("Successfully registered member %s")
    void registeredMember(Member m);
    
    @Log @Message("Posting status for %s: %s")
    void postingStatusForUser(String username, String text);
    
    @Log @Message("Importing seed data for application at context path %s")
    void importingSeedData(String application);
    
    @Log @Message("Seed data successfully imported")
    void seedDataImportComplete();
    
    @Log(level = Level.WARN) @Message("Seed data import failed")
    void seedDataImportFailed(@Cause Throwable reason);
}
