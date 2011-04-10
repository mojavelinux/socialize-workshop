package com.socialize.startup;

import javax.ejb.TransactionAttribute;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Alternative;
import javax.persistence.EntityManager;

import org.jboss.seam.servlet.WebApplication;
import org.jboss.seam.servlet.event.Initialized;
import org.jboss.seam.solder.logging.Category;

import com.socialize.data.qualifier.Utility;
import com.socialize.domain.Member;
import com.socialize.support.SocializeLogger;

/**
 * Import seed data into the database on application startup.
 * 
 * <p>
 * Observes the context initialized event and loads seed data into the database using JPA.
 * </p>
 * 
 * @author Dan Allen
 */
@Alternative
public class SeedDataImporter {
    @TransactionAttribute
    public void importData(@Observes @Initialized WebApplication webapp, @Utility EntityManager em, @Category("socialize") SocializeLogger log) {
        log.importingSeedData(webapp.getContextPath());
        Member member1 = new Member();
        member1.setName("John Smith");
        member1.setUsername("jsmith");
        member1.setPassword("jsmith");
        member1.setEmail("john.smith@mailinator.com");
        try {
            em.persist(member1);
            log.seedDataImportComplete();
        } catch (Exception e) {
            log.seedDataImportFailed(e);
        }
    }
}