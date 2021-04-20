package xenoteo.com.github.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xenoteo.com.github.model.Word;
import xenoteo.com.github.repositories.WordRepository;

@Service
public class WordService {

    private final WordRepository wordRepository;

    @Autowired
    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    private Word save(Word word){
        return wordRepository.save(word);
    }

    public Word addNewWord(String polish, String german){
        return save(new Word(polish, german));
    }
}
