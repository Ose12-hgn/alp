package Model;

import java.util.LinkedList;
import Interface.Queueable;

public class Queue implements Queueable{
    private LinkedList list = new LinkedList();

    @Override
    public void enqueue(Object e) {
        list.addLast(e);
    }

    @Override
    public Object dequeue() {
        if (!list.isEmpty()) {
            return list.removeFirst();
        } else {
            return null;
        }
    }

    @Override
    public Object peek() {
        if (!list.isEmpty()) {
            return list.getFirst();
        } else {
            return null;
        }
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
