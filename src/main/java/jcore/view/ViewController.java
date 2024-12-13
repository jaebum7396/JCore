package jcore.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {
    @RequestMapping("/admin/index")
    public ModelAndView adminIndex() throws Exception {
        System.out.println("admin index");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/index");
        return mv;
    }
    @GetMapping("/admin/login")
    public String adminUser() {
        return "admin/login";
    }
    @GetMapping("/policy/privacy_policy")
    public String privacyPolicy() {
        return "policy/privacy_policy";
    }
}
