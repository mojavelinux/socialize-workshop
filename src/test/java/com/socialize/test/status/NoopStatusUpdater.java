package com.socialize.test.status;

import com.socialize.domain.Status;
import com.socialize.status.StatusUpdater;

public class NoopStatusUpdater implements StatusUpdater {

    @Override
    public void postStatus(Status status) {
    }
    
    @Override
    public void shortenStatus(Status status) {
    }
}
