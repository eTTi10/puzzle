package com.notx2wice.puzzlemakers.controller;

import com.notx2wice.puzzlemakers.dto.QuizAndTF;
import com.notx2wice.puzzlemakers.dto.QuizDto;
import com.notx2wice.puzzlemakers.service.QuizService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api
@RequiredArgsConstructor
@RestController
public class QuizController {
    private final QuizService quizService;

    @ApiOperation(value = "퀴즈 리스트를 가져옵니다.") // Swagger 사용
    @GetMapping("quizlist")
    public List<QuizDto> getQuizs(@RequestParam(value = "id") String googleId) {
        return quizService.getQuizsAndIsSolved(googleId);
    }


    @ApiOperation(value = "회원가입을 합니다.")
    @PostMapping("signup")
    public boolean signUp(@RequestParam(value = "id") String googleId , @RequestParam(value ="nick_name") String nickName) {
        if (quizService.isSingUped(googleId)){
            return false;
        }
        quizService.signUp(googleId, nickName);
        return true;
    }

    @ApiOperation(value = "퀴즈를 풀었을 때 푼 문제로 등록합니다.")
    @PostMapping("solved")
    public boolean solved(@RequestParam(value = "id") String googleId , @RequestParam(value ="quiz_id") String quizId) {
        quizService.addSolvedQuiz(googleId, quizId);
        return true;
    }

    @ApiOperation("리스트의 퀴즈를 눌렀을 때 내가 풀수 있는 퀴즈인지 확인하고 풀수있는 문제일 때 문제를 가져옵니다.")
    @GetMapping("goQuiz")
    public QuizAndTF canIsolve(@RequestParam(value = "id") String googleId , @RequestParam(value ="quiz_id") String quizId) {
        return quizService.goQuiz(googleId, quizId);
    }

}
