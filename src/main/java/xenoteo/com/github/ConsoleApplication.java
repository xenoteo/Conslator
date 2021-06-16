package xenoteo.com.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xenoteo.com.github.model.Lesson;
import xenoteo.com.github.model.Word;
import xenoteo.com.github.services.LessonService;
import xenoteo.com.github.services.WordService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        Scanner in = new Scanner(System.in);

        List<Lesson> lessons = lessonService.findAll();
        System.out.println("Choose the lesson ID:");
        lessons.forEach(lesson -> System.out.printf("%d - %s\n", lesson.getId(), lesson.getName()));

        Lesson lesson = getLessonFromInput();
        String direction = getDirectionFromInput();

        System.out.println("See the lesson vocabulary:");
        printWordPairs(lesson.getWords());

        System.out.println("Press enter to start the practice");
        in.nextLine();
        clearConsole();

        switch (direction) {
            case "1" -> practiceFromPolish(lesson);
            case "2" -> practiceFromGerman();
        }

        run();
    }

    private void practiceFromPolish(Lesson lesson) {
        List<Word> wordsToPractice = new ArrayList<>(lesson.getWords());
        Collections.shuffle(wordsToPractice);
        Scanner in = new Scanner(System.in);
        int errorCount = 0;

        while (!wordsToPractice.isEmpty()) {
            List<Word> problems = new ArrayList<>();
            for (Word word : wordsToPractice) {
                System.out.printf("%s - ", word.getPolish());
                String translation = in.nextLine().toLowerCase();
                if (!word.getGerman().equals(translation)
                        && !word.getGermanWithoutGermanLetters().equals(translation)){
                    errorCount++;
                    problems.add(word);
                }
            }
            wordsToPractice = problems;
            if (!wordsToPractice.isEmpty()) {
                System.out.println("There are some more words to practice. Check these words out:");
                printWordPairs(wordsToPractice);
                System.out.println("Press enter to continue the practice");
                in.nextLine();
                clearConsole();
            }
            Collections.shuffle(wordsToPractice);

        }

        if (errorCount == 0){
            System.out.println("Brilliant!");
        }
        else {
            System.out.println("Well done!");
        }
    }

    private void printWordPairs(Iterable<Word> words) {
        words.forEach(word ->
                ColoredOutput.printlnBlue(String.format("%s - %s", word.getGerman(), word.getPolish())));
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private Lesson getLessonFromInput() {
        Scanner in = new Scanner(System.in);
        Lesson lesson;
        try {
            long id = in.nextLong();
            lesson = lessonService.findById(id);
            while (lesson == null){
                System.out.println("Provide the correct lesson ID.");
                id = in.nextLong();
                lesson = lessonService.findById(id);
            }
            return lesson;
        } catch (Exception e){
            System.out.println("Provide the correct lesson ID.");
            return getLessonFromInput();
        }
    }

    private String getDirectionFromInput() {
        Scanner in = new Scanner(System.in);
        System.out.print("""
                Choose a direction:
                1 for polish -> german
                2 for german -> polish
                """);
        String choice = in.nextLine();
        while (!choice.equals("1") && !choice.equals("2")){
            System.out.println("Provide the correct direction.");
            choice = in.nextLine();
        }
        return choice;
    }

    private void practiceFromGerman() {

    }

    private void quit(){

    }
}
