package bigjava.parser;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageParser implements Parser {

    private Integer caracter;
    private BufferedReader bfReader;

    public MessageParser(Integer caracter, BufferedReader bfReader) {
        this.caracter = caracter;
        this.bfReader = bfReader;
    }

    @Override
    public String parse() throws IOException {
        final StringBuilder localStringBuilder = new StringBuilder();
        char anterior = '0';
        while (caracter != '\n') {
            final char cha = (char) (int) caracter;
            localStringBuilder.append(cha);
            if (anterior == '.' && (cha == ' ' || cha == 9 || cha == '\n')) {
                break;
            }
            anterior = cha;
            caracter = bfReader.read();
        }
        caracter = bfReader.read();
        return localStringBuilder.toString();
    }

}
