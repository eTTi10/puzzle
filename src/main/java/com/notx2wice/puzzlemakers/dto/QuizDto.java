package com.notx2wice.puzzlemakers.dto;


import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class QuizDto {

    //Se-1 , Iq-1, Ma-1, Uni-1
    String id;

    Boolean isSolved;

    Boolean isNowSolving;
}

