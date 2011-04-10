package com.socialize.status;

import java.util.Date;

import javax.ejb.TransactionAttribute;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.SystemException;
import javax.validation.ValidatorFactory;

import org.jboss.seam.solder.logging.Category;
import org.jboss.seam.transaction.DefaultTransaction;
import org.jboss.seam.transaction.SeamTransaction;

import com.socialize.domain.Status;
import com.socialize.status.qualifier.Posted;
import com.socialize.support.SocializeLogger;

@Named("statusUpdater")
@TransactionAttribute
public class StatusUpdaterBean implements StatusUpdater {
    
    @Inject
    @Category("socialize")
    private SocializeLogger log;
    
    @Inject
    private UrlShortener shortener;
    
    @Inject @Any
    private Event<Status> statusEvent;
    
    @Inject
    @DefaultTransaction
    private SeamTransaction tx;

    @Inject
    ValidatorFactory validator;
    
    @Inject
    private EntityManager em;
    
    @Override
    public void postStatus(Status status) {
        log.postingStatusForUser(status.getUsername(), status.getText());
        status.setCreated(new Date());
//        status.setText(shortener.replaceUrls(status.getText()));
        // TODO validate length
        try {
            System.out.println("Transaction active? " + tx.isActive());
        } catch (SystemException e) {
        }
        em.persist(status);
        statusEvent.select(new AnnotationLiteral<Posted>() {}).fire(status);
    }
    
    @Override
    public void shortenStatus(Status status) {
        int len = status.getText().length();
        status.setText(shortener.replaceUrls(status.getText()));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Saved " + (len - status.getText().length()) + " characters!"));
    }
}