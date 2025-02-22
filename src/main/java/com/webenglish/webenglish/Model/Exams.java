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
public class Exams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXAMID")
    private Integer id;

    @Column(name = "EXAMNAME", length = 100)
    private String examName;
}
