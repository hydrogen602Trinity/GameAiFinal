package students.qLearning;

import snakes.Coordinate;
import snakes.Snake;

public class State {
    // relative to snake head and direction of snake!

    private Coordinate appleRel;

    private int[] wallDistance;

    public State(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        Coordinate center = snake.getHead();
        //appleRel = new Coordinate(apple.x - center.x, apple.y - center.y);

        //wallDistance = new int[4];


    }
}
