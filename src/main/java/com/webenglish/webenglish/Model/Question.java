package com.webenglish.webenglish.Model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "EXAMS")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QuestionID")
    private Integer id;

    @Column(name = "QCID", nullable = false)
    private Integer qcid;

    @Column(name = "QuestionText", columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "AnswerA", columnDefinition = "TEXT")
    private String answerA;

    @Column(name = "AnswerB", columnDefinition = "TEXT")
    private String answerB;

    @Column(name = "AnswerC", columnDefinition = "TEXT")
    private String answerC;

    @Column(name = "AnswerD", columnDefinition = "TEXT")
    private String answerD;

    @Column(name = "CorrectAnswer", length = 1)
    private String correctAnswer;
}
