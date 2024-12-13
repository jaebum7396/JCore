package jcore.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {
    @GetMapping("/admin/charts")
    public String charts() {
        return "admin/charts";
    }
    @RequestMapping("/admin/index")
    public ModelAndView adminIndex() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/index");
        return mv;
    }
    @GetMapping("/admin/layout-sidenav-light")
    public String layoutSidenavLight() {
        return "admin/layout-sidenav-light";
    }
    @GetMapping("/admin/layout-static")
    public String layoutstatic() {
        return "admin/layout-static";
    }
    @GetMapping("/admin/login")
    public String adminUser() {
        return "admin/login";
    }
    @GetMapping("/admin/password")
    public String password() {
        return "admin/password";
    }
    @GetMapping("/admin/register")
    public String register() {
        return "admin/register";
    }
    @GetMapping("/admin/tables")
    public String tables() {
        return "admin/tables";
    }
    @GetMapping("/policy/privacy_policy")
    public String privacyPolicy() {
        return "policy/privacy_policy";
    }
}
