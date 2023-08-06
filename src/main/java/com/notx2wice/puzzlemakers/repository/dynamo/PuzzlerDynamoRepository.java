package com.notx2wice.puzzlemakers.repository.dynamo;

import com.notx2wice.puzzlemakers.repository.dynamo.data.Puzzler;
import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface PuzzlerDynamoRepository extends CrudRepository<Puzzler, String> {
    Optional<Puzzler> findByGoogleId(String  id);
}
