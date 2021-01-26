package tw.peugin.bahaluckydraw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LuckyDrawController {
    @GetMapping(value = {"/","/index"})
    public String mainPage(){
        return "index";
    }
}
