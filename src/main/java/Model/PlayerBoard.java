package Model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;



public class PlayerBoard {
    private Loader loader;
    private List<BloodToken> damagePoints;
    private List<BloodToken> marks;
    private final int MAX_DAMAGE_POINTS = 12;
    private LinkedList<Integer> points ;

    public PlayerBoard(){
        loader = new Loader();
        damagePoints = new LinkedList<>();
        marks = new LinkedList<>();
        points = new LinkedList<>().addAll(IntStream.of(8,6,4,2,1,1,1).boxed().collect(Collectors.toList());
    }

    public int getNumDamagePoints(){
        return damagePoints.size();
    }

    public int getNumMarksOfPlayer(Player p){
        return  (int) marks.stream().filter(mark -> mark.getOwner()==p).count();
    }

    public int getNumDamagePoints(Player p){
        return (int) damagePoints.stream().filter(damage ->damage.getOwner() == p).count();
    }

    public Loader getLoader(){
        return loader;
    }


    public void calculateDamage(DamageTransporter d){
        //TODO
    }

    private void MarkToDamage(){
        //TODO
    }
}
