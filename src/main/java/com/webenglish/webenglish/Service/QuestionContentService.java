package com.webenglish.webenglish.Service;

import com.webenglish.webenglish.Model.QuestionContent;
import com.webenglish.webenglish.Repository.QuestionContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionContentService {

    @Autowired
    private QuestionContentRepository questionContentRepository;

    // Retrieve all question contents
    public List<QuestionContent> getAllQuestionContents() {
        return questionContentRepository.findAll();
    }

    // Retrieve question content by ID
//    public QuestionContent getQuestionContentById(long id) {
//        Optional<QuestionContent> questionContent = questionContentRepository.findById(id);
//        return questionContent.orElse(null);
//    }


//    public List<QuestionContent> getQuestionContentsByExamId(long examId) {
//        return questionContentRepository.findAllById();
//    }

    // Save or update question content
    public QuestionContent saveQuestionContent(QuestionContent questionContent) {
        return questionContentRepository.save(questionContent);
    }

//    // Delete question content by ID with validation
//    public boolean deleteQuestionContent(Integer id) {
//        if (questionContentRepository.existsById(id)) {
//            questionContentRepository.deleteById(id);
//            return true;
//        }
//        return false; // ID not found
//    }
}
