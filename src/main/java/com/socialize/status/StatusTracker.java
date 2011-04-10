package com.socialize.status;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.socialize.domain.Status;
import com.socialize.support.SocializeMessages;

@SessionScoped
public class StatusTracker implements Serializable {
    
    @Inject
    private SocializeMessages msg;
    
    private Status lastStatus;
    
    public void detectDuplicate(Status status) {
        if (lastStatus != null) {
            if (lastStatus.getText().equals(status.getText())) {
                throw new IllegalArgumentException(msg.duplicateStatus());
            }
        }
        
        lastStatus = status;
    }
}
