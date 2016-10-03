package pl.zyskowski.hybris.authentication;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class OAuthCodeToAccessToken {

    final String redirectUri = "http://local.host:8080/home";
    final String client_id = "341661419558486";
    final String client_secret = "91ea55d3d4dc64d2610d3aa207c1de87";

    final String url = "https://graph.facebook.com/oauth/access_token?" +
            "client_id=%s" +
            "&redirect_uri=%s" +
            "&client_secret=%s" +
            "&code=%s";

    public String exchangeForAccessToken(final String oAuthCode) throws Exception {

        final String readyUrl = String.format(url, client_id, redirectUri, client_secret, oAuthCode);
        final HttpURLConnection con = (HttpURLConnection) new URL(readyUrl).openConnection();
        final String response = IOUtils.toString(con.getInputStream(), "UTF-8");
        final String turboToken = response.split("&")[0].split("=")[1];
        return turboToken;
    }

}
