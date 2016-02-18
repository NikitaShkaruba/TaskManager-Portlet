package elcom.jpa;

import elcom.entities.*;

import java.util.*;

public class TasksQueryBuilder {
    public class TasksQuery {
        private long id;
        private String description;
        private Contact organisation;
        private Date creationDate;
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
        private Boolean visible;

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
        Date getCreationDate() {
            return creationDate;
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
        Boolean getVisible() {
            return visible;
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
        query.description = description;

        return this;
    }
    public TasksQueryBuilder setOrganisation(Contact organisation) {
        query.organisation = organisation;

        return this;
    }
    public TasksQueryBuilder setCreationDate(Date creationDate) {
        query.creationDate = creationDate;

        return this;
    }
    public TasksQueryBuilder setStartDate(Date startDate) {
        query.startDate = startDate;

        return this;
    }
    public TasksQueryBuilder setModificationDate(Date modificationDate) {
        query.modificationDate = modificationDate;

        return this;
    }
    public TasksQueryBuilder setFinishDate(Date finishDate) {
        query.finishDate = finishDate;

        return this;
    }
    public TasksQueryBuilder setExecutorGroup(Group executorGroup) {
        query.executorGroup = executorGroup;

        return this;
    }
    public TasksQueryBuilder setCreator(Employee creator) {
        query.creator = creator;

        return this;
    }
    public TasksQueryBuilder setExecutor(Employee executor) {
        query.executor = executor;

        return this;
    }
    public TasksQueryBuilder setPriority(Priority priority) {
        query.priority = priority;

        return this;
    }
    public TasksQueryBuilder setStatus(Status status) {
        query.status = status;

        return this;
    }
    public TasksQueryBuilder setParentTask(Task parentTask) {
        query.parentTask = parentTask;

        return this;
    }
    public TasksQueryBuilder setType(TaskType type) {
        query.type = type;

        return this;
    }
    public TasksQueryBuilder setVisible(Boolean visible) {
        query.visible = visible;

        return this;
    }
}
