package xenoteo.com.github.model;

import java.util.Map;
import java.util.Set;

/**
 * A class normalizing words, that is replacing Polish and German characters.
 */
public class Normalizer {

    /**
     * Map from Polish special characters to the latin equivalents.
     */
    private static final Map<Character, Character> polishSpecialMap = Map.of(
            'ą', 'a',
            'ć', 'c',
            'ę', 'e',
            'ł', 'l',
            'ń', 'n',
            'ó', 'o',
            'ś', 's',
            'ź', 'z',
            'ż', 'z');

    /**
     * Map from German special characters to the latin equivalents.
     */
    private static final Map<Character, Character> germanSpecialMap = Map.of(
            'ä', 'a',
            'ö', 'o',
            'ü', 'u'
            );

    /**
     * The set of Polish special characters.
     */
    private static final Set<Character> polishSpecial = polishSpecialMap.keySet();

    /**
     * The set of German special characters.
     */
    private static final Set<Character> germanSpecial = germanSpecialMap.keySet();

    /**
     * Replaces German or Polish special characters.
     *
     * @param word  the word to normalize
     * @return the normalized word
     */
    public static String normalize(String word){
        StringBuilder normalized = new StringBuilder();
        for (char c : word.toCharArray()){
            if (polishSpecial.contains(c))
                normalized.append(polishSpecialMap.get(c));
            else if (germanSpecial.contains(c))
                normalized.append(germanSpecialMap.get(c));
            else
                normalized.append(c);
        }
        return normalized.toString();
    }

}
