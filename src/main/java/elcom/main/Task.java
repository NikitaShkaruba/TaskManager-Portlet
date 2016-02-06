package elcom.main;

import java.util.Date;

// TODO: 12.01.16 Make this bean an entity and map it to database
// TODO: 16.01.16 Make use of startDate, finishDate

public class Task {
    private int id;
    private String description;
    private String status;
    private String group;
    private String executor;
    private String priority;
    private Date startDate;
    private Date finishDate;

    public Task(int id, String description, String executor, String status, String priority) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.executor = executor;
        this.priority = priority;

        // TODO: 05.02.16 Work it out
        this.group = "None";
        this.startDate = new Date();
        this.startDate = new Date();
    }
    public Task(int id, String description, String status, String group, String executor, String priority, Date startDate, Date finishDate) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.group = group;
        this.executor = executor;
        this.priority = priority;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public String getStatus() {
        return status;
    }
    public String getGroup() {
        return group;
    }
    public String getExecutor() {
        return executor;
    }
    public String getPriority() {
        return priority;
    }
    public Date getStartDate() {
        return this.startDate;
    }
    public Date getFinishDate() {
        return this.finishDate;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public void setExecutor(String executorName) {
        this.executor = executorName;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public void setStartDate(Date date) {
        this.startDate = date;
    }
    public void setFinishDate(Date date) {
        this.finishDate = date;
    }

    public boolean equals(Task task) {
        // TODO: 04.02.16 Learn about standard equals realization :) Maybe it was implemented like i do
        return this.description.equals(task.description) && this.executor.equals(task.executor) && this.status.equals(task.status) &&
            this.priority.equals(task.priority) && this.startDate.equals(task.startDate) && this.finishDate.equals(task.finishDate);
    }
}
