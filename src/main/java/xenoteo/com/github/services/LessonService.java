package xenoteo.com.github.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xenoteo.com.github.model.Lesson;
import xenoteo.com.github.model.Word;
import xenoteo.com.github.repositories.LessonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    private Lesson save(Lesson lesson){
        return lessonRepository.save(lesson);
    }

    public void delete(Lesson lesson) {
        lessonRepository.delete(lesson);
    }

    public List<Lesson> findAll(){
        return StreamSupport.stream(lessonRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Lesson findById(Long id){
        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        return lessonOptional.isEmpty() ? null : lessonOptional.get();
    }

    public Lesson addNewLesson(String lessonName){
        return save(new Lesson(lessonName));
    }

    public void addWordToLesson(Lesson lesson, Word word){
        lesson.getWords().add(word);
        lessonRepository.save(lesson);
    }

}
