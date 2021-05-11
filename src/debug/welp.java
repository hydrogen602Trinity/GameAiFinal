package debug;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import students.QLearning;

public class welp {
    
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        ObjectInputStream objectinputstream = null;
        FileInputStream streamIn = null;
        try {
            streamIn = new FileInputStream("q_AppleState_1.bin");
            System.out.println(streamIn);
            objectinputstream = new ObjectInputStream(streamIn);
            System.out.println(objectinputstream);
            Object o = objectinputstream.readObject();
            System.out.println(o);
            QLearning readCase = (QLearning) objectinputstream.readObject();
        }
        finally {
            if (streamIn != null)
                streamIn.close();
            if (objectinputstream != null)
                objectinputstream.close();
            
        }
        

    }
}
