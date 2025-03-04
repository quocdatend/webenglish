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
@Table(name = "QUESTION")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUESTIONID")
    private Integer id;

    @Column(name = "QUESTIONTEXT", columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "ANSWERA", columnDefinition = "TEXT")
    private String answerA;

    @Column(name = "ANSWERB", columnDefinition = "TEXT")
    private String answerB;

    @Column(name = "ANSWERC", columnDefinition = "TEXT")
    private String answerC;

    @Column(name = "ANSWERD", columnDefinition = "TEXT")
    private String answerD;

    @Column(name = "CORRECTANSWER", length = 1)
    private String correctAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENTID")
    private QuestionContent content;
}
