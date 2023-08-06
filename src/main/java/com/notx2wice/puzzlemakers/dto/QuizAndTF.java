package com.notx2wice.puzzlemakers.dto;

import com.notx2wice.puzzlemakers.repository.rds.data.Quiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class QuizAndTF {
    public Quiz quiz;
    public Boolean canIgo;
}
