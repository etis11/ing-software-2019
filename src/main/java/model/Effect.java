package model;

import java.util.List;

public abstract class Effect {
    private List<String> cost;
    private boolean isOptional;
    private boolean isGlobal;


    private AbstractTargetStrategy strategy ;


    public abstract void  useEffect(List<Player> p);

    public AbstractTargetStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(AbstractTargetStrategy strategy) {
        this.strategy = strategy;
    }


    public List<String> getCost() {
        return cost;
    }

    public void setCost(List<String> cost) {
        this.cost = cost;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }


    public boolean isOptional() {
        return isOptional;
    }

    public void setOptional(boolean optional) {
        isOptional = optional;
    }
}
