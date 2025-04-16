import java.util.concurrent.ThreadLocalRandom;

// Worker class using Thread execution
public class Worker extends Thread {
    private final TaskQueue taskQueue;
    private final int workerID;

    public Worker(TaskQueue taskQueue, int workerID) {
        this.taskQueue = taskQueue;
        this.workerID = workerID;
    }

    @Override
    public void run() {
        System.out.println("Worker " + workerID + " started.");
        while (true) {
            try {
                Task task = taskQueue.getTask();
                // Check for termination signal (null task)
                if (task == null) {
                    System.out.println("Worker " + workerID + " received termination signal.");
                    break;
                }

                // Process the task
                int taskID = task.getTaskID();
                String taskData = task.getData();
                System.out.println("Worker " + workerID + " processing task: " + taskID + " with data: " + taskData);

                // Simulate processing delay (some kind of computational work)
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1000));

                // Create result and write it to the shared output file
                String result = "Worker " + workerID + " completed task: " + taskID + " with data: " + taskData;
                ResultWriter.writeResult(result);
                System.out.println("Worker " + workerID + " finished task: " + taskID + " with data: " + taskData);
            } catch (InterruptedException e) {
                String result = "Worker " + workerID + " was interrupted: " + e.getMessage();
                ResultWriter.writeResult(result);
                System.err.println(result);
                break;
            } catch (Exception e) {
                String result = "Worker " + workerID + " encountered error: " + e.getMessage();
                ResultWriter.writeResult(result);
                System.err.println(result);
            }
        }
        System.out.println("--- Worker " + workerID + " terminated. ---");
    }
}
