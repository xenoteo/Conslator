package xenoteo.com.github.repositories;

import org.springframework.data.repository.CrudRepository;
import xenoteo.com.github.model.Lesson;

/**
 * The lesson repository.
 */
public interface LessonRepository extends CrudRepository<Lesson, Long> {
}
