package pl.zyskowski.hybris.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zyskowski.hybris.authentication.AuthenticationRepository;
import pl.zyskowski.hybris.authentication.OAuthCodeToAccessToken;

@Controller
public class HomepageController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private OAuthCodeToAccessToken oAuthCodeToAccessToken;

    public HomepageController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homepage(@RequestParam(required = false) String code, Model model) throws Exception {

        if(code != null) {
            final String accessToken = oAuthCodeToAccessToken.exchangeForAccessToken(code);
            final String innerToken = authenticationRepository.addCredential(accessToken);
            model.addAttribute("accesstoken", innerToken);
        }

        return "homepage";
    }


}
