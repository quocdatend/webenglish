package com.webenglish.webenglish.Repository;

import com.webenglish.webenglish.Model.Exams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamsRepository extends JpaRepository<Exams, Long> {
    Exams findExamsById(Long examId);
}
