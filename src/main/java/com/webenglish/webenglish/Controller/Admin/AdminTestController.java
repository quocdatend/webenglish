package com.webenglish.webenglish.Controller.Admin;

import com.webenglish.webenglish.Model.Exams;
import com.webenglish.webenglish.Model.Question;
import com.webenglish.webenglish.Model.QuestionContent;
import com.webenglish.webenglish.Service.ExamsService;
import com.webenglish.webenglish.Service.QuestionContentService;
import com.webenglish.webenglish.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/TestAdmin")
public class AdminTestController {
    private final ExamsService examsService;
    private final QuestionContentService questionContentService;
    private final QuestionService questionService;
    public AdminTestController(ExamsService examsService, QuestionContentService questionContentService, QuestionService questionService) {
        this.examsService = examsService;
        this.questionContentService = questionContentService;
        this.questionService = questionService;
    }
    @GetMapping("/Exam")
    public String HomeAdminTest(Model model){
        List<Exams> exams = examsService.getAllExams(); // Example service call
        System.out.println(exams);
        model.addAttribute("exams", exams);
        return "Admin/Test/TestIndex";
    }
    //create
    //update
    @GetMapping("/Exam/Edit/{id}")
    public String editExam(@PathVariable("id") long examId, Model model) {
        List<Exams> exams = examsService.getAllExams();
        Exams exam = examsService.getExamById(examId);
        model.addAttribute("exams", exams);
        model.addAttribute("exam", exam);
        return "Admin/Test/TestIndex";
    }
    @PostMapping("/Exam/Save")
    public String Edit(@ModelAttribute Exams exam) {
        if (exam.getId() != 0) {
            Exams existingExam = examsService.getExamById(exam.getId());
            existingExam.setExamName(exam.getExamName());
            existingExam.setExamTime(exam.getExamTime());
            existingExam.setExamDetails(exam.getExamDetails());
            examsService.saveExam(existingExam);
        } else {
            examsService.saveExam(exam);
        }
        return "redirect:/TestAdmin/Exam";
    }
    //delete
    @PostMapping("/Exam/Delete/{id}")
    public String deleteExam(@PathVariable("id") long examId) {
        examsService.deleteExam(examId);
        return "redirect:/TestAdmin/Exam";
    }


    //QuestionContent
//    @GetMapping("/Exam/{id}")
//    public String showQuestionContent(@PathVariable("id") long examId, Model model) {
//        System.out.println(examId);
//        Exams exam = examsService.getExamByIdOrThrow(examId);
//        List<QuestionContent> questionContents = questionContentService.getQuestionContentsByExamId(examId);
//        model.addAttribute("exam", exam);
//        model.addAttribute("questionsContent", questionContents);
//        return "Admin/Test/Question_content";
//    }

    @GetMapping("/Exam/{id}")
    public String showQuestionContent(@PathVariable("id") long examId, Model model) {
        System.out.println(examId);
        Exams exam = examsService.getExamByIdOrThrow(examId);
        System.out.println(exam);
        List<QuestionContent> questionContents = questionContentService.getQuestionContentsByExamId(examId);
        System.out.println(questionContents);
        model.addAttribute("exam", exam);
        model.addAttribute("questionsContent", questionContents);
        return "Admin/Test/Question_content";
    }

    @PostMapping("/Exam/UpdateQuestionContent/{id}")
    public String updateQuestionContent(@PathVariable("id") long QCid, @ModelAttribute QuestionContent questionContent,  Model model) {
        QuestionContent existingQC = questionContentService.getQuestionContentById(QCid);
        Exams exam = examsService.getExamById(existingQC.getExam().getId());
        List<QuestionContent> questionContents = questionContentService.getQuestionContentsByExamId(exam.getId());
        model.addAttribute("questionsContent", questionContents);
        model.addAttribute("exam", exam);
        model.addAttribute("QC",existingQC);
        return "Admin/Test/Question_content";
    }

    //UPDATE
    @PostMapping("/Exam/SaveQuestionContent")
    public String saveQuestionContent(@ModelAttribute QuestionContent questionContent, @RequestParam("idexam") Long examId) {
        System.out.println("ẽamid"+examId);
        if (questionContent.getId() != 0) {
            QuestionContent existingQC = questionContentService.getQuestionContentById(questionContent.getId());
            if (existingQC != null) {
                Exams exam = examsService.getExamById(examId);
                existingQC.setTextContent(questionContent.getTextContent());
                existingQC.setExam(exam);
                existingQC.setQuestionStyle(questionContent.getQuestionStyle());
                existingQC.setPicture(questionContent.getPicture());
                existingQC.setAudio(questionContent.getAudio());
                existingQC.setBigQuestionText(questionContent.getBigQuestionText());
                questionContentService.saveQuestionContent(existingQC);
            }
        } else {
            System.out.println(examsService.getExamById(examId));
            questionContent.setExam(examsService.getExamById(examId));
            questionContentService.saveQuestionContent(questionContent);
        }
        return "redirect:/TestAdmin/Exam/" + examId;
    }

    //DATELE
    @PostMapping("/Exam/DeleteQuestionContent/{id}")
    public String deleteQuestionContent(@PathVariable("id") long QCid) {
        System.out.println(QCid);
        QuestionContent questionContent = questionContentService.getQuestionContentById(QCid);
        if (questionContent != null) {
            long examId = questionContent.getExam().getId(); // Get exam ID before deletion
            questionContentService.deleteQuestionContent(QCid);
            return "redirect:/TestAdmin/Exam/" + examId; // Redirect to the exam's question content list
        }
        return "redirect:/TestAdmin/Exam";
    }
    //QUESTION
    @PostMapping("/exams/qc/{id}")
    public String listQuestions(Model model,@PathVariable("id") long QCid ) {
        model.addAttribute("questions", questionService.getQuestionsByContentId(QCid));
        model.addAttribute("QC",questionContentService.getQuestionContentById(QCid));
        return "Admin/Test/Question";
    }
    @PostMapping("/exams/qc/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return "Admin/Test/TestIndex";
    }
    @GetMapping("/exams/qc/update/{id}")
    public String updateQuestion(@PathVariable("id") long Questionid, Model model) {
        Question existingQ = questionService.getQuestionById(Questionid);
        model.addAttribute("question",existingQ);
        return "Admin/Test/Question";
    }
    @PostMapping("/exams/qc/saveQuestion")
    public String SaveQuestion(@ModelAttribute Question question,@RequestParam("idqc") Long idQc ) {
        System.out.println("Received idqc: " + idQc);
//        if (question.getId() != 0) {
//            Question existingQuestion = questionService.getQuestionById(question.getId());
//            if (existingQuestion != null) {
//                existingQuestion.setQuestionText(question.getQuestionText());
//                existingQuestion.setAnswerA(question.getAnswerA());
//                existingQuestion.setAnswerB(question.getAnswerB());
//                existingQuestion.setAnswerC(question.getAnswerC());
//                existingQuestion.setAnswerD(question.getAnswerD());
//                existingQuestion.setCorrectAnswer(question.getCorrectAnswer());
//                existingQuestion.setContent(question.getContent());
//                questionService.saveQuestion(existingQuestion);
//                System.out.println("Question updated: " + existingQuestion);
//            } else {
//                System.out.println("Question not found with ID: " + question.getId());
//            }
//        } else {
//            questionService.saveQuestion(question);
//            System.out.println("New question saved: " + question);
//        }
        return "Admin/Test/Question";
    }
}
