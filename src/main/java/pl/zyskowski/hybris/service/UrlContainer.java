package pl.zyskowski.hybris.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlContainer {

    @Value("${server.url}")
    private String SERVER_ADDRESS;

    @Value("${mongo.url}")
    private String DB_ADDRESS;

    public String getDbUrl() {
        return DB_ADDRESS;
    }

    public String getServerAddress() {
        return SERVER_ADDRESS;
    }

    public String getFacebookRedirect() {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(getServerAddress());
        sb.append("/home");
        return sb.toString();
    }

    private UrlContainer(){};
}
