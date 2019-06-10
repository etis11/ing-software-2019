import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import java.io.PrintWriter;

public class jsonParser extends PApplet {


    String path = "/home/oscar/Scrivania/";
    String fileName = "maxenti.json";
    String imageName = "maxenti.png";
    int rows = 3;
    int cols = 4;
    Tile[][] matrix;
    int tileSize = 200;
    int currentRoom = 0;
    boolean oldKeyPressed = false;
    boolean oldRightMousePressed = false;
    boolean doorMode = false;
    boolean quit = false;
    String[] colors = {"red", "blue", "yellow"};
    int currentRegenPoint = 0;
    int steps = 0;
    Tile currentTile = null;
    String[] playersNames = {"Banshee", "Dozer", "Distruttore", "Sprog", "Violetta"};
    int playerCount;

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"jsonparser"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }

    public void settings() {
        int height = tileSize * rows;
        int width = tileSize * cols;
        size(width, height);

    }

    public void setup() {
        textAlign(CENTER, CENTER);
        textSize(50);
        frameRate(30);
        background(0);
        matrix = new Tile[rows][cols];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = new Tile(i * cols + j);
            }

    }

    public void draw() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                matrix[i][j].show(j * tileSize, i * tileSize);
            }

        oldKeyPressed = keyPressed;

        if (quit) quit();


    }

    public void keyPressed() {

        if (!oldKeyPressed && key == 'r') currentRoom += 1;
        if (!oldKeyPressed && key == 'd') doorMode = true;
        if (!oldKeyPressed && key == 'q') quit = true;
        if (!oldKeyPressed && key == 'p') {
            int j = pmouseX / tileSize;
            int i = pmouseY / tileSize;
            if (matrix[i][j].active && playerCount < playersNames.length) {
                Player p = new Player(playersNames[playerCount]);
                matrix[i][j].addPlayer(p);
                playerCount++;
                println("Added " + p.name);

            }
        }


    }

    public void mouseClicked() {

        if (!doorMode) {
            //left click, tile normale
            if (mouseButton == LEFT) {
                int j = pmouseX / tileSize;
                int i = pmouseY / tileSize;
                if (!matrix[i][j].active) {
                    matrix[i][j].activateTile();
                    matrix[i][j].ammoTile = true;
                    return;
                }
            }

            //right click, weapon tile
            if (mouseButton == RIGHT) {
                if (currentRegenPoint < 3) {
                    int j = pmouseX / tileSize;
                    int i = pmouseY / tileSize;
                    if (!matrix[i][j].active) {
                        matrix[i][j].activateTile();
                        matrix[i][j].regenPoint = colors[currentRegenPoint];
                        matrix[i][j].weaponTile = true;
                        currentRegenPoint += 1;
                        return;
                    }
                }
            }

        }//inizio door mode

        if (mouseButton == LEFT) {
            int j = pmouseX / tileSize;
            int i = pmouseY / tileSize;
            if (matrix[i][j].active) {
                if (steps == 0) {
                    currentTile = matrix[i][j];
                    steps = 1;
                    println("scelto current tile", currentTile);
                } else if (steps == 1) {
                    Tile newTile = matrix[i][j];
                    println("scelto new tile", newTile);
                    if (currentTile.room != newTile.room) {
                        currentTile.door.add(newTile);
                        newTile.door.add(currentTile);
                        println("evviva", currentTile, newTile);
                        println(currentTile.door, newTile.door);
                        currentTile = null;
                        steps = 0;

                    }

                }
            }

        }


    }

    public void quit() {
        Tile t;
        //devo controllare che sia possibile arrivare dappertutto
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j].active) {
                    t = matrix[i][j];
                    println("analizzo " + t, t.active);
                    //controllo sopra (north)
                    if (i - 1 >= 0) {
                        assignID(t, i - 1, j, "north");
                    }


                    //controllo sotto (south)
                    if (i + 1 < rows) {
                        assignID(t, i + 1, j, "south");
                    }


                    //controllo a sinistra (west)
                    if (j - 1 >= 0) {
                        assignID(t, i, j - 1, "west");
                    }


                    //controllo a destra (east)
                    if (j + 1 < cols) {
                        assignID(t, i, j + 1, "east");
                    }
                }


            }
        }

        List<Tile> lista = new LinkedList<Tile>();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j].active) lista.add(matrix[i][j]);
            }

        GameMap gameMap = new GameMap(currentRoom + 1, lista);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String s = gson.toJson(gameMap);
        //println(s);
        PrintWriter output = createWriter(path + fileName);
        output.println(s);
        output.flush();
        output.close();
        println(s.length());
        save(path + imageName);
        exit();

    }

    public void assignID(Tile t, int i, int j, String dir) {
        if (!matrix[i][j].active) {
            //println("sono qua", t);
            return;
        }
        if (t.room == matrix[i][j].room) {
            t.setNeigh(matrix[i][j].ID, dir);
        }
        //se non sono della stessa stanza, controllo che ci sia la porta
        else if (t.room != matrix[i][j].room && t.door.contains(matrix[i][j])) {
            t.setNeigh(matrix[i][j].ID, dir);
        } else if (t.room != matrix[i][j].room) {
            t.setWalls(matrix[i][j].ID, dir);
        }

    }

    class GameMap {
        int n_rooms;
        int n_rows;
        int n_cols;
        List<Tile> map;

        public GameMap() {
            this.n_rows = rows;
            this.n_cols = cols;
            map = null;
        }

        public GameMap(int n_rooms, List<Tile> mappa) {
            this.n_rooms = n_rooms;
            this.n_rows = rows;
            this.n_cols = cols;
            this.map = mappa;
        }
    }

    class Player {
        String name = "";

        public Player() {
        }

        public Player(String nome) {
            this.name = nome;
        }
    }

    class Tile {
        transient boolean active;
        int ID;
        int north;
        int east;
        int south;
        int west;
        int northWalled;
        int eastWalled;
        int southWalled;
        int westWalled;
        boolean ammoTile;
        boolean weaponTile;
        int room;
        String regenPoint;
        transient List<Tile> door;
        transient boolean checked = false;
        List<Player> players = new LinkedList<Player>();

        public Tile() {
            active = false;
            ID = 0;
            north = -1;
            east = -1;
            south = -1;
            west = -1;
            northWalled = -1;
            eastWalled = -1;
            southWalled = -1;
            westWalled = -1;
            ammoTile = false;
            weaponTile = false;
            room = -1;
            regenPoint = "";
            door = null;
        }

        public Tile(int ID) {
            active = false;
            this.ID = ID;
            north = -1;
            east = -1;
            south = -1;
            west = -1;
            northWalled = -1;
            eastWalled = -1;
            southWalled = -1;
            westWalled = -1;
            ammoTile = false;
            weaponTile = false;
            room = -1;
            regenPoint = "";
            door = new LinkedList<Tile>();
        }

        private void assignColor() {
            if (!active) {
                fill(255);
                return;
            }

            switch (room) {
                case -1:
                    fill(255);
                case 0:
                    //rosso
                    fill(192, 57, 43);
                    break;
                case 1:
                    //blu /azzurro
                    fill(41, 128, 185);
                    break;
                case 2:
                    //giallo
                    fill(254, 202, 87);
                    break;
                case 3:
                    //verde smeraldo
                    fill(46, 204, 113);
                    break;
                case 4:
                    //ametista
                    fill(155, 89, 182);
                    break;
                case 5:
                    //grigio
                    fill(127, 140, 141);
                    break;
                default:
                    fill(244, 0, 161);
            }

        }

        public void addPlayer(Player p) {
            players.add(p);
        }

        public void show(int xpos, int ypos) {
            this.assignColor();
            rect(xpos, ypos, tileSize, tileSize);
            fill(0);
            text(ID, xpos + tileSize / 2, ypos + tileSize / 2);
            if (regenPoint != "") ellipse(xpos + 50, ypos + 50, 40, 40);

            stroke(125);
            for (Tile t : door) {
                //la y è la posizione rispetto alle righe
                int yt = (t.ID / cols) * tileSize;
                //la x è la posizione rispetto alle colonne
                int xt = (t.ID % cols) * tileSize;
                strokeWeight(5);
                line(xpos + tileSize / 2, ypos + tileSize / 2, xt + tileSize / 2, yt + tileSize / 2);
                strokeWeight(1);
            }
            stroke(0);

            fill(51);
            for (int i = 0; i < players.size(); i++) {
                ellipse(xpos + (i + 1) * 10, ypos + (i + 1) * 10, 20, 20);

            }
        }

        public void activateTile() {
            active = true;
            room = currentRoom;
        }

        public void setNeigh(int id, String dir) {
            switch (dir) {
                case "north":
                    north = id;
                    break;
                case "east":
                    east = id;
                    break;
                case "south":
                    south = id;
                    break;
                case "west":
                    west = id;
                    break;
                default:
                    println("ERRORE");
            }

        }


        public void setWalls(int id, String dir) {
            switch (dir) {
                case "north":
                    northWalled = id;
                    break;
                case "east":
                    eastWalled = id;
                    break;
                case "south":
                    southWalled = id;
                    break;
                case "west":
                    westWalled = id;
                    break;
                default:
                    println("ERRORE");
            }
        }

        public String toString() {
            return String.valueOf(this.ID);
        }

    }
}
