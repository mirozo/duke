package tasks;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Boolean getStatus() {
        return this.isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public void doTask() {
        this.isDone = true;
    }

    public String getDescription() {
        return this.description;
    }

    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }


}
