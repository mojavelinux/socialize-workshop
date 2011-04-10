package com.socialize.status;

import com.socialize.status.qualifier.Basic;

@Basic
public class BasicUrlShortener extends AbstractUrlShortener {

    @Override
    public String shortenUrl(String url) {
        return url.replace("http://www.", "http://");
    }
    
}
