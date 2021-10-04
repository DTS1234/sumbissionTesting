package uep.adam.kazmierczak;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author akazmierczak
 * @create 03.10.2021
 */
class ReaderTest {

    private Reader subject;

    @BeforeEach
    void setUp() {
        subject = new Reader();
    }

    @Test
    void shouldReturnWordsCountedThreeOneToOne() throws IOException {
        //given
        String filePath = "text.txt";
        //when
        Map<String, Long> actual = subject.execute(filePath).get(0).getWordsMap();
        //then
        boolean containsDog = actual.containsKey("dog");
        boolean containsTomato = actual.containsKey("tomato");
        boolean containsTractor = actual.containsKey("tractor");

        assertTrue(containsDog);
        assertTrue(containsTomato);
        assertTrue(containsTractor);

        boolean dogWordOccurredOnce = actual.get("dog") == 1;
        boolean tomatoWordOccurredOnce = actual.get("tomato") == 1;
        boolean tractorWordOccurredOnce = actual.get("tractor") == 1;

        assertTrue(dogWordOccurredOnce);
        assertTrue(tomatoWordOccurredOnce);
        assertTrue(tractorWordOccurredOnce);
    }

    @Test
    void shouldReturnEmptyMapIfFilesIsEmpty() throws IOException {
        //given
        String filePath = "text2.txt";
        //when
        Map<String, Long> actual = subject.execute(filePath).get(0).getWordsMap();
        //then
        assertTrue(actual.isEmpty());
    }

    @Test
    void shouldCountTheWordsThatAppearSeveralTimes() throws IOException {
        // given
        String filePath = "text3.txt";
        // when
        Map<String, Long> actual = subject.execute(filePath).get(0).getWordsMap();
        // then
        assertTrue(actual.containsKey("line"));
        assertTrue(actual.containsKey("word"));
        assertTrue(actual.containsKey("first"));
        assertTrue(actual.containsKey("second"));
        assertTrue(actual.containsKey("third"));
        assertTrue(actual.containsKey("fourth"));

        assertEquals(8, (long) actual.get("line"));
        assertEquals(4, (long) actual.get("word"));
        assertEquals(1, actual.get("first"));
        assertEquals(1, actual.get("second"));
        assertEquals(1, actual.get("third"));
        assertEquals(1, actual.get("fourth"));
    }

    @Test
    void shouldProperlyCountTheWordsWithMultipleSpacesBetweenTheWords() throws IOException {
        // given
        String filePath = "text4.txt";
        // when
        Map<String, Long> actual = subject.execute(filePath).get(0).getWordsMap();
        // then
        assertTrue(actual.containsKey("string"));
        assertTrue(actual.containsKey("line"));
        assertTrue(actual.containsKey("verification"));
        assertTrue(actual.containsKey("random"));
        assertTrue(actual.containsKey("hat"));
        assertTrue(actual.containsKey("fourth"));
        assertTrue(actual.containsKey("hope"));
        assertTrue(actual.containsKey("validation"));
        assertTrue(actual.containsKey("course"));
        assertTrue(actual.containsKey("word"));

        assertEquals(1L, actual.get("string"));
        assertEquals(1L, actual.get("verification"));
        assertEquals(1L, actual.get("course"));
        assertEquals(1L, actual.get("random"));
        assertEquals(1L, actual.get("hat"));
        assertEquals(1L, actual.get("word"));

        assertEquals(3L, actual.get("hope"));
        assertEquals(3L, actual.get("fourth"));
        assertEquals(3L, actual.get("validation"));

        assertEquals(9L, actual.get("line"));
    }

    @Test
    void shouldNotCountArticles() throws IOException {
        // given
        String filePath = "text5.txt";
        // when
        Map<String, Long> actual = subject.execute(filePath).get(0).getWordsMap();
        // then
        assertTrue(actual.containsKey("dog"));
        assertTrue(actual.containsKey("jumped"));
        assertTrue(actual.containsKey("from"));
        assertTrue(actual.containsKey("roof"));
        assertTrue(actual.containsKey("random"));
        assertTrue(actual.containsKey("nice"));
        assertTrue(actual.containsKey("city"));
        assertTrue(actual.containsKey("unhappy"));
        assertTrue(actual.containsKey("boy"));

        assertFalse(actual.containsKey("a"));
        assertFalse(actual.containsKey("an"));
        assertFalse(actual.containsKey("the"));
    }

    @Test
    void shouldNotCountPrepositions() throws IOException {
        // given
        String filePath = "text6.txt";
        // when
        Map<String, Long> actual = subject.execute(filePath).get(0).getWordsMap();
        // then
        assertTrue(actual.containsKey("end"));
        assertTrue(actual.containsKey("next"));
        assertTrue(actual.containsKey("year"));
        assertTrue(actual.containsKey("random"));
        assertTrue(actual.containsKey("word"));
        assertTrue(actual.containsKey("money"));
        assertTrue(actual.containsKey("jump"));
        assertTrue(actual.containsKey("look"));

        assertFalse(actual.containsKey("by"));
        assertFalse(actual.containsKey("of"));
        assertFalse(actual.containsKey("out"));
        assertFalse(actual.containsKey("on"));
        assertFalse(actual.containsKey("in"));
        assertFalse(actual.containsKey("at"));
    }

    @Test
    void shouldNotCountPronouns() throws IOException {
        // given
        String filePath = "text7.txt";
        // when
        Map<String, Long> actual = subject.execute(filePath).get(0).getWordsMap();
        // then
        for (int i = 0; i < GrammarConstraints.PRONOUNS.length; i++) {
            assertFalse(actual.containsKey(GrammarConstraints.PRONOUNS[i]));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"somePdfFile.pdf", "someWordFile.docx", "song.mp3", "script.sql"})
    void shouldPrintAMessageWhenFileIsNotATextFile(String wrongFilePath) {
        IllegalArgumentException actual = Assertions.assertThrows(IllegalArgumentException.class, () -> subject.execute(wrongFilePath));

        String expected = "The following file : " + wrongFilePath + " is not a .txt file !";
        assertEquals(expected, actual.getMessage());
    }

    @Test
    void shouldWorkForMultipleFiles() throws IOException {
        // given
        String[] filePaths = {"text.txt", "text2.txt", "text3.txt", "text4.txt", "text5.txt", "text6.txt", "text7.txt"};
        // when
        List<TextProperties> actual = subject.execute(filePaths);
        // then
        assertEquals(7, actual.size());
    }

    @Test
    void shouldNotCountPunctuationSigns() throws IOException {
        // given
        String[] filePaths = {"text8.txt"};
        // when
        List<TextProperties> actual = subject.execute(filePaths);
        // then
        assertEquals(1, actual.size());
        assertEquals("text8.txt", actual.get(0).getFileName());

        for (int i = 0; i < GrammarConstraints.PUNCTUATION.length; i++) {
            assertFalse(actual.get(0).getWordsMap().containsKey(GrammarConstraints.PUNCTUATION[i]));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"unexsisting.txt", "randomTHing.txt", "fileNotHere.txt"})
    void shouldThrowIfFileDoesNotExist(String wrongFilePath) {
        IllegalArgumentException actual = Assertions.assertThrows(IllegalArgumentException.class, () -> subject.execute(wrongFilePath));

        String expected = "The file named " + wrongFilePath + " does not exist !";
        assertEquals(expected, actual.getMessage());
    }
}
