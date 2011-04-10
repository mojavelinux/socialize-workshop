package com.socialize.status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractUrlShortener implements UrlShortener {

    @Override
    public String replaceUrls(String text) {
        Pattern p = Pattern.compile("http://[^ $!]+");
        Matcher m = p.matcher(text);
        StringBuffer buffer = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(buffer, shortenUrl(m.group()));
        }
        m.appendTail(buffer);
        return buffer.toString();
    }

}
