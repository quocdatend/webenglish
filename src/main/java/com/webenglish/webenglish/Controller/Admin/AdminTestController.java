package com.webenglish.webenglish.Controller.Admin;

import com.webenglish.webenglish.Model.Exams;
import com.webenglish.webenglish.Model.QuestionContent;
import com.webenglish.webenglish.Service.ExamsService;
import com.webenglish.webenglish.Service.QuestionContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/Test")
public class AdminTestController {
    private final ExamsService examsService;
    private final QuestionContentService questionContentService;

    public AdminTestController(ExamsService examsService, QuestionContentService questionContentService) {
        this.examsService = examsService;
        this.questionContentService = questionContentService;
    }

    @GetMapping("/Exam")
    public String HomeAdminTest(Model model){
        List<Exams> exams = examsService.getAllExams(); // Example service call
        System.out.println(exams);
        model.addAttribute("exams", exams);
        return "Admin/Test/TestIndex";
    }
    //UPDATE
    //DATELE

    @GetMapping("/Exam/{id}")
    public String showQuestionContent(@PathVariable("id") long examId, Model model) {
        Exams exam = examsService.getExamById(examId);
        List<QuestionContent> questionContents = questionContentService.getQuestionContentsByExamId(examId);
        model.addAttribute("exam", exam);
        model.addAttribute("questionsContent", questionContents);
        return "Admin/Test/Question_content";
    }


}
