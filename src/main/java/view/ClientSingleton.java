package view;

public class ClientSingleton {

    private static ClientSingleton instance;

    private String token = "";

    private ClientSingleton() {
    }

    public static ClientSingleton getInstance() {
        if (instance == null) {
            instance = new ClientSingleton();
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
