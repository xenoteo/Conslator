package xenoteo.com.github.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * A class representing a word. A word contains full German and Polish versions,
 * as well as normalized Polish and German versions.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String polish;

    @Column(nullable = false)
    private String german;

    @Column(nullable = false)
    private String polishWithoutPolishLetters;

    @Column(nullable = false)
    private String germanWithoutGermanLetters;

    @ManyToOne
    private Lesson lesson;

    public Word(String polish, String german, Lesson lesson) {
        this.polish = polish;
        this.german = german;
        this.polishWithoutPolishLetters = Normalizer.normalize(polish);
        this.germanWithoutGermanLetters = Normalizer.normalize(german);
        this.lesson = lesson;
    }

}
