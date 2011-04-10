package com.socialize.status;

import com.socialize.domain.Status;

public interface StatusUpdater {

    public void postStatus(Status status);
    
    public void shortenStatus(Status status);

}
