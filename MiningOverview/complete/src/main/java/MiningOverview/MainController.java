package MiningOverview;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String indexreroute(){
        return "redirect:index";
    }
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @RequestMapping("/charts")
    public String charts(){
        return "charts";
    }
    @RequestMapping("/tables")
    public String tables(){
        return "tables";
    }
}
