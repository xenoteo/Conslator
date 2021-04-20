package xenoteo.com.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xenoteo.com.github.model.Lesson;
import xenoteo.com.github.model.Word;
import xenoteo.com.github.services.LessonService;
import xenoteo.com.github.services.WordService;

@Component
public class DataLoader {

    private final LessonService lessonService;

    private final WordService wordService;

    @Autowired
    public DataLoader(LessonService lessonService, WordService wordService) {
        this.lessonService = lessonService;
        this.wordService = wordService;
    }

    public void populateData(){
        Lesson lesson = lessonService.addNewLesson("lektion 1");
        Word word = wordService.addNewWord("robić", "machen");
        lessonService.addWordToLesson(lesson, word);
    }
}
