package xenoteo.com.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xenoteo.com.github.model.Lesson;
import xenoteo.com.github.model.Word;
import xenoteo.com.github.services.LessonService;
import xenoteo.com.github.services.WordService;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApplication {

    private final LessonService lessonService;

    private final WordService wordService;

    @Autowired
    public ConsoleApplication(LessonService lessonService, WordService wordService) {
        this.lessonService = lessonService;
        this.wordService = wordService;
    }

    public void run(){
        Scanner in = new Scanner(System.in);
        System.out.print("""
                Please choose an action to perform:
                1 for practice
                2 for new lesson
                3 to quit
                """);
        String choice = in.nextLine();
        switch (choice){
            case "1" -> practice();
            case "2" -> createNewLesson();
            case "3" -> quit();
            default -> run();
        }

    }

    private void createNewLesson(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the lesson name: ");
        String lessonName = in.nextLine();
        Lesson lesson = lessonService.addNewLesson(lessonName);

        System.out.println("Enter the word polish - german pairs (q to stop)");
        while (true){
            System.out.print("polish: ");
            String polish = in.nextLine();
            if (polish.equals("q")) {
                break;
            }
            System.out.print("german: ");
            String german = in.nextLine();
            Word word = wordService.addNewWord(polish.toLowerCase(), german.toLowerCase(), lesson);
            lessonService.addWordToLesson(lesson, word);
        }

        run();
    }

    private void practice() {
        List<Lesson> lessons = lessonService.findAll();
        System.out.println("Choose the lesson ID:");
        lessons.forEach(lesson -> System.out.printf("%d - %s\n", lesson.getId(), lesson.getName()));
        Scanner in = new Scanner(System.in);
        Long id = in.nextLong();
        Lesson lesson = lessonService.findById(id);
        if (lesson == null){
            System.out.println("Provide the correct lesson ID.");
            practice();
            return;
        }
        System.out.println("See the lesson vocabulary:");
        lesson.getWords().forEach(word -> System.out.printf("%s - %s\n", word.getGerman(), word.getPolish()));
        System.out.println("Press enter to start the practice");
        in.nextLine();
        in.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();

        run();
    }

    private void quit(){

    }
}
