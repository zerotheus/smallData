package bigjava;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import bigjava.hibernate.Postgres;
import bigjava.model.Dados;
import bigjava.model.Local;
import bigjava.model.Mensagem;
import bigjava.parser.DateParser;
import bigjava.parser.MessageParser;
import bigjava.parser.NumberParser;
import bigjava.parser.Parser;

public class App {

    private Integer caracter;
    private BufferedReader bfReader;
    private final StringBuilder stringBuilder = new StringBuilder();
    private Postgres postgres = new Postgres();

    public static void main(String[] args) throws IOException {
        new App().parser();
    }

    public void parser() throws IOException {
        bfReader = new BufferedReader(new FileReader("app/src/main/java/bigjava/data/checkin_data_foursquare.txt"));
        caracter = bfReader.read();
        postgres.start();
        int i = 0;
        while (caracter != -1 && caracter != null) {
            try {
                final String str = lineReader();
                if (str == null) {
                    postgres.close();
                    break;
                }
            } catch (Exception e) {
                System.out.println("Linha " + i + "com erro" + e.toString());
            }
            i++;
        }
    }

    public String lineReader() throws IOException {
        final long id, messageId;
        final double latitude, longitude;
        final LocalDateTime dataHora;
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final Parser numberParser = new NumberParser(caracter, bfReader),
                messageParser = new MessageParser(caracter, bfReader), dateParser = new DateParser(caracter, bfReader);
        id = Long.parseLong(numberParser.parse());
        messageId = Long.parseLong(numberParser.parse());
        latitude = Double.parseDouble(numberParser.parse());
        longitude = Double.parseDouble(numberParser.parse());
        dataHora = LocalDateTime.parse(dateParser.parse(), formatter);
        final Dados dado = new Dados();
        dado.setMensagem(new Mensagem(messageId, messageParser.parse(), id));
        if (bfReader.readLine() == null) {
            return null;
        }
        dado.setLocal(new Local(latitude, longitude));
        dado.setDataHora(dataHora);
        // System.out.println("Dado: " + dado);
        postgres.open();
        postgres.save(dado);
        return stringBuilder.toString();
    }

}
