package com.webenglish.webenglish.Controller.Test;

import com.webenglish.webenglish.Model.Exams;
import com.webenglish.webenglish.Service.ExamsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/Test")
public class TestController {

    private final ExamsService examsService;

    @Autowired
    public TestController(ExamsService examsService) {
        this.examsService = examsService; // Correctly initialized
    }
    @GetMapping("")
    public String index(Model model) {
        List<Exams> exams = examsService.getAllExams(); // Example service call
        System.out.println(exams);
        model.addAttribute("examList", exams);
//        model.addAttribute("alertMessage", "This is an alert message!"); // Optional
        return "Test/TestIndex";
    }
}
