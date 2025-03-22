package com.webenglish.webenglish.Service;

import com.webenglish.webenglish.Model.Exams;
import com.webenglish.webenglish.Repository.ExamsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamsService {
    private final ExamsRepository examsRepository;

    public ExamsService(ExamsRepository examsRepository) {
        this.examsRepository = examsRepository;
    }

    public List<Exams> getAllExams() {
        return examsRepository.findAll();
    }

    public Exams getExamById(Long id) {
        return examsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));
    }

    public Exams saveExam(Exams exam) {
        return examsRepository.save(exam);
    }

    public void deleteExam(long id) {
        examsRepository.deleteById(id);
    }
    public Exams getExamByIdOrThrow(long examId) {
        return examsRepository.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with id: " + examId));
    }

}
