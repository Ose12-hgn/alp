package Interface;

public interface Queueable {
    public void enqueue(Object e);
    public Object dequeue();
    public Object peek();
    public boolean isEmpty();
}
