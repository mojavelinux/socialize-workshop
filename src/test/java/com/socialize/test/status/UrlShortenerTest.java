package com.socialize.test.status;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.socialize.status.AbstractUrlShortener;
import com.socialize.status.BasicUrlShortener;
import com.socialize.status.UrlShortener;
import com.socialize.status.qualifier.Basic;

@RunWith(Arquillian.class)
public class UrlShortenerTest {
    @Deployment
    public static JavaArchive createArchive() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(BasicUrlShortener.class, AbstractUrlShortener.class, UrlShortener.class, Basic.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(jar.toString(true));
        return jar;
    }
    
    @Inject @Basic UrlShortener service;
    
    @Test
    public void should_shorten_urls() {
        String status = "Check out all the projects at http://www.jboss.org!";
        Assert.assertNotNull(service);
        String result = service.replaceUrls(status);
        Assert.assertEquals("Check out all the projects at http://jboss.org!", result);
    }
}
