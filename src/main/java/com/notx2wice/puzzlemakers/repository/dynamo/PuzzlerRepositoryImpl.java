package com.notx2wice.puzzlemakers.repository.dynamo;

import com.notx2wice.puzzlemakers.repository.dynamo.data.Puzzler;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public class PuzzlerRepositoryImpl implements PuzzlerRepositoryInterface{
    private final PuzzlerDynamoRepository puzzlerDynamoRepository;

    public PuzzlerRepositoryImpl(PuzzlerDynamoRepository puzzlerDynamoRepository) {
        this.puzzlerDynamoRepository = puzzlerDynamoRepository;
    }

    @Override
    public Optional<Puzzler> findByGoogleId(String id) {
        return puzzlerDynamoRepository.findByGoogleId(id);
    }

    @Override
    public void save(Puzzler build) {
        puzzlerDynamoRepository.save(build);
    }

}
