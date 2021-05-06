package lab1;

/**
 * Created by Danil Lyskin at 21:24 01.04.2021
 */
public class Pair <T, E> {

    T first;
    E second;

    public Pair(T first, E second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public E getSecond() {
        return second;
    }
}
