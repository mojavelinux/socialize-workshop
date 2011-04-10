package com.socialize.status;

import java.security.SecureRandom;
import java.util.Properties;
import java.util.Random;

import javax.inject.Inject;

import org.jboss.seam.solder.core.Requires;
import org.jboss.seam.solder.resourceLoader.Resource;

import com.socialize.status.qualifier.Google;

/**
 * http://code.google.com/apis/urlshortener/v1/reference.html
 */
//@Requires("com.sun.jersey.api.client.ClientXX")
@Google
public class GoogleUrlShortener extends AbstractUrlShortener {

    private String shortUrlRoot = "http://goo.gl/";
    
    private String apiUrlRoot;
    
    private String apiKey;
    
    @Inject @Resource("googl-config.properties")
    private Properties googlConfig;
    
    private Random rand = new SecureRandom();

    public String replaceUrls(String text) {
        System.out.println("Replacing URLs with goo.gl URLs");
        System.out.println("apiKey = " + googlConfig.getProperty("apiKey"));
        return super.replaceUrls(text);
    }

    public String shortenUrl(String target) {
        if (target.startsWith(shortUrlRoot)) {
            return target;
        }
        String pointer = shortUrlRoot + rand.nextInt(1000000);
        System.out.println(pointer + " points to " + target +
                ", which saved " + (target.length() - pointer.length()) + " characters");
        return pointer;
    }

}