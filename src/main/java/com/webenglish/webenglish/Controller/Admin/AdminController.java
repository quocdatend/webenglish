package com.webenglish.webenglish.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Admin")
public class AdminController {
    @GetMapping("")
    public String HomeAdmin(Model model){
        model.addAttribute("content", "Admin/HomeAdmin::content");
        return "Admin/LayoutAdmin";
    }
}
