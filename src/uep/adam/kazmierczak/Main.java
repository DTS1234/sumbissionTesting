package uep.adam.kazmierczak;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        List<TextProperties> execute = reader.execute(args);

        for (TextProperties textProperty:
             execute) {
            System.out.println(textProperty);
        }

    }

}
