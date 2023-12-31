package com.notx2wice.puzzlemakers.repository.rds;

import com.notx2wice.puzzlemakers.repository.rds.data.Quiz;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizJpaRepository extends JpaRepository<Quiz, String> {
    List<Quiz> findAllByCategory(String category);

}
