package com.webenglish.webenglish.Model;

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
@Table(name = "EXAMS")
public class Exams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXAMID")
    private long id;

    @Column(name = "EXAMNAME", length = 100)
    private String examName;

    @Column(name = "EXAMTIME")
    private Integer examTime;

    @Column(name = "EXAMSDETAIL", length = 100)

    private String examDetails;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<QuestionContent> questionContents;

}
