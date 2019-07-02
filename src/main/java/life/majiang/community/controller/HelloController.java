package life.majiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//可以被访问到  成为spring中的bean
@Controller
public class HelloController {
    @GetMapping("/hello")
    public String  hello(@RequestParam(name="name") String  name, Model   model){
        model.addAttribute("name",name);
        return  "hello";
    }
}
