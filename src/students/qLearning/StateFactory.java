/**
 * Author: Jonathan
 * 
 */
package students.qLearning;

import snakes.Coordinate;
import snakes.Snake;

public class StateFactory {
    
    public static State create(String name, Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        if (name.equals("AppleState")) {
            return new AppleState(snake, opponent, mazeSize, apple);
        }
        else if (name.equals("BasicState")) {
            return new BasicState(snake, opponent, mazeSize, apple);
        }
        else if (name.equals("AStarState")) {
            return new AStarState(snake, opponent, mazeSize, apple);
        }
        else {
            throw new IllegalArgumentException("What?, got: " + name);
        }
    }
}
