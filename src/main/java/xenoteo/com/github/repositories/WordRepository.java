package xenoteo.com.github.repositories;

import org.springframework.data.repository.CrudRepository;
import xenoteo.com.github.model.Word;

/**
 * The word repository.
 */
public interface WordRepository extends CrudRepository<Word, Long> {
}
