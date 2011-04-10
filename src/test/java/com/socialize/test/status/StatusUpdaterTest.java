package com.socialize.test.status;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.socialize.data.PersistenceContextProducers;
import com.socialize.data.qualifier.Primary;
import com.socialize.domain.Member;
import com.socialize.domain.Status;
import com.socialize.status.StatusUpdater;
import com.socialize.status.UrlShortenerSelector;
import com.socialize.support.SocializeLogger;

@RunWith(Arquillian.class)
public class StatusUpdaterTest {
    @Deployment
    public static WebArchive createArchive() {
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadReposFromPom("pom.xml");
        WebArchive war = ShrinkWrap.create(WebArchive.class, "update-status.war")
            //.addClasses(StatusUpdaterBean.class, BasicUrlShortener.class, GoogleUrlShortener.class)
            .addPackages(true, StatusUpdater.class.getPackage())
            .addPackage(SocializeLogger.class.getPackage())
            .addClass(PersistenceContextProducers.class)
            .addPackage(Primary.class.getPackage())
            .addClasses(Status.class, Member.class)
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsLibraries(resolver.artifacts(
                    "org.jboss.seam.solder:seam-solder:3.0.0.Final",
                    "org.jboss.seam.persistence:seam-persistence:3.0.0.Final").resolveAsFiles());
        System.out.println(war.toString(true));
        return war;
    }
    
    @Inject StatusUpdater service;
    @Inject UrlShortenerSelector selector;
    
    @Test
    public void should_update_status() {
        String status = "Check out all the projects at http://www.jboss.org/projects/matrix!";
        Assert.assertNotNull(service);
        selector.setSelection("google");
        service.postStatus(new Status("mojavelinux", status));
    }
}
