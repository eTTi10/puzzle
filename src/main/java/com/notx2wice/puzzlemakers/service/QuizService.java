package com.notx2wice.puzzlemakers.service;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.notx2wice.puzzlemakers.dto.QuizAndTF;
import com.notx2wice.puzzlemakers.dto.QuizDto;
import com.notx2wice.puzzlemakers.repository.dynamo.PuzzlerRepositoryInterface;
import com.notx2wice.puzzlemakers.repository.rds.QuizJpaRepository;
import com.notx2wice.puzzlemakers.repository.dynamo.data.Puzzler;
import com.notx2wice.puzzlemakers.repository.rds.data.Quiz;

import java.util.*;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class QuizService {
    final private QuizJpaRepository quizeRepository;
    final private PuzzlerRepositoryInterface puzzlerRepository;

    public List<QuizDto> getQuizsAndIsSolved(String googleId) {
        System.out.println("googleId = " + googleId);
        List<QuizDto> quizDtos = new ArrayList<>();
        Optional<Puzzler> byGoogleId = puzzlerRepository.findByGoogleId(googleId);

        if(byGoogleId.isEmpty()) {
            System.out.println("빈값이래");
            return quizDtos;
        }
        Set<String> solvedQuizsinfo = byGoogleId.get().getSolvedQuizs();
        Set<String> nowSolvingQuizsinfo = byGoogleId.get().getSolvingQuizs();


        List<Quiz> quizList = quizeRepository.findAll();
        for (Quiz quiz : quizList) {
            QuizDto quizDto = QuizDto.builder()
                    .id(quiz.getId())
                    .isNowSolving(nowSolvingQuizsinfo.contains(quiz.getId()))
                    .isSolved(solvedQuizsinfo.contains(quiz.getId()))
                    .build();
            quizDtos.add(quizDto);
        }
        return quizDtos;
    }

    public boolean isSingUped(String googleId) {
        return puzzlerRepository.findByGoogleId(googleId).isPresent();
    }

    public void signUp(String googleId, String nickName) {
        Set<String> firstSet = new HashSet<>();
        firstSet.add("sign_up_ is_first_puzzle_thx");
        Set<String> oneSet = new HashSet<>();
        oneSet.add("no_solution");

        puzzlerRepository.save(Puzzler.builder()
                .googleId(googleId)
                .nickName(nickName)
                .solvedQuizs(firstSet)
                .solvingQuizs(oneSet)
                .maxQuizCount(3)
                .hintCount(0)
                .score(0)
                .solvingCount(0)
                .cash(0)
                .isAdFree(false)
                .build());
    }

    @Transactional
    public void addSolvedQuiz(String googleId, String quizId) {
        Optional<Puzzler> byGoogleId = puzzlerRepository.findByGoogleId(googleId);
        if(byGoogleId.isEmpty()) {
            return;
        }
        Puzzler puzzler = byGoogleId.get();
        Set<String> solvedQuizs = puzzler.getSolvedQuizs().isEmpty()? new HashSet<>() : puzzler.getSolvedQuizs();
        Set<String>  nowSolvingQuizs = puzzler.getSolvingQuizs().isEmpty()? new HashSet<>() : puzzler.getSolvingQuizs();
        if (solvedQuizs.contains(quizId)) {
            return;
        }
        solvedQuizs.add(quizId);
        nowSolvingQuizs.remove(quizId);

        puzzler.setSolvedQuizs(solvedQuizs);
        puzzler.setSolvingQuizs(nowSolvingQuizs );
        puzzler.setScore(puzzler.getScore() + 1);
        puzzler.setSolvingCount(puzzler.getSolvingCount() - 1);

        puzzlerRepository.save(puzzler);
    }

    @Transactional
    public QuizAndTF goQuiz(String googleId, String quizId) {
        System.out.println("googleId  = " + googleId + " quizId = " + quizId);
        Optional<Puzzler> byGoogleId = puzzlerRepository.findByGoogleId(googleId);
        if(byGoogleId.isEmpty()) {
            return QuizAndTF.builder().canIgo(false).quiz(null).build();
        }
        Puzzler puzzler = byGoogleId.get();
        Set<String> solvedQuizs = puzzler.getSolvedQuizs();
        Set<String> nowSolvingQuizs = puzzler.getSolvingQuizs();

        // 퀴즈 풀수 있는가 확인 푸는 문재 중에 이싸
        if(nowSolvingQuizs.contains(quizId) || solvedQuizs.contains(quizId)) {
            return QuizAndTF.builder()
                .canIgo(true)
                .quiz(quizeRepository.getById(quizId))
                .build();
        }


        // 새로운 문제임 나에게 문제 풀수 있는 여분의 카운터가 있는지 확인.
        if(puzzler.getMaxQuizCount() >= puzzler.getSolvingCount()) {
            nowSolvingQuizs.add(quizId);
            puzzler.setSolvingQuizs(nowSolvingQuizs);
            puzzler.setSolvingCount(puzzler.getSolvingCount() + 1);
            puzzlerRepository.save(puzzler);
            return QuizAndTF.builder()
                .canIgo(true)
                .quiz(quizeRepository.getById(quizId))
                .build();
        }

        return QuizAndTF.builder().canIgo(false).quiz(null).build();
    }


}
