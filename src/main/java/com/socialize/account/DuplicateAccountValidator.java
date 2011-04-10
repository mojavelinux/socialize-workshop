/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.socialize.account;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.faces.validation.InputElement;
import org.jboss.seam.international.status.Messages;

import com.socialize.data.qualifier.Primary;
import com.socialize.support.SocializeMessages;

/**
 * Validate that the account is not already taken.
 */
@FacesValidator("duplicateAccount")
public class DuplicateAccountValidator implements Validator {
    @Inject
    private Messages messages;
    
    @Inject
    private SocializeMessages msg;
    
    @Inject
    @Primary
    private EntityManager em;

    @Inject
    private InputElement<String> username;

    @Inject
    private InputElement<String> email;

    public void validate(final FacesContext ctx, final UIComponent form, final Object component) throws ValidatorException {
        boolean conflicts = false;
        if (username.getValue() != null) {
            try {
                em.createQuery("select m from Member m where m.username = :username")
                    .setParameter("username", username.getValue())
                    .getSingleResult();
                messages.error(msg.usernameTaken()).targets(username.getClientId());
                conflicts = true;
            }
            catch (NoResultException e) {
            }
        }
        
        if (email.getValue() != null) {
            try {
                em.createQuery("select m from Member m where m.email = :email")
                    .setParameter("email", email.getValue())
                    .getSingleResult();
                messages.error(msg.accountAlreadyExistsForEmail()).targets(email.getClientId());
                conflicts = true;
            }
            catch (NoResultException e) {
            }
        }
        
        if (conflicts) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg.accountConflict(), null));
        }
    }

}
