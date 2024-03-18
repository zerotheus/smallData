package bigjava.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Dados {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Mensagem mensagem;
    private LocalDateTime dataHora;
    @ManyToOne
    private Local local;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public double getLatitude() {
        return this.getLocal().getLatitude();
    }

    public double getLongitude() {
        return this.getLocal().getLongitude();
    }

    @Override
    public String toString() {
        return "Dados id=" + id + ", " + mensagem.toString() + ", " + local.toString();
    }
}
