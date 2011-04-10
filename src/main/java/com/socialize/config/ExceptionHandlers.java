package com.socialize.config;

import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.jboss.seam.exception.control.CaughtException;
import org.jboss.seam.exception.control.Handles;
import org.jboss.seam.exception.control.HandlesExceptions;

@HandlesExceptions
public class ExceptionHandlers {
    void handleAll(@Handles CaughtException<Throwable> caught, Logger log, HttpServletResponse resp) {
        log.error("Handling exception", caught.getException());
        try {
            resp.sendError(500, "Invalid request: " + caught.getException().getMessage());
            caught.markHandled();
        } catch (Exception e) {
        }
    }
    
    /*
    void handleAll(@Handles CaughtException<Throwable> caught, HttpServletResponse response, @ContextPath String contextPath) {
        try {
            response.sendRedirect(contextPath + "/500.html");
            caught.markHandled();
        } catch (Exception e) {
        }
    }*/
}
