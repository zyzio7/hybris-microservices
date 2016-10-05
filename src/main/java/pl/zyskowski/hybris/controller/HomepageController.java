package pl.zyskowski.hybris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zyskowski.hybris.access.AuthenticationRepository;
import pl.zyskowski.hybris.access.OAuthCodeToAccessToken;
import pl.zyskowski.hybris.service.UrlContainer;

@Controller
public class HomepageController {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private OAuthCodeToAccessToken oAuthCodeToAccessToken;

    @Value("${spring.social.facebook.appId}")
    String clientId;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homepage(@RequestParam(required = false) String code, Model model) throws Exception {
        if(code != null) {
            final String accessToken = oAuthCodeToAccessToken.convert(code);
            final String innerToken = authenticationRepository.addCredential(accessToken);
            model.addAttribute("accesstoken", innerToken);
        }
        model.addAttribute("clientId", clientId);
        model.addAttribute("redirectUrl", UrlContainer.getFacebookRedirect());

        return "homepage";
    }


}
