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
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Test")
public class TestController {
    private final QuestionService questionService;
    private final ExamsService examsService;
    private final QuestionContentService questionContentService;
    private static final int TEST_DURATION = 30 * 60;

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
    public String doExam(@PathVariable Long id, Model model, HttpSession session) {
        Exams exam = examsService.getExamById(id);
        if (exam == null) {
            model.addAttribute("error", "Bài kiểm tra không tồn tại!");
            return "errorPage";
        }

        // Lấy danh sách câu hỏi
        List<QuestionContent> questionContents = questionContentService.getQuestionContentsByExamId(id);
        List<Question> questions = new ArrayList<>();
        for (var questionContent : questionContents) {
            questions.addAll(questionService.getQuestionsByContentId(questionContent.getId()));
        }

        // Kiểm tra thời gian làm bài (tính theo giây)
        int examDurationSeconds = exam.getExamTime() * 60; // Đổi phút sang giây
        Long prevExamId = (Long) session.getAttribute("currentExamId");
        Long startTimeEpoch = (Long) session.getAttribute("startTime");

        if (prevExamId == null || !prevExamId.equals(id)) {
            startTimeEpoch = Instant.now().getEpochSecond();
            session.setAttribute("startTime", startTimeEpoch);
            session.setAttribute("currentExamId", id);
        }

        // Tính thời gian còn lại
        long elapsedSeconds = Instant.now().getEpochSecond() - startTimeEpoch;
        int timeRemaining = examDurationSeconds - (int) elapsedSeconds;

        if (timeRemaining <= 0) {
            return "redirect:/Test/submit";
        }

        model.addAttribute("timeRemainingSeconds", timeRemaining);
        model.addAttribute("exam", exam);
        model.addAttribute("danhsachQC", questionContents);
        model.addAttribute("questions", questions);
        return "Test/TestMain";
    }

    @PostMapping("/submit")
    public String submitAnswers(@RequestParam Map<String, String> selectedAnswers, Model model,HttpSession session) {
        Long id = (Long) session.getAttribute("idexame"); // Lấy giá trị từ session
        Exams exams = examsService.getExamById(id);
        List<QuestionContent> questionContents = questionContentService.getQuestionContentsByExamId(id);
        List<Question> questions = new ArrayList<>();
        for (var questionContent :  questionContents){
            questions.addAll(questionService.getQuestionsByContentId(questionContent.getId()));
        }
        System.out.println(questions);
        Map<String, String> correctAnswers = questions.stream()
                .collect(Collectors.toMap(q -> String.valueOf(q.getId()), Question::getCorrectAnswer));
        System.out.println("Correct Answers: " + correctAnswers);
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
        session.removeAttribute("startTime");
        model.addAttribute("correctAnswers", correctCount);
        model.addAttribute("wrongAnswers", wrongCount);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("score", score);
        model.addAttribute("message", "Bài làm đã được nộp thành công!");
        return "Test/TestResult";
    }
}
