package com.webenglish.webenglish.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "UserResponses")
@Getter
@Setter
public class UserResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ResponseID")
    private Integer responseId;

//    @ManyToOne
//    @JoinColumn(name = "UserID", referencedColumnName = "ID")
//    private Account user;

    @ManyToOne
    @JoinColumn(name = "ExamID", referencedColumnName = "EXAMID")
    private Exams exam;

    @ManyToOne
    @JoinColumn(name = "QuestionID", referencedColumnName = "QuestionID")
    private Question question;

    @Column(name = "UserAnswer", length = 1)
    private String userAnswer;

    @Column(name = "IsCorrect")
    private Integer isCorrect;
}
