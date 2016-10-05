package pl.zyskowski.hybris.access;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.zyskowski.hybris.service.UrlContainer;

import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class OAuthCodeToAccessToken {

    @Autowired
    UrlContainer urlContainer;

    @Value("${spring.social.facebook.appId}")
    String client_id;

    @Value("${spring.social.facebook.appSecret}")
    String client_secret;

    final String url = "https://graph.facebook.com/oauth/access_token?" +
            "client_id=%s" +
            "&redirect_uri=%s" +
            "&client_secret=%s" +
            "&code=%s";

    public String convert(final String oAuthCode) throws Exception {

        final String readyUrl = String.format(url, client_id, urlContainer.getFacebookRedirect(), client_secret, oAuthCode);
        final HttpURLConnection con = (HttpURLConnection) new URL(readyUrl).openConnection();
        final String response = IOUtils.toString(con.getInputStream(), "UTF-8");
        final String accessToken = response.split("&")[0].split("=")[1];
        return accessToken;
    }

}
