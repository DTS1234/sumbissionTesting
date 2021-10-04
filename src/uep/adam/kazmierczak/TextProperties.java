package uep.adam.kazmierczak;

import java.util.Map;

/**
 * @author akazmierczak
 * @create 03.10.2021
 */
public class TextProperties {

    private Map<String, Long> wordsMap;
    private String fileName;

    public TextProperties(Map<String, Long> wordsMap, String fileName) {
        this.fileName = fileName;
        this.wordsMap = wordsMap;
    }

    @Override
    public String toString() {
        return "TextProperties { \nword : count = \n" + getWordsMapString() +
                "FILE NAME ='" + fileName + '\'' +
                "\n}";
    }

    public Map<String, Long> getWordsMap() {
        return wordsMap;
    }

    public String getFileName() {
        return fileName;
    }

    private String getWordsMapString() {
        StringBuilder stringBuilder = new StringBuilder();

        wordsMap.forEach((word, count) ->
                stringBuilder.append("\t").append(word).append(" : ").append(count).append("\n")
        );
        return stringBuilder.toString();
    }

}
