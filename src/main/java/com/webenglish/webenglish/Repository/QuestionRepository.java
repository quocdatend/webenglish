package com.webenglish.webenglish.Repository;

import com.webenglish.webenglish.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByContentId(Long contentId); // Lấy câu hỏi theo content ID
}
