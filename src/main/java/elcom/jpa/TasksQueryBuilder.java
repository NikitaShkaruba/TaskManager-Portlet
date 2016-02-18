package elcom.jpa;

import elcom.entities.*;

import java.util.*;

public class TasksQueryBuilder {
    public class TasksQuery {
        private long id;
        private String description;
        private Contact organisation;
        private Date startDate;
        private Date modificationDate;
        private Date finishDate;
        private Group executorGroup;
        private Employee creator;
        private Employee executor;
        private Priority priority;
        private Status status;
        private Task parentTask;
        private TaskType type;

        private TasksQuery(){}

        long getId() {
            return id;
        }

        String getDescription() {
            return description;
        }
        Contact getOrganisation() {
            return organisation;
        }
        Date getStartDate() {
            return startDate;
        }
        Date getModificationDate() {
            return modificationDate;
        }
        Date getFinishDate() {
            return finishDate;
        }
        Group getExecutorGroup() {
            return executorGroup;
        }
        Employee getCreator() {
            return creator;
        }
        Employee getExecutor() {
            return executor;
        }
        Priority getPriority() {
            return priority;
        }
        Status getStatus() {
            return status;
        }
        Task getParentTask() {
            return parentTask;
        }
        TaskType getType() {
            return type;
        }

        public void setCreator(Employee creator) {
            if (creator != null)
                this.creator = creator;
        }
    }

    private TasksQuery query;

    public TasksQueryBuilder() {
        query = new TasksQuery();
    }

    public TasksQuery getQuery() {
        return query;
    }

    public TasksQueryBuilder setId(long id) {
        query.id = id;

        return this;
    }
    public TasksQueryBuilder setDescription(String description) {
        if (description != null)
            query.description = description;

        return this;
    }
    public TasksQueryBuilder setOrganisation(Contact organisation) {
        if (organisation != null)
            query.organisation = organisation;

        return this;
    }
    public TasksQueryBuilder setStartDate(Date startDate) {
        if (startDate != null)
            query.startDate = startDate;

        return this;
    }
    public TasksQueryBuilder setModificationDate(Date modificationDate) {
        if (modificationDate != null)
            query.modificationDate = modificationDate;

        return this;
    }
    public TasksQueryBuilder setFinishDate(Date finishDate) {
        if (finishDate != null)
            query.finishDate = finishDate;

        return this;
    }
    public TasksQueryBuilder setExecutorGroup(Group executorGroup) {
        if (executorGroup != null)
            query.executorGroup = executorGroup;

        return this;
    }
    public TasksQueryBuilder setCreator(Employee creator) {
        query.setCreator(creator);

        return this;
    }
    public TasksQueryBuilder setExecutor(Employee executor) {
        if (executor != null)
            query.executor = executor;

        return this;
    }
    public TasksQueryBuilder setPriority(Priority priority) {
        if (priority != null)
            query.priority = priority;

        return this;
    }
    public TasksQueryBuilder setStatus(Status status) {
        if (status != null)
            query.status = status;

        return this;
    }
    public TasksQueryBuilder setParentTask(Task parentTask) {
        if (parentTask != null)
            query.parentTask = parentTask;

        return this;
    }
    public TasksQueryBuilder setType(TaskType type) {
        if (type != null)
            query.type = type;

        return this;
    }
}
