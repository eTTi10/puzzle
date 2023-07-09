package com.notx2wice.puzzlemakers.repository;

import com.notx2wice.puzzlemakers.repository.data.Puzzler;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuzzlerJpaRepository extends JpaRepository<Puzzler, String> {
    Optional<Puzzler> findByGoogleId(String  id);
}
