package jcore.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {
    @GetMapping("/sample/charts")
    public String charts() {
        return "sample/charts";
    }
    @RequestMapping("/sample/index")
    public ModelAndView sampleIndex() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sample/index");
        return mv;
    }
    @GetMapping("/sample/layout-sidenav-light")
    public String layoutSidenavLight() {
        return "sample/layout-sidenav-light";
    }
    @GetMapping("/sample/layout-static")
    public String layoutstatic() {
        return "sample/layout-static";
    }
    @GetMapping("/sample/login")
    public String sampleUser() {
        return "sample/login";
    }
    @GetMapping("/sample/password")
    public String password() {
        return "sample/password";
    }
    @GetMapping("/sample/register")
    public String register() {
        return "sample/register";
    }
    @GetMapping("/sample/tables")
    public String tables() {
        return "sample/tables";
    }
    @GetMapping("/policy/privacy_policy")
    public String privacyPolicy() {
        return "policy/privacy_policy";
    }
    @GetMapping("/admin/index")
    public String adminIndex() {
        return "admin/index";
    }
}
