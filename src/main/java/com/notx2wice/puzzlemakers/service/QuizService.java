package com.notx2wice.puzzlemakers.service;

import com.notx2wice.puzzlemakers.dto.QuizAndTF;
import com.notx2wice.puzzlemakers.dto.QuizDto;
import com.notx2wice.puzzlemakers.repository.PuzzlerJpaRepository;
import com.notx2wice.puzzlemakers.repository.QuizJpaRepository;
import com.notx2wice.puzzlemakers.repository.data.Puzzler;
import com.notx2wice.puzzlemakers.repository.data.Quiz;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.swing.Spring;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class QuizService {
    final private QuizJpaRepository quizeRepository;
    final private PuzzlerJpaRepository puzzlerRepository;

    public List<QuizDto> getQuizsAndIsSolved(String googleId) {
        System.out.println("googleId = " + googleId);
        List<QuizDto> quizDtos = new ArrayList<>();
        Optional<Puzzler> byGoogleId = puzzlerRepository.findByGoogleId(googleId);

        if(byGoogleId.isEmpty()) {
            System.out.println("빈값이래");
            return quizDtos;
        }
        String solvedQuizsinfo = byGoogleId.get().getSolvedQuizs();
        String nowSolvingQuizsinfo = byGoogleId.get().getSolvingQuizs();
        Set<String> solvedQuizsSet = new java.util.HashSet<>(Collections.emptySet());
        Set<String> nowSolvingQuizsSet = new java.util.HashSet<>(Collections.emptySet());
        solvedQuizsSet.addAll(Arrays.asList(solvedQuizsinfo.split(",")));
        nowSolvingQuizsSet.addAll(Arrays.asList(nowSolvingQuizsinfo.split(",")));
        List<Quiz> quizList = quizeRepository.findAll();
        for (Quiz quiz : quizList) {
            QuizDto quizDto = QuizDto.builder()
                    .id(quiz.getId())
                    .isNowSolving(nowSolvingQuizsSet.contains(quiz.getId()))
                    .isSolved(solvedQuizsSet.contains(quiz.getId()))
                    .build();
            quizDtos.add(quizDto);
        }
        return quizDtos;
    }

    public boolean isSingUped(String googleId) {
        return puzzlerRepository.findByGoogleId(googleId).isPresent();
    }

    public void signUp(String googleId, String nickName) {
        puzzlerRepository.save(Puzzler.builder()
                .googleId(googleId)
                .nickName(nickName)
                .solvedQuizs("")
                .solvingQuizs("")
                .maxQuizCount(3)
                .hintCount(0)
                .score(0)
                .solvingCount(0)
                .cash(0L)
                .build());
    }

    @Transactional
    public void addSolvedQuiz(String googleId, String quizId) {
        Optional<Puzzler> byGoogleId = puzzlerRepository.findByGoogleId(googleId);
        if(byGoogleId.isEmpty()) {
            return;
        }
        Puzzler puzzler = byGoogleId.get();
        String solvedQuizs = puzzler.getSolvedQuizs();
        String nowSolvingQuizs = puzzler.getSolvingQuizs();
        Set<String> solvedQuizsSet = new java.util.HashSet<>(Collections.emptySet());
        Set<String> nowSolvingQuizsSet = new java.util.HashSet<>(Collections.emptySet());
        solvedQuizsSet.addAll(Arrays.asList(solvedQuizs.split(",")));
        nowSolvingQuizsSet.addAll(Arrays.asList(nowSolvingQuizs.split(",")));
        solvedQuizsSet.add(quizId);
        nowSolvingQuizsSet.remove(quizId);
        puzzler.setSolvedQuizs(String.join(",", solvedQuizsSet));
        puzzler.setSolvingQuizs(String.join(",", nowSolvingQuizsSet));
        puzzler.setScore(puzzler.getScore() + 1);
        puzzler.setSolvingCount(puzzler.getSolvingCount() - 1);
        puzzlerRepository.save(puzzler);
    }

    @Transactional //todo get quiz랑 합치쟝... 호출이 엄청나겠군요...  나중에 넘 느리면 db를 다이나모 같은 걸로 써보자규.
    public QuizAndTF goQuiz(String googleId, String quizId) {
        System.out.println("googleId  = " + googleId + " quizId = " + quizId);
        Optional<Puzzler> byGoogleId = puzzlerRepository.findByGoogleId(googleId);
        if(byGoogleId.isEmpty()) {
            return QuizAndTF.builder().canIgo(false).quiz(null).build();
        }
        Puzzler puzzler = byGoogleId.get();
        String solvedQuizs = puzzler.getSolvedQuizs();
        String nowSolvingQuizs = puzzler.getSolvingQuizs();
        Set<String> solvedQuizsSet = new java.util.HashSet<>(Collections.emptySet());
        Set<String> nowSolvingQuizsSet = new java.util.HashSet<>(Collections.emptySet());
        solvedQuizsSet.addAll(Arrays.asList(solvedQuizs.split(",")));
        nowSolvingQuizsSet.addAll(Arrays.asList(nowSolvingQuizs.split(",")));
        // 퀴즈 풀수 있는가 확인 푸는 문재 중에 이싸
        if(nowSolvingQuizsSet.contains(quizId) || solvedQuizsSet.contains(quizId)) {
            return QuizAndTF.builder()
                .canIgo(true)
                .quiz(quizeRepository.getById(quizId))
                .build();
        }


        // 새로운 문제임 나에게 문제 풀수 있는 여분의 카운터가 있는지 확인.
        if(puzzler.getMaxQuizCount() >= puzzler.getSolvingCount()) {
            nowSolvingQuizs += "," + quizId;
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
