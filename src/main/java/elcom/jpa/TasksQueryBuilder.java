package elcom.jpa;

import elcom.entities.*;
import java.util.*;

// Composes Query for tasks
public class TasksQueryBuilder {
    public class TasksQuery {
        private long id;
        private String description;
        private Organisation organisation;
        private ContactPerson contactPerson;
        private Date creationDate;
        private Date startDate;
        private Date modificationDate;
        private Date finishDate;
        private Group executorGroup;
        private Employee creator;
        private Employee executor;
        private Priority priority;
        private Status status;
        private TaskType type;

        private TasksQuery(){}

        long getId() {
            return id;
        }

        String getDescription() {
            return description;
        }
        Organisation getOrganisation() {
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
        TaskType getType() {
            return type;
        }
        ContactPerson getContactPerson() {
            return contactPerson;
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
            query.description = '%'+ description.toLowerCase() + '%';
        else
            query.description = null;

        return this;
    }
    public TasksQueryBuilder setOrganisation(Organisation organisation) {
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
    public TasksQueryBuilder setType(TaskType type) {
        query.type = type;

        return this;
    }
    public TasksQueryBuilder setContactPerson(ContactPerson contactPerson) {
        query.contactPerson = contactPerson;

        return this;
    }
}
