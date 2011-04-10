package com.socialize.account;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.solder.logging.Category;
import org.jboss.seam.transaction.Transactional;
import org.picketlink.idm.impl.api.PasswordCredential;

import com.socialize.data.qualifier.Primary;
import com.socialize.domain.Member;
import com.socialize.support.SocializeLogger;
import com.socialize.support.SocializeMessages;

@Model
public class Registration {
    @Inject
    @Category("socialize")
    private SocializeLogger log;
    
    @Inject
    private SocializeMessages msg;

    @Inject
    @Primary
    private EntityManager em;

    @Inject
    private Event<Member> memberEventSrc;

    private Member newMember;
    
    private String confirmPassword;
    
    @Inject
    private Messages messages;
    
    @Produces
    @Named
    public Member getNewMember() {
        return newMember;
    }

    @Transactional
    public String register() throws Exception {
//        if (!uniqueMember(newMember)) {
//            return null;
//        }
        log.registeringMember(newMember);
        em.persist(newMember);
        memberEventSrc.fire(newMember);
        initNewMember();
        return "index";
    }

    private boolean uniqueMember(Member member) {
        boolean unique = true;
        try {
            em.createQuery("select m from Member m where m.username = :username")
                .setParameter("username", member.getUsername())
                .getSingleResult();
            messages.error(msg.usernameTaken()).targets("reg:username");
            unique = false;
        }
        catch (NoResultException e) {
        }
        
        try {
            em.createQuery("select m from Member m where m.email = :email")
                .setParameter("email", member.getEmail())
                .getSingleResult();
            messages.error(msg.accountAlreadyExistsForEmail()).targets("reg:email");
            unique = false;
        }
        catch (NoResultException e) {
        }
        
        return unique;
    }

    public void postRegister(@Observes Member member, Identity identity, Credentials credentials, Messages messages) {
        credentials.setUsername(member.getUsername());
        credentials.setCredential(new PasswordCredential(member.getPassword()));
        identity.quietLogin();
        // quietLogin is leaving behind a message, and this clear isn't working
        messages.getAll().clear();
        messages.info(msg.welcome(member.getName()));
        log.registeredMember(member);
    }
    
    @PostConstruct
    public void initNewMember() {
        newMember = new Member();
        confirmPassword = null;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
