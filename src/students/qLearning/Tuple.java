/**
 * Author: Jonathan
 * 
 */
package students.qLearning;

import java.io.Serializable;

public class Tuple<A, B> implements Serializable {

    public A _0;
    public B _1;

    public Tuple(A a, B b) {
        _0 = a;
        _1 = b;
    }

    public Tuple<A, B> of(A a, B b) {
        return new Tuple<A, B>(a, b);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Tuple<?, ?> && (((Tuple<?, ?>) o)._0.equals(this._0) && ((Tuple<?, ?>) o)._1.equals(this._1));
    }

    @Override
    public int hashCode() {
        // https://stackoverflow.com/questions/2943347/combining-java-hashcodes-into-a-master-hashcode
        return 1597 * (_0.hashCode()) ^ 1453 * (_1.hashCode());
    }

    @Override
    public String toString() {
        return "(" + _0.toString() + ", " + _1.toString() + ")";
    }
}
