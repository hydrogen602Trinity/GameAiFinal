/**
 * Author: Jonathan
 * 
 */
package students;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import snakes.Direction;
import students.qLearning.*;

public class QLearning implements Serializable {
    
    private HashMap<Tuple<State, Direction>, Double> qValues;

    private double alpha;
    private double discount;
    private double epsilon;

    private String stateName;

    public int test;

    public QLearning(double alpha, double discount, double epsilon, String stateName) {
        qValues = new HashMap<Tuple<State, Direction>, Double>();
        this.stateName = stateName;
        this.alpha = alpha;
        this.discount = discount;
        this.epsilon = epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public void debug() {
        System.out.println("{");
        for (Tuple<State, Direction> key: qValues.keySet()) {
            double val = qValues.get(key);
            System.out.println("\t" + key.toString() + ": " + val);
        }
        System.out.println("}");
    }

    public QLearning(double epsilon, String stateName) {
        this(0.25, 0.8, epsilon, stateName);
    }

    public QLearning(String stateName) {
        this(0.25, 0.8, 0.25, stateName);
    }

    public String getStateName() {
        return stateName;
    }

    public double getQValue(Tuple<State, Direction> key) {
        return qValues.getOrDefault(key, 0.0);
    }

    public double getQValue(State s, Direction d) {
        return getQValue(new Tuple<State, Direction>(s, d));
    }

    /**
     * Computes and returns the value of the state based on
     * the value of every possible action from that state
     * 
     * @param st The state to get the value of
     * @return the value of the state
     */
    public double computeValueFromQValues(State st) {
        Direction[] dirs = st.getLegalActions();
        if (dirs.length == 0) {
            return 0;
        }

        double bestValue = Double.NEGATIVE_INFINITY;
        for (Direction d: dirs) {
            double qValue = this.getQValue(st, d);
            if (qValue > bestValue) {
                bestValue = qValue;
            }
        }

        if (bestValue == Double.NEGATIVE_INFINITY) {
            throw new RuntimeException("This shouldn't ever happen. Direction should be non-zero length");
        }
        return bestValue;
    }

    /**
     * Returns the best possible move from the state
     * 
     * @param st the state to analyze
     * @return the best move or none if there are no valid moves
     */
    private Optional<Direction> computeBestAction(State st) {
        Direction[] dirs = st.getLegalActions();
        if (dirs.length == 0) {
            return Optional.empty();
        }

        double bestValue = Double.NEGATIVE_INFINITY;
        Direction bestDir = null;

        for (Direction d: dirs) {
            double qValue = this.getQValue(st, d);
            if (qValue > bestValue) {
                bestValue = qValue;
                bestDir = d;
            }
        }

        if (bestDir == null) {
            throw new RuntimeException("This shouldn't ever happen. Direction should be non-zero length");
        }
        return Optional.of(bestDir);
    }

    public Direction getMove(State st) {
        Random random = new Random();
        Direction[] allLegalDirs = st.getLegalActions();

        // random choice in case q learning didn't return anything
        Direction choice = allLegalDirs[random.nextInt(allLegalDirs.length)];

        Optional<Direction> d = computeBestAction(st);

        if (d.isPresent() && random.nextDouble() > epsilon) {
            //System.out.println("Got direction: " + d.get());
            choice = d.get();
        }

        return choice;
    }

    public Direction getOptimalMove(State st) {
        Random random = new Random();
        Direction[] allLegalDirs = st.getLegalActions();

        // random choice in case q learning didn't return anything
        Direction choice = allLegalDirs[random.nextInt(allLegalDirs.length)];

        Optional<Direction> d = computeBestAction(st);
        if (d.isPresent())
            return d.get();
        return choice;
    }

    /**
     * Updates the Q Value
     * 
     * @param st the current state
     * @param d the transitioning action
     * @param nextSt the state after the transition
     * @param reward the reward of the transition
     */
    public void updateQValue(State st, Direction d, State nextSt, double reward) {
       
        // self.values[(state, action)] = ((1-self.alpha) * self.values[(state, action)]) + self.alpha * (reward + self.discount * self.getValue(nextState))
        double qValue = ((1 - alpha) * this.getQValue(st, d)) + alpha * (reward + discount * this.computeValueFromQValues(nextSt));
        qValues.put(new Tuple<State, Direction>(st, d), qValue);
    }
    

}
