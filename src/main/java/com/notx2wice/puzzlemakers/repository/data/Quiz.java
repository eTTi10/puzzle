package com.notx2wice.puzzlemakers.repository.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id //Se-1 , Iq-1, Ma-1, Uni-1
    String id;

    @Column
    String quizImageUrl;

    @Column
    String quizAnswer;

    @Column
    String category; // serial 1, mensa 2, math 3, unique-think 4  todo Enum으로 전환

}
