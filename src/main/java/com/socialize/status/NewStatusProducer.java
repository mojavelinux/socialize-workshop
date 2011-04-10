package com.socialize.status;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.jboss.seam.security.Identity;

import com.socialize.domain.Status;
import com.socialize.status.qualifier.Posted;

@Model
public class NewStatusProducer {
    private Status newStatus;
    
    public void clear() {
        newStatus = null;
    }
    
    public void onStatusPosted(@Observes(notifyObserver = Reception.IF_EXISTS, during = TransactionPhase.AFTER_SUCCESS) @Posted Status s) {
        clear();
    }
    
    @Produces @Named
    public Status getStatus(Identity identity) {
        if (newStatus == null) {
            newStatus = new Status(identity.getUser().getId());
        }
        
        return newStatus;
    }
}
