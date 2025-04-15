// Task class used to set and get data
public class Task {
    private final int taskID;
    private final String data;

    public Task(int id, String data) {
        this.taskID = id;
        this.data = data;
    }

    public int getTaskID() {
        return taskID;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Task {ID = " + taskID +
                "Data = " + data + "}";
    }
}
