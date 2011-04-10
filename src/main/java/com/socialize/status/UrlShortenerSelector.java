package com.socialize.status;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;

import com.socialize.status.qualifier.Basic;
import com.socialize.status.qualifier.Google;

@Model
public class UrlShortenerSelector {
    
    private String selection;
    
    @Produces @RequestScoped
    public UrlShortener select(@Basic UrlShortener basicImpl, @Google Instance<UrlShortener> googleImpl) {
        if ("google".equals(selection) && !googleImpl.isUnsatisfied()) {
            return googleImpl.get();
        }
        else {
            return basicImpl;
        }
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }
}