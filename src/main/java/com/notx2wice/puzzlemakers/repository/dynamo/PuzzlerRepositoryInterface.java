package com.notx2wice.puzzlemakers.repository.dynamo;

import com.notx2wice.puzzlemakers.repository.dynamo.data.Puzzler;

import java.util.Optional;

public interface PuzzlerRepositoryInterface {
    Optional<Puzzler> findByGoogleId(String  id);

    void save(Puzzler build);
}
