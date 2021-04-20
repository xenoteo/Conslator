package xenoteo.com.github.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(nullable = false)
    private String polish;

    @Column(nullable = false)
    private String german;

    @Column(nullable = false)
    private String polishWithoutPolishLetters;

    @Column(nullable = false)
    private String germanWithoutGermanLetters;

    public Word(String polish, String german) {
        this.polish = polish;
        this.german = german;
        this.polishWithoutPolishLetters = Normalizer.normalize(polish);
        this.germanWithoutGermanLetters = Normalizer.normalize(german);
    }

}
