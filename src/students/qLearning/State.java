/**
 * Author: Jonathan
 * 
 */
package students.qLearning;


import snakes.Direction;

public interface State {

    /**
     * Computes and returns an array of all valid moves from the current state
     * 
     * Right now it just returns everything (ToDo)
     * 
     * @return all valid directions
     */
    public Direction[] getLegalActions();
}
