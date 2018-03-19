package yi.work.predictx.webapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hy10121012 on 2018/3/18.
 */

@Controller
public class DefaultWebApi {

    @RequestMapping("/")
    public String homePage(){

        return "index.html";
    }

}
