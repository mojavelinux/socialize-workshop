package com.socialize.account;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.impl.api.PasswordCredential;
import org.picketlink.idm.impl.api.model.SimpleUser;

import com.socialize.data.qualifier.Primary;
import com.socialize.domain.Member;
import com.socialize.support.SocializeMessages;

public class MemberAuthenticator extends BaseAuthenticator implements Authenticator {

    @Inject
    private Credentials credentials;
    
    @Inject @Primary
    private EntityManager em;
    
    @Inject
    private Messages messages;
    
    @Inject
    private SocializeMessages msg;
    
    @Override
    public void authenticate() {
        try {
            em.createQuery("select m from Member m where m.username = :username and m.password = :password", Member.class)
                .setParameter("username", credentials.getUsername())
                .setParameter("password", PasswordCredential.class.cast(credentials.getCredential()).getValue())
                .getSingleResult();
            setStatus(AuthenticationStatus.SUCCESS);
            setUser(new SimpleUser(credentials.getUsername()));
        }
        catch (NoResultException e) {
            setStatus(AuthenticationStatus.FAILURE);
            messages.error(msg.invalidUsernamePassword());
        }
    }
}
