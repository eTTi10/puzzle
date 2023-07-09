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
public class Puzzler {
    @Id
    String googleId;

    @Column(nullable = false)
    String nickName; //닉네임 변환에 캐쉬

    @Column
    String solvedQuizs; //푼 문제들

    @Column //동시 3개 까지 풀 수 있음.
    String solvingQuizs;

    @Column
    int solvingCount;

    @Column
    int hintCount;

    @Column
    int maxQuizCount;

    @Column
    int score;

    @Column
    Long cash;

}
