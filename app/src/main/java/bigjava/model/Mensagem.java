package bigjava.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Mensagem {

    private long messageId;
    private String message;
    private long senderId;

    public Mensagem() {
    }

    public Mensagem(long messageId, String message, long senderId) {
        this.messageId = messageId;
        this.message = message;
        this.senderId = senderId;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return "Mensagem mId=" + messageId + ", message=" + message + ".";
    }
}
