package xenoteo.com.github.model;

import java.util.Map;
import java.util.Set;

public class Normalizer {

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

    private static final Map<Character, Character> germanSpecialMap = Map.of(
            'ä', 'a',
            'ö', 'o',
            'ü', 'u'
            );

    private static final Set<Character> polishSpecial = polishSpecialMap.keySet();

    private static final Set<Character> germanSpecial = germanSpecialMap.keySet();

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
