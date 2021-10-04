package uep.adam.kazmierczak;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author akazmierczak
 * @create 03.10.2021
 */
public class Reader {

    public Map<String, Long> execute(String... files) throws IOException {

        Path path = Paths.get(files[0]);
        List<String> lines = Files.readAllLines(path.toAbsolutePath());



        HashMap<String, Long> stringLongHashMap = new HashMap<>();
        lines.forEach(line -> {
            String[] words = line.split(" ");
            Arrays.stream(words).forEach(word -> stringLongHashMap.putIfAbsent(word,1L));
        } );

        return stringLongHashMap;
    }

}
