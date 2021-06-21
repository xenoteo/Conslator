package xenoteo.com.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xenoteo.com.github.model.Lesson;
import xenoteo.com.github.model.Normalizer;
import xenoteo.com.github.model.Word;
import xenoteo.com.github.services.LessonService;
import xenoteo.com.github.services.WordService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * The console application.
 */
@Component
public class ConsoleApplication {

    private final LessonService lessonService;

    private final WordService wordService;

    private final NewLessonCreator newLessonCreator;

    private final PracticeRunner practiceRunner;

    @Autowired
    public ConsoleApplication(LessonService lessonService, WordService wordService) {
        this.lessonService = lessonService;
        this.wordService = wordService;
        this.newLessonCreator = new NewLessonCreator();
        this.practiceRunner = new PracticeRunner();
    }

    /**
     * The main function running the application.
     */
    public void run(){
        Scanner in = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.print("""
                
                Please choose an action to perform:
                1 for practice
                2 for new lesson
                3 to quit
                """);
            String choice = in.nextLine();
            switch (choice){
                case "1" -> practiceRunner.choosePractice();
                case "2" -> newLessonCreator.createNewLesson();
                case "3" -> {
                    running = false;
                    quit();
                }
            }
        }
    }

    /**
     * The class responsible for creating new lessons.
     */
    private class NewLessonCreator {

        /**
         * Gets input from the user and creates a new lesson.
         */
        private void createNewLesson(){
            Scanner in = new Scanner(System.in);
            System.out.print("Enter the lesson name (b to go back to the main menu): ");
            String lessonName = in.nextLine();
            if (lessonName.equals("b")) {
                return;
            }
            Lesson lesson = lessonService.addNewLesson(lessonName);

            System.out.println("Enter the word Polish - German pairs (s to stop)");
            while (true){
                System.out.print("Polish: ");
                String polish = in.nextLine();
                if (polish.equals("s")) {
                    break;
                }
                System.out.print("German: ");
                String german = in.nextLine();
                Word word = wordService.addNewWord(polish.toLowerCase(), german.toLowerCase(), lesson);
                lessonService.addWordToLesson(lesson, word);
            }

            if (lesson.getWords().isEmpty()) {
                lessonService.delete(lesson);
            }
        }
    }

    /**
     * A class responsible for running a practice.
     */
    private class PracticeRunner {

        /**
         * Gets input from the user, chooses a lesson to practice and starts a practice.
         */
        private void choosePractice() {
            Scanner in = new Scanner(System.in);

            List<Lesson> lessons = lessonService.findAll();
            if (lessons.isEmpty()) {
                System.out.println("There are no lessons to practice yet. Add a new lesson to start practice!");
                return;
            }
            System.out.println("Choose the lesson ID (b to go back to the main menu):");
            lessons.forEach(lesson -> System.out.printf("%d - %s\n", lesson.getId(), lesson.getName()));

            Lesson lesson = getLessonFromInput();
            if (lesson == null) {
                return;
            }
            String direction = getDirectionFromInput();

            System.out.println("See the lesson vocabulary:");
            printWordPairs(lesson.getWords());

            System.out.println("Press enter to start the practice");
            in.nextLine();
            clearConsole();

            practice(lesson, direction);

        }

        /**
         * Gets the input from the user, which is necessary to choose a lesson to practice.
         *
         * @return the chosen lesson
         */
        private Lesson getLessonFromInput() {
            Scanner in = new Scanner(System.in);
            Lesson lesson;
            try {
                String idString = in.nextLine();
                if (idString.equals("b")) {
                    return null;
                }
                long id = Long.parseLong(idString);
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

        /**
         * Gets from the user a direction of the practice (Polish -> German or German -> Polish).
         *
         * @return the chosen direction
         */
        private String getDirectionFromInput() {
            Scanner in = new Scanner(System.in);
            System.out.print("""
                Choose a direction:
                1 for Polish -> German
                2 for German -> Polish
                """);
            String choice = in.nextLine();
            while (!choice.equals("1") && !choice.equals("2")){
                System.out.println("Provide the correct direction.");
                choice = in.nextLine();
            }
            return choice;
        }

        /**
         * Prints word pairs.
         *
         * @param words  the words to print
         */
        private void printWordPairs(Iterable<Word> words) {
            words.forEach(word ->
                    ColoredOutput.printlnBlue(String.format("%s - %s", word.getGerman(), word.getPolish())));
        }

        /**
         * Clears the console.
         */
        private void clearConsole() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

        /**
         * Runs the practice until there is no mistakes made by the user.
         *
         * @param lesson  the chosen lesson
         * @param direction  the chosen direction
         */
        private void practice(Lesson lesson, String direction) {
            Scanner in = new Scanner(System.in);

            List<Word> wordsToPractice = new ArrayList<>(lesson.getWords());
            Collections.shuffle(wordsToPractice);
            int errorCount = 0;

            while (!wordsToPractice.isEmpty()) {
                if (direction.equals("1")) {
                    wordsToPractice = practiceFromPolish(wordsToPractice);
                }
                else {
                    wordsToPractice = practiceFromGerman(wordsToPractice);
                }
                errorCount += wordsToPractice.size();
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

        /**
         * Runs practice from Polish to German and remembers the words where user made mistakes.
         *
         * @param wordsToPractice  the list of words to practice
         * @return the problem words (the ones with mistakes)
         */
        private List<Word> practiceFromPolish(List<Word> wordsToPractice) {
            Scanner in = new Scanner(System.in);
            List<Word> problems = new ArrayList<>();
            for (Word word : wordsToPractice) {
                System.out.printf("%s - ", word.getPolish());
                String translation = in.nextLine().toLowerCase();
                if (!word.getGerman().equals(translation)
                        && !word.getGermanWithoutGermanLetters().equals(translation)){
                    problems.add(word);
                }
            }
            return problems;
        }

        /**
         * Runs practice from German to Polish and remembers the words where user made mistakes.
         *
         * @param wordsToPractice  the list of words to practice
         * @return the problem words (the ones with mistakes)
         */
        private List<Word> practiceFromGerman(List<Word> wordsToPractice) {
            Scanner in = new Scanner(System.in);
            List<Word> problems = new ArrayList<>();
            for (Word word : wordsToPractice) {
                System.out.printf("%s - ", word.getGerman());
                String translation = in.nextLine().toLowerCase();
                if (!word.getPolish().equals(translation)
                        && !word.getPolishWithoutPolishLetters().equals(translation)
                        && !word.getPolishWithoutPolishLetters().equals(Normalizer.normalize(translation))) {
                    problems.add(word);
                }
            }
            return problems;
        }
    }

    /**
     * Quits the program.
     */
    private void quit(){
        System.out.println("See you next time!");
    }
}
