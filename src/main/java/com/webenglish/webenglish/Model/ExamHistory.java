package com.webenglish.webenglish.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "ExamHistory")
@Getter
@Setter
public class ExamHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ScoreID")
    private Integer scoreId;

//    @ManyToOne
//    @JoinColumn(name = "UserID", referencedColumnName = "ID", nullable = false)
//    private Account user;

    @ManyToOne
    @JoinColumn(name = "ExamID", referencedColumnName = "EXAMID", nullable = false)
    private Exams exam;

    @Column(name = "StartTime")
    private LocalDateTime startTime;

    @Column(name = "EndTime")
    private LocalDateTime endTime;

    @Column(name = "Score", nullable = false)
    private Double score;
}
