package uep.adam.kazmierczak;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void name() throws IOException {
        //given
        String filePath = "C:\\Users\\User\\Desktop\\EIT STUDIES\\VV\\sumbissionTesting\\text.txt";
        //when
        Map<String, Long> actual = subject.execute(filePath);
        //then
        boolean containsOne = actual.containsKey("1");
        boolean containsTwoAsAKey = actual.containsKey("2");
        boolean containsThreeAsAKey = actual.containsKey("3");

        assertTrue(containsOne);
        assertTrue(containsTwoAsAKey);
        assertTrue(containsThreeAsAKey);
    }
}
