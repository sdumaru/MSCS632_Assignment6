/**
 * Main class to set up and run the data processing system using raw Threads.
 */

public class Main {
    public static void main(String[] args) {
        // Create the shared task queue
        TaskQueue taskQueue = new TaskQueue();
        int numberOfWorkers = 5;
        int numberOfTasks = 10;

        // Start worker threads
        Worker[] workers = new Worker[numberOfWorkers];
        for (int i = 0; i < numberOfWorkers; i++) {
            workers[i] = new Worker(taskQueue, i + 1);
            workers[i].start(); // Start the thread execution
        }

        // Add tasks to the queue
        for (int i = 0; i < numberOfTasks; i++) {
            Task task = new Task(i + 1, "Information " + (i + 1));
            taskQueue.addTask(task);
            System.out.println("Added task: " + task.getTaskID() + " with data: " + task.getData());
        }

        // Send termination signals to workers (adding 'null' as a signal)
        for (int i = 0; i < numberOfWorkers; i++) {
            taskQueue.addTask(null);
        }

        // Wait for all worker threads to complete
        System.out.println("Waiting for workers to finish...");
        for (Worker worker : workers) {
            try {
                worker.join(); // Wait for this thread to die
            } catch (InterruptedException e) {
                System.err.println("Main thread interrupted while waiting for workers: " + e.getMessage());
            }
        }

        System.out.println("All tasks processed. Program terminating.");
    }
}
