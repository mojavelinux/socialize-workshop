package com.socialize.config;

import java.io.IOException;

import javax.enterprise.event.Observes;

import org.jboss.seam.servlet.event.Initialized;
import org.jboss.seam.servlet.event.Path;
import org.jboss.seam.servlet.http.HttpServletRequestContext;

public class ServletPathRedirector {
    public void redirectToIndexPage(@Observes @Initialized @Path("") HttpServletRequestContext ctx) throws IOException {
        ctx.getResponse().sendRedirect(ctx.getContextPath() + "/index.jsf");
    }
    
    public void redirectToJsfPage(@Observes @Initialized HttpServletRequestContext ctx) throws IOException {
        String servletPath = ctx.getRequest().getServletPath();
        if (!servletPath.startsWith("/faces/") && servletPath.endsWith(".xhtml")) {
            ctx.getResponse().sendRedirect(ctx.getContextPath() + servletPath.substring(0, servletPath.length() - 6) + ".jsf");
        }
    }
}