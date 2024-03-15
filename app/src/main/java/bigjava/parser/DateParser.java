package bigjava.parser;

import java.io.BufferedReader;
import java.io.IOException;

public class DateParser implements Parser {

    private Integer caracter;
    private BufferedReader bfReader;

    public DateParser(Integer caracter, BufferedReader bfReader) {
        this.caracter = caracter;
        this.bfReader = bfReader;
    }

    @Override
    public String parse() throws IOException {
        final StringBuilder localStringBuilder = new StringBuilder();
        while (caracter != 9) {
            final char cha = (char) (int) caracter;
            localStringBuilder.append(cha);
            caracter = bfReader.read();
        }
        caracter = bfReader.read();
        return localStringBuilder.toString();
    }

}
