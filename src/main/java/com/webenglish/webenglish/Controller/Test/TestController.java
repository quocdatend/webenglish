package com.webenglish.webenglish.Controller.Test;

import com.webenglish.webenglish.Model.Exams;
import com.webenglish.webenglish.Model.Question;
import com.webenglish.webenglish.Model.QuestionContent;
import com.webenglish.webenglish.Service.ExamsService;
import com.webenglish.webenglish.Service.QuestionContentService;
import com.webenglish.webenglish.Service.QuestionService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/Test")
public class TestController {
    private final QuestionService questionService;
    private final ExamsService examsService;
    private final QuestionContentService questionContentService;
    @Autowired
    public TestController(QuestionService questionService, ExamsService examsService, QuestionContentService questionContentService) {
        this.questionService = questionService;
        this.examsService = examsService;
        this.questionContentService = questionContentService;
    }
    @GetMapping("")
    public String index(Model model) {
        List<Exams> exams = examsService.getAllExams();
        System.out.println(exams);
        model.addAttribute("examList", exams);
        return "Test/TestIndex";
    }
    @GetMapping("Testdetail/{id}")
    public String startExam(@PathVariable Long id, Model model) {
        Exams exams = examsService.getExamById(id);
        model.addAttribute("exam",exams);
        List<QuestionContent> questionContents = questionContentService.getAllQuestionContents();
            System.out.println(questionContents);
        model.addAttribute("QC",questionContents);
        return "Test/ExamsDeltail";
    }
    @GetMapping("start-test/{id}")
    public String doExam(@PathVariable Long id, Model model, HttpSession session){
        Exams exams = examsService.getExamById(id);

        if (exams == null) {
            model.addAttribute("error", "Bài kiểm tra không tồn tại!");
            return "errorPage"; // Redirect tới trang lỗi nếu bài kiểm tra không tồn tại
        }
        model.addAttribute("exam",exams);
        List<Question> questions = questionService.getAllQuestions();
        model.addAttribute("danhsachQC", questionContentService.getAllQuestionContents());
        model.addAttribute("questions", questions);
        session.setAttribute("timeRemainingSeconds", 3600);
        return "Test/TestMain";
    }
    @PostMapping("/submit")
    public String submitAnswers(@RequestParam Map<String, String> selectedAnswers, Model model) {
        Map<String, String> correctAnswers = Map.of(
                "5", "A",
                "6", "B",
                "7", "C",
                "8", "D",
                "9", "A"
        );

        int correctCount = 0;
        int totalQuestions = correctAnswers.size();
        selectedAnswers.forEach((questionId, answer) ->
                System.out.println("Câu hỏi ID: " + questionId + " - Đáp án chọn: " + answer)
        );
        model.addAttribute("message", "Bài làm đã được nộp thành công!");
        for (Map.Entry<String, String> entry : selectedAnswers.entrySet()) {
            String questionId = entry.getKey();
            String userAnswer = entry.getValue();
            String correctAnswer = correctAnswers.get(questionId);

            if (correctAnswer != null && correctAnswer.equals(userAnswer)) {
                correctCount++;
            }
        }

        int wrongCount = totalQuestions - correctCount;
        int score = (correctCount * 100) / totalQuestions; // Tính điểm %

        // Đưa dữ liệu vào Model để hiển thị trên giao diện
        model.addAttribute("correctAnswers", correctCount);
        model.addAttribute("wrongAnswers", wrongCount);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("score", score);
        model.addAttribute("message", "Bài làm đã được nộp thành công!");
return "Test/TestResult";
    }

}
