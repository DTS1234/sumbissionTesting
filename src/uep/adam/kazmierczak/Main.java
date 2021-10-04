package uep.adam.kazmierczak;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Reader reader = new Reader();

        try {
            List<TextProperties> execute = reader.execute(args);

            for (TextProperties textProperty : execute) {
                System.out.println(textProperty);
            }

        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        } catch (IOException ioException) {
            System.out.println("Something gone wrong with, try again !");
            System.out.println(ioException.getMessage());
        }



    }

}
