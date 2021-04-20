package xenoteo.com.github.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xenoteo.com.github.model.Lesson;
import xenoteo.com.github.model.Word;
import xenoteo.com.github.repositories.LessonRepository;

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

    public Lesson addNewLesson(String lessonName){
        return save(new Lesson(lessonName));
    }

    public void addWordToLesson(Lesson lesson, Word word){
        lesson.getWords().add(word);
        save(lesson);
    }

}
