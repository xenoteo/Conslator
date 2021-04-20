package xenoteo.com.github.repositories;

import org.springframework.data.repository.CrudRepository;
import xenoteo.com.github.model.Word;

public interface WordRepository extends CrudRepository<Word, Long> {
}
