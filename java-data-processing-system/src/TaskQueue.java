import java.util.LinkedList;
import java.util.Queue;

// Shared task queue with appropriate synchronization
public class TaskQueue {
    private final Queue<Task> queue = new LinkedList<>();

    public synchronized void addTask(Task task) {
        queue.add(task);
        notify(); // Notify waiting threads that a new task is available.
    }

    // Get task from the queue. Throw error if there is thread interruption
    public synchronized Task getTask() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // Wait if the queue is empty.
        }
        return queue.poll();
    }
}
