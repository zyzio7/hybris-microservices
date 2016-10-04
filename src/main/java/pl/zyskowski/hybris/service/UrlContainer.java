package pl.zyskowski.hybris.service;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UrlContainer {

    private static Boolean remoteServer = System.getenv("MONGODB_URI") != null;
    private static String SERVER_HOST = "hybris-microservices.herokuapp.com";
    private static String LOCAL_HOST = "localhost";
    private static String LOCAL_PORT = "8080";

    static public String getDbUrl() {
        if(remoteServer) return System.getenv("MONGODB_URI");
        else return getServerAddress();
    }

    static public String getServerAddress() {
        if (remoteServer) return SERVER_HOST;
        else return LOCAL_HOST + ":" + LOCAL_PORT;
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
