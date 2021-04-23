package students;

import java.util.HashMap;

import snakes.Direction;
import students.qLearning.*;

public class QLearning {
    
    private HashMap<Tuple<State, Direction>, Double> qValues;

    public QLearning() {
        qValues = new HashMap<Tuple<State, Direction>, Double>();
    }

    public double getQValue(Tuple<State, Direction> key) {
        return qValues.getOrDefault(key, 0.0);
    }

    public double getQValue(State s, Direction d) {
        return getQValue(new Tuple<State, Direction>(s, d));
    }

    

}
