package view;

public  abstract class AbstractView implements LobbyListener, MapObserver, PlayerObserver {

    public String notify(String message){
        return message;
    }
}
