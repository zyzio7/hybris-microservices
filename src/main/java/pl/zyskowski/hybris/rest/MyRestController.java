package pl.zyskowski.hybris.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zyzio on 01.10.2016.
 */
@Controller
@RequestMapping("/rest")
public class MyRestController {

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "test value";
    }

}
