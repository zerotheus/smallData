package bigjava.hibernate;

import bigjava.model.Dados;

public class PostgresThread implements Runnable {

    private Postgres postgres;
    private Dados dado;

    public PostgresThread(Postgres postgres, Dados dados) {
        this.postgres = postgres;
        this.dado = dados;
    }

    @Override
    public void run() {
        postgres.open();
        postgres.save(dado);
        postgres.close();
    }

}
