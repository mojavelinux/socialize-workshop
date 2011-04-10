package com.socialize.status;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.socialize.domain.Status;

@Decorator
public abstract class DuplicateStatusDetector implements StatusUpdater {

    @Inject @Delegate
    private StatusUpdater delegate;
    
    @Inject
    private StatusTracker statusTracker;
    
    @Override
    public void postStatus(Status status) {
        statusTracker.detectDuplicate(status);
        delegate.postStatus(status);
    }
    
}
