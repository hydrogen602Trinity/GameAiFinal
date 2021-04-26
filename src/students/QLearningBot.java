package students;

import java.io.Serializable;
import java.util.Optional;
import java.util.Random;

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;
import students.qLearning.BasicState;
import students.qLearning.State;
import students.qLearning.Tuple;
import students.qLearning.UtilStuff;

public class QLearningBot implements Bot, Serializable {
    private static final Direction[] DIRECTIONS = new Direction[] {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

    private QLearning qStuff;
    private Random random;
    

    public QLearningBot() {
        Optional<QLearning> perhabs = UtilStuff.readObject("q.bin");

        if (perhabs.isPresent()) {
            qStuff = perhabs.get();
        }
        else {
            qStuff = new QLearning();
        }

        random = new Random();
    }


    @Override
    public Direction chooseDirection(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        qStuff.test += 1;
        State st = new BasicState(snake, opponent, mazeSize, apple);

        Optional<Direction> d = qStuff.computeBestAction(st);

        // random choice in case q learning didn't return anything
        Direction choice = DIRECTIONS[random.nextInt(DIRECTIONS.length)];

        if (d.isPresent()) {
            System.out.println("Got direction: " + d.get());
            choice = d.get();
        }

        Tuple<Double, State> out = getValueAndState(snake, opponent, mazeSize, apple, choice);
        double moveScore = out._0;
        State nextSt = out._1;

        System.out.println("Value of move is: " + moveScore);

        qStuff.updateQValue(st, choice, nextSt, moveScore);

        return choice;
    }

    private Tuple<Double, State> getValueAndState(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple, Direction d) {
        Snake snake0 = snake.clone();
        Snake snake1 = opponent.clone();

        // if snake ate an apple - good
        boolean grow0 = snake0.getHead().moveTo(d).equals(apple);

        // if snake died - very bad
        boolean s0dead = !snake0.moveTo(d, grow0);
        s0dead |= snake0.headCollidesWith(snake1);
        
        // if snake killed other opponent - good
        boolean s1dead = snake1.headCollidesWith(snake0);

        int appleEatPoints = (grow0) ? 5 : 0;

        int deathPoints = (s0dead) ? -100 : 0;

        int killPoints = (s1dead) ? 100 : 0;

        int livingPoints = 1; // being alive is good

        double points = appleEatPoints + deathPoints + killPoints + livingPoints;

        State nextSt = new BasicState(snake0, snake1, mazeSize, apple);

        return new Tuple<Double, State>(points, nextSt);
    }


    @Override
    public void cleanup(int winner) {
        // TODO Auto-generated method stub

        qStuff.debug();
        System.out.println("Debug counter: " + qStuff.test);

        UtilStuff.writeObject(qStuff, "q.bin");
        
    }
}