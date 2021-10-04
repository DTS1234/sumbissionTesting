package uep.adam.kazmierczak;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static uep.adam.kazmierczak.GrammarConstraints.*;

/**
 * @author akazmierczak
 * @create 03.10.2021
 */
public class Reader {

    public List<TextProperties> execute(String... files) throws IOException {

        List<TextProperties> result = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {

            Path path = Paths.get(files[i]);

            if (!path.getFileName().toString().endsWith(".txt")) {
                throw new IllegalArgumentException("The following file : " + path.getFileName() + " is not a .txt file !");
            }

            List<String> lines;

            try {
                lines = Files.readAllLines(path.toAbsolutePath());
            } catch (NoSuchFileException exception) {
                throw new IllegalArgumentException("The file named " + path.getFileName() + " does not exist !");
            }

            HashMap<String, Long> stringLongHashMap = new HashMap<>();
            List<String> wordsAlreadyInFile = new ArrayList<>();

            lines.forEach(line -> {
                String[] words = line.split(" ");
                Arrays.stream(words)
                        .filter(word -> !Arrays.asList(ARTICLES).contains(word))
                        .filter(word -> !Arrays.asList(PREPOSITIONS).contains(word))
                        .filter(word -> !Arrays.asList(PRONOUNS).contains(word))
                        .filter(word -> !isPunctuationChain(word))
                        .forEach(word -> {


                            if (wordsAlreadyInFile.contains(word)) {
                                increaseWordCount(stringLongHashMap, word);
                            }
                            stringLongHashMap.putIfAbsent(word, 1L);
                            wordsAlreadyInFile.add(word);

                        });
            });

            result.add(new TextProperties(stringLongHashMap, files[i]));
        }

        return result;
    }

    private void increaseWordCount(HashMap<String, Long> stringLongHashMap, String word) {
        Long aLong = stringLongHashMap.get(word);
        stringLongHashMap.put(word, ++aLong);
    }

    private boolean isPunctuationChain(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isLetterOrDigit(text.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
