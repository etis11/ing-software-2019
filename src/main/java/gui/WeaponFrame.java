package gui;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class WeaponFrame {

    private Stage stage;
    private List<String> players;

    private Label weapon11;
    private Label weapon12;
    private Label weapon13;
    private List<Label> firstW;

    private Label weapon21;
    private Label weapon22;
    private Label weapon23;
    private List<Label> secondW;

    private Label weapon31;
    private Label weapon32;
    private Label weapon33;
    private List<Label> thirdW;

    public WeaponFrame(List<String> players) {
        this.stage = new Stage();
        this.players = players;
        generate();
    }

    private void generate(){

        generateFirst();



        weapon21 = new Label();
        weapon22 = new Label();
        weapon23 = new Label();


        weapon31 = new Label();
        weapon32 = new Label();
        weapon33 = new Label();




    }

    private void generateFirst(){
        weapon11 = new Label();
        weapon12 = new Label();
        weapon13 = new Label();
        firstW = new ArrayList<>();
        firstW.add(weapon11);
        firstW.add(weapon12);
        firstW.add(weapon13);

        for (Label l:firstW){

        }
    }
}
