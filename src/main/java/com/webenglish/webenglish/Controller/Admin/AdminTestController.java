package com.webenglish.webenglish.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Admin")
public class AdminTestController {
    @GetMapping("/Test/{id}")
    public String HomeAdmintest(Model model,@PathVariable int id){

        return "Admin/Test/TestIndex";
    }
}
