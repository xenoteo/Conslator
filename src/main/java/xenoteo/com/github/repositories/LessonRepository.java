package xenoteo.com.github.repositories;

import org.springframework.data.repository.CrudRepository;
import xenoteo.com.github.model.Lesson;

public interface LessonRepository extends CrudRepository<Lesson, Long> {
}
