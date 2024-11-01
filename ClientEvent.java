import java.util.EventObject;

public class ClientEvent extends EventObject {
    private final String clientId;
    private final String message;

    // Constructor para eventos sin mensaje (como conexión o desconexión)
    public ClientEvent(Object source, String clientId) {
        super(source);
        this.clientId = clientId;
        this.message = null;
    }

    // Constructor para eventos con mensaje (como mensaje recibido)
    public ClientEvent(Object source, String clientId, String message) {
        super(source);
        this.clientId = clientId;
        this.message = message;
    }

    public String getClientId() {
        return clientId;
    }

    public String getMessage() {
        return message;
    }
}
