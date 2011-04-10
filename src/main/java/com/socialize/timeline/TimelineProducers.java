package com.socialize.timeline;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.jboss.seam.servlet.http.RequestParam;

import com.socialize.domain.Status;
import com.socialize.status.qualifier.Latest;

@RequestScoped
public class TimelineProducers {

    private int latestUpdatesLimit = 10;
    
    @Inject @RequestParam
    private Instance<String> username;
    
    @Inject
    private HttpServletRequest request;
    
    @PersistenceContext
    private EntityManager em;
    
    private List<Status> latestUpdates;
    
    private List<Status> userTimeline;
    
    @Produces @Named @Latest
    public List<Status> getLatestUpdates() {
        return latestUpdates;
    }
    
    @Produces @Named
    public List<Status> getUserTimeline() {
        if (userTimeline == null) {
            TypedQuery<Status> q = em.createQuery("select s from Status s where s.username = :username order by s.created DESC", Status.class);
            //q.setParameter("username", username.get());
            q.setParameter("username", request.getParameter("username"));
            userTimeline = q.getResultList();
        }
        return userTimeline;
    }
    
    public void onStatusPosted(@Observes(notifyObserver = Reception.IF_EXISTS) Status status) {
        retrieveLatestUpdates();
    }
    
    @PostConstruct
    public void retrieveLatestUpdates() {
        TypedQuery<Status> q = em.createQuery("select s from Status s order by s.created DESC", Status.class);
        q.setMaxResults(latestUpdatesLimit);
        System.out.println("Fetching " + latestUpdatesLimit + " latest updates");
        latestUpdates = q.getResultList();
    }
    
}
