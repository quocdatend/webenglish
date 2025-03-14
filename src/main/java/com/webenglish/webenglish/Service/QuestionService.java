package com.webenglish.webenglish.Service;

import com.webenglish.webenglish.Model.Question;
import com.webenglish.webenglish.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    // Lấy tất cả câu hỏi
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Lấy câu hỏi theo ID
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    // Lấy danh sách câu hỏi theo Content ID
    public List<Question> getQuestionsByContentId(Long contentId) {
        return questionRepository.findByContentId(contentId);
    }

    // Thêm hoặc cập nhật câu hỏi
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    // Xóa câu hỏi theo ID
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
