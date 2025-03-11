package com.webenglish.webenglish.Repository;

import com.webenglish.webenglish.Model.QuestionContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionContentRepository extends JpaRepository<QuestionContent, Long> {

}
