// Worker class using Thread execution
public class Worker extends Thread {
    private final TaskQueue taskQueue;
    private final int workerId;

    public Worker(TaskQueue taskQueue, int workerId) {
        this.taskQueue = taskQueue;
        this.workerId = workerId;
    }

    @Override
    public void run() {
        System.out.println("Worker " + workerId + " started.");
        while (true) {
            try {
                Task task = taskQueue.getTask();
                // Check for termination signal (null task)
                if (task == null) {
                    System.out.println("Worker " + workerId + " received termination signal.");
                    break;
                }
                // Process the task
                String taskData = task.getData();
                System.out.println("Worker " + workerId + " processing task: " + taskData);

                // Simulate processing delay (computational work)
                Thread.sleep(500);

                // Create result and write it to the shared output file
                String result = "Worker " + workerId + " completed task: " + taskData;
                ResultWriter.writeResult(result);
                System.out.println("Worker " + workerId + " finished task: " + taskData);
            } catch (InterruptedException e) {
                System.err.println("Worker " + workerId + " was interrupted: " + e.getMessage());
                break;
            } catch (Exception e) {
                System.err.println("Worker " + workerId + " encountered error: " + e.getMessage());
            }
        }
        System.out.println("Worker " + workerId + " terminated.");
    }
}
