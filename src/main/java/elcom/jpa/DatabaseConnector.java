package elcom.jpa;

import elcom.entities.Task;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class DatabaseConnector {
    private static final String PERSISTENCE_UNIT_NAME = "MainPersistenceUnit";
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private EntityManager em;

    public DatabaseConnector(){}

    private List<Task> filterTasks(List<Task> tasks, TasksQueryBuilder.TasksQuery query) {
        if (query.getId() != 0)
            tasks.removeIf(new Predicate<Task>() {
                @Override
                public boolean test(Task task) {
                    return task.getId() != query.getId();
                }
            });

        if (query.getDescription() != null)
            tasks.removeIf(new Predicate<Task>() {
                @Override
                public boolean test(Task task) {
                    boolean descriptionExists = task.getDescription() != null;
                    boolean blockedByDescriptionFilter = !descriptionExists || !task.getDescription().equals(query.getDescription());

                    return blockedByDescriptionFilter;
                }
            });

        if (query.getCreator() != null)
            tasks.removeIf(new Predicate<Task>() {
                @Override
                public boolean test(Task task) {
                    boolean creatorExists = task.getCreator() != null;
                    boolean blockedByCreatorFilter = !creatorExists || !task.getCreator().equals(query.getCreator());

                    return blockedByCreatorFilter;
                }
            });

        if (query.getExecutor() != null)
            tasks.removeIf(new Predicate<Task>() {
                @Override
                public boolean test(Task task) {
                    boolean executorExists = task.getExecutor() != null;
                    boolean blockedByExecutorFilter = !executorExists || !task.getExecutor().equals(query.getExecutor());

                    return blockedByExecutorFilter;
                }
            });

        if (query.getExecutorGroup() != null)
            tasks.removeIf(new Predicate<Task>() {
                @Override
                public boolean test(Task task) {
                    boolean groupExists = task.getExecutorGroup() != null;
                    boolean blockedByGroupFilter = !groupExists || !task.getExecutorGroup().equals(query.getExecutorGroup());

                    return blockedByGroupFilter;
                }
            });

        if (query.getFinishDate() != null)
            tasks.removeIf(new Predicate<Task>() {
                @Override
                public boolean test(Task task) {
                    boolean finishDateExists = task.getFinishDate() != null;
                    boolean blockedByFindateFilter = !finishDateExists || !task.getFinishDate().equals(query.getFinishDate());

                    return blockedByFindateFilter;
                }
            });

        if (query.getModificationDate() != null)
            tasks.removeIf(new Predicate<Task>() {
                @Override
                public boolean test(Task task) {
                    boolean modifDateExists = task.getModificationDate() != null;
                    boolean blockedByModdateFilter = !modifDateExists || !task.getModificationDate().equals(query.getModificationDate());

                    return blockedByModdateFilter;
                }
            });

        if (query.getOrganisation() != null)
            tasks.removeIf(new Predicate<Task>() {
                @Override
                public boolean test(Task task) {
                    boolean organisationsExists = task.getOrganisation() != null;
                    boolean blockedByOrganisationFilter = !organisationsExists || !task.getOrganisation().equals(query.getOrganisation());

                    return blockedByOrganisationFilter;
                }
            });

        if (query.getParentTask() != null)
            tasks.removeIf(new Predicate<Task>() {
                @Override
                public boolean test(Task task) {
                    boolean parentExists = task.getParentTask() != null;
                    boolean blockedByParentFilter = !parentExists || !task.getParentTask().equals(query.getParentTask());

                    return blockedByParentFilter;
                }
            });

        if (query.getPriority() != null)
            tasks.removeIf(new Predicate<Task>() {
                @Override
                public boolean test(Task task) {
                    boolean priorityExists = task.getPriority() != null;
                    boolean blockedByPriorityFilter = !priorityExists || !task.getPriority().equals(query.getPriority());

                    return blockedByPriorityFilter;
                }
            });

        if (query.getStartDate() != null)
            tasks.removeIf(new Predicate<Task>() {
                @Override
                public boolean test(Task task) {
                    boolean startDateExists = task.getStartDate() != null;
                    boolean blockedByStartdateFilter = !startDateExists || !task.getStartDate().equals(query.getStartDate());

                    return blockedByStartdateFilter;
                }
            });

        if (query.getStatus() != null)
            tasks.removeIf(new Predicate<Task>() {
                @Override
                public boolean test(Task task) {
                    boolean statusExists = task.getStatus() != null;
                    boolean blockedByStatusFilter = !statusExists || !task.getStatus().equals(query.getStatus());

                    return blockedByStatusFilter;
                }
            });

        if (query.getType() != null)
            tasks.removeIf(new Predicate<Task>() {
                @Override
                public boolean test(Task task) {
                    boolean typeExists = task.getType() != null;
                    boolean blockedByTypeFilter = !typeExists || !task.getType().equals(query.getType());

                    return blockedByTypeFilter;
                }
            });

        return tasks;
    }

    public void persist(Object o) {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(em.merge(o));

        em.getTransaction().commit();
        em.close();
    }

    public List getNamedQueryResult(String query) {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        List result = em.createNamedQuery(query).getResultList();

        em.getTransaction().commit();
        em.close();

        return result;
    }

    public <T> T findById(Class<T> type, long id) {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        T result = em.find(type, id);

        em.getTransaction().commit();
        em.close();

        return result;
    }
    public List<Task> getTasksQueryResult(TasksQueryBuilder.TasksQuery query) {
        List<Task> tasks = getNamedQueryResult("select from Task");

        tasks = filterTasks(tasks, query);

        return tasks;

    }
}
