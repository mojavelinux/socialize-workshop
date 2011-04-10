package com.socialize.test.status;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.spec.cdi.beans.BeansDescriptor;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.socialize.domain.Status;
import com.socialize.status.DuplicateStatusDetector;
import com.socialize.status.StatusTracker;
import com.socialize.status.StatusUpdater;

@RunWith(Arquillian.class)
public class DuplicateStatusDetectorTest {
    @Deployment
    public static JavaArchive createArchive() {
        BeansDescriptor beansXml = Descriptors.create(BeansDescriptor.class);
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(StatusUpdater.class, NoopStatusUpdater.class, DuplicateStatusDetector.class, Status.class, StatusTracker.class)
            .addAsManifestResource(new StringAsset(beansXml.decorator(DuplicateStatusDetector.class).exportAsString()), beansXml.getDescriptorName());
        System.out.println(jar.toString(true));
        return jar;
    }
    
    @Inject StatusUpdater service;
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldDetectedDuplicatePost() {
        Status s = new Status();
        s.setUsername("mojavelinux");
        s.setText("I'm tweeting!");
        service.postStatus(s);
        service.postStatus(s);
    }
}
