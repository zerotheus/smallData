package bigjava.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import bigjava.model.Dados;
import bigjava.model.Local;
import bigjava.model.Mensagem;

public class Postgres {

    private SessionFactory sessionFactory;
    private Configuration configuration;
    private Session session;

    public void start() {
        configuration = new Configuration().addAnnotatedClass(Dados.class).addAnnotatedClass(Mensagem.class)
                .addAnnotatedClass(Local.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public void open() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    public void save(Dados dado) {
        session.persist(dado);
        session.getTransaction().commit();
    }

    public void close() {
        session.close();
    }

}
