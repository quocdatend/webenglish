package com.webenglish.webenglish.Controller.Login;

import com.webenglish.webenglish.Model.Users;
import com.webenglish.webenglish.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginSignupController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/Login")
    public String Login() {
        return "Login/index";
    }

    @GetMapping("/Signup")
    public String SignupPage() {
        return "Login/Signup";
    }

    @PostMapping("/Signup")
    public String featureSignup(Model model, @RequestParam String username, @RequestParam String email, @RequestParam String password) {
        Users checkEmail = usersService.findUserByEmail(email);
        if(checkEmail != null) {
            model.addAttribute("failMessage", "Email đã dăng ký!");
            return "Login_Signup/index";
        }
        Optional<Users> checkUserName = usersService.findUserByUsername(username);
        if(!checkUserName.isEmpty()) {
            model.addAttribute("failMessage", "Username đã tồn tại!");
            return "Login_Signup/index";
        }
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        usersService.saveUser(user);
        usersService.setDefaultRole(user.getUsername());
        model.addAttribute("successMessage", "Đăng ký thành công!");
        return "Login/index";
    }
}
