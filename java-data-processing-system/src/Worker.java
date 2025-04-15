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

                // Simulate processing delay (computational work)
                Thread.sleep(500);

                // Create result and write it to the shared output file
                String result = "Worker " + workerID + " completed task: " + taskID + " with data: " + taskData;
                ResultWriter.writeResult(result);
                System.out.println("Worker " + workerID + " finished task: " + taskID + " with data: " + taskData);
            } catch (InterruptedException e) {
                System.err.println("Worker " + workerID + " was interrupted: " + e.getMessage());
                break;
            } catch (Exception e) {
                System.err.println("Worker " + workerID + " encountered error: " + e.getMessage());
            }
        }
        System.out.println("Worker " + workerID + " terminated.");
    }
}
