package bigjava.parser;

import java.io.BufferedReader;
import java.io.IOException;

public class LinkParser implements Parser {

    private Integer caracter;
    private BufferedReader bfReader;

    public LinkParser(Integer caracter, BufferedReader bfReader) {
        this.caracter = caracter;
        this.bfReader = bfReader;
    }

    @Override
    public String parse() throws IOException {
        caracter = bfReader.read();
        final StringBuilder localStringBuilder = new StringBuilder();
        while (caracter != 9 && caracter != ' ') {
            final char cha = (char) (int) caracter;
            localStringBuilder.append(cha);
            caracter = bfReader.read();
        }
        caracter = bfReader.read();
        return localStringBuilder.toString();
    }

}
