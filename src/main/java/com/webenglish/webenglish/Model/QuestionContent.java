package com.webenglish.webenglish.Model;
import com.webenglish.webenglish.Model.Exams;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "QUESTIONS_CONTENT")

public class QuestionContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENTID")
    private long id;

    @Column(name = "QUESTIONS_STYLE", length = 100)
    private String questionStyle;

    @Column(name = "PICTURE", length = 255)
    private String picture;

    @Column(name = "TEXT_CONTENT", length = 255)
    private String textContent;

    @Column(name = "AUDIO", length = 255)
    private String audio;

    @Column(name = "Text_questionsbig_ifhave", columnDefinition = "TEXT")
    @Lob
    private String bigQuestionText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXAMID")
    @ToString.Exclude
    private Exams exam;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Question> questions;


}
