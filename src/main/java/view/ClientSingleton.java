package view;

public class ClientSingleton {

    private static ClientSingleton instance;

    private long token;

    private ClientSingleton(){}

    public static ClientSingleton getInstance(){
        if (instance == null){
            instance = new ClientSingleton();
        }
        return instance;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }
}
