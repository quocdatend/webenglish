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
    public List<QuestionContent> getAllQuestionContents() {
        return questionContentRepository.findAll();
    }
    public List<QuestionContent> getQuestionContentsByExamId(long examId) {
        return questionContentRepository.findByExamId(examId);
    }
    public int quantity_QC_byexamid(long examId){
        return questionContentRepository.findByExamId(examId).size();
    }
}
