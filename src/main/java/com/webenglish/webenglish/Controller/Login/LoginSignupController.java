package com.webenglish.webenglish.Controller.Login;

import com.webenglish.webenglish.Model.Users;
import com.webenglish.webenglish.Service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

    @GetMapping("/logout")
    public String performLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/Home";
    }

    @PostMapping("/RedirectPage")
    public String redirectPage(@AuthenticationPrincipal UserDetails userDetails) {
        Users users = usersService.getUserByUsername(userDetails.getUsername());
        if (users == null) {
            return  "redirect:/Admin";
        } else {
            return "redirect:/Home";
        }
    }
}
