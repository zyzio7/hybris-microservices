package pl.zyskowski.hybris.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlContainer {

    @Value("${SERVER_ADDRESS}")
    private static String SERVER_ADDRESS;

    @Value("${DB_ADDRESS")
    private static String DB_ADDRESS;

    static public String getDbUrl() {
        return DB_ADDRESS;
    }

    static public String getServerAddress() {
        return SERVER_ADDRESS;
    }

    static public String getFacebookRedirect() {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(getServerAddress());
        sb.append("/home");
        return sb.toString();
    }

    private UrlContainer(){};
}
