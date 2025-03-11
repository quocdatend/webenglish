package com.webenglish.webenglish.Controller.Test;

import com.webenglish.webenglish.Model.Exams;
import com.webenglish.webenglish.Model.QuestionContent;
import com.webenglish.webenglish.Service.ExamsService;
import com.webenglish.webenglish.Service.QuestionContentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/Test")
public class TestController {

    private final ExamsService examsService;
    private final QuestionContentService examService;
    @Autowired
    public TestController(ExamsService examsService, QuestionContentService examService) {
        this.examsService = examsService; // Correctly initialized
        this.examService = examService;
    }
    @GetMapping("")
    public String index(Model model) {
        List<Exams> exams = examsService.getAllExams(); // Example service call
        System.out.println(exams);
        model.addAttribute("examList", exams);
        return "Test/TestIndex";
    }
    @GetMapping("/doTest/{id}")
    public String startExam(@PathVariable Long id, Model model) {
        return "Test/ExamsDeltail";
    }
}
