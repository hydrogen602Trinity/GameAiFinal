/**
 * Author: Jonathan
 * 
 */
package students.qLearning;

import java.io.*;
import java.util.Optional;

import snakes.Direction;

public class UtilStuff {

    public static final Direction[] DIRECTIONS = new Direction[] {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
    
    private UtilStuff() {}

    // public static computeRelCoordWithRotate(Coordinate source, Coordinate src, Direction facing) {

    // }

    public static <T> void writeObject(T st, String file) {
        //https://stackoverflow.com/questions/17293991/how-to-write-and-read-java-serialized-objects-into-a-file
        //https://stackoverflow.com/questions/4409100/how-to-make-a-java-generic-method-static

        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
        try{
            fout = new FileOutputStream(file, false);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(st);
        } catch (NotSerializableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                oos.flush();
                oos.close();

                fout.flush();
                fout.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }

    public static <T> Optional<T> readObject(String file) {
        ObjectInputStream objectinputstream = null;
        try {
            FileInputStream streamIn = new FileInputStream(file);
            objectinputstream = new ObjectInputStream(streamIn);
            T readCase = (T) objectinputstream.readObject();
            return Optional.of(readCase);
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find file");
            return Optional.empty();
        } 
        catch (NotSerializableException e) {
            e.printStackTrace();
            System.exit(1);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("yeet");
        } finally {
            try {
                if(objectinputstream != null){
                    objectinputstream.close();
                } 
            }
            catch (IOException e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
    }

    public static Direction getInverseDir(Direction d) {
        switch (d) {
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            case RIGHT:
                return Direction.LEFT;
            case LEFT:
                return Direction.RIGHT;
            default:
                return null;
        }

    }
}
