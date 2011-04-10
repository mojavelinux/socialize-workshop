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

import org.jboss.seam.faces.validation.InputElement;
import org.jboss.seam.international.status.Messages;

import com.socialize.support.SocializeMessages;

/**
 * Validate that both the password fields contain the same value. Implements the classic password change validation.
 */
@FacesValidator("confirmInputText")
public class ConfirmInputTextValidator implements Validator {
    @Inject
    private Messages messages;
    
    @Inject
    private SocializeMessages msg;

    @Inject
    private InputElement<String> primaryInput;

    @Inject
    private InputElement<String> confirmInput;

    public void validate(final FacesContext ctx, final UIComponent form, final Object component) throws ValidatorException {
        if (primaryInput.getValue() == null || confirmInput.getValue() == null) {
            return;
        }
        
        if (!primaryInput.getValue().equals(confirmInput.getValue())) {
            messages.error(msg.valueDoesNotMatch("password")).targets(confirmInput.getClientId());
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg.inputsDoNotMatch("Passwords"), null));
            //throw new ValidatorException(Collections.<FacesMessage>emptyList());
        }
    }

}
