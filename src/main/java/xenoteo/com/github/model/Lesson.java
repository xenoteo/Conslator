package xenoteo.com.github.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A class representing a lesson, that is a set of pairs of words.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Word> words = new HashSet<>();

    public Lesson(String name) {
        this.name = name;
    }
}
