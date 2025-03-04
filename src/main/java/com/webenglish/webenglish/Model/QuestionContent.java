package com.webenglish.webenglish.Model;
import com.webenglish.webenglish.Model.Exams;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "QUESTIONS_CONTENT")
@Getter
@Setter
public class QuestionContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENTID")
    private Integer id;

    @Column(name = "QUESTIONS-STYLE", columnDefinition = "ntext")
    private String questionStyle;

    @Column(name = "PICTURE", columnDefinition = "nvarchar(max)")
    private String picture;

    @Column(name = "TEXT-CONTENT", length = 500)
    private String textContent;

    @Column(name = "ADUDI", columnDefinition = "nvarchar(max)")
    private String audio;

    @Column(name = "Text-questionsbig-ifhave", columnDefinition = "ntext")
    private String bigQuestionText;

    @ManyToOne
    @JoinColumn(name = "EXAMID", referencedColumnName = "EXAMID")
    private Exams exam;
}
