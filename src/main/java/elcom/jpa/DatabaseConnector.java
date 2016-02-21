package elcom.jpa;

import elcom.entities.Task;

import javax.persistence.*;
import javax.persistence.Query;
import java.util.*;

public class DatabaseConnector {
    private static final String PERSISTENCE_UNIT_NAME = "MainPersistenceUnit";
    private static final char CLASS_FIELD_QUERY_DELIMITER = '_';
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private EntityManager em;

    public DatabaseConnector(){}

    private Set<Map.Entry<String, Object>> parseTaskQueryFilters(TasksQueryBuilder.TasksQuery query) {
        Map<String, Object> filters = new HashMap<>();

        //filters.put("id", query.getId()); Id filtering is turned off
        filters.put("description", query.getDescription());
        filters.put("organisation", query.getOrganisation());
        filters.put("contactPerson", query.getContactPerson());
        filters.put("creationDate", query.getCreationDate());
        filters.put("startDate", query.getStartDate());
        filters.put("modificationDate", query.getModificationDate());
        filters.put("finishDate", query.getFinishDate());
        filters.put("executorGroup", query.getExecutorGroup());
        filters.put("wfCreator.employee", query.getCreator());
        filters.put("wfExecutor.employee", query.getExecutor());
        filters.put("priority", query.getPriority());
        filters.put("status", query.getStatus());
        filters.put("parentTask", query.getParentTask());
        filters.put("type", query.getType());
        filters.put("visible", query.getVisible());
        filters.put("privateTask", query.getPrivateTask());

        Set<Map.Entry<String, Object>> result = filters.entrySet();

        Iterator<Map.Entry<String, Object>> entryPointer = result.iterator();
        while (entryPointer.hasNext()) {
            if (entryPointer.next().getValue() == null)
                entryPointer.remove();
        }

        return result;
    }
    private StringBuilder changeDescFilterFromEqualsToContains(StringBuilder sb) {
        int index = sb.indexOf("t.description = :description");
        if (index > -1)
            sb.replace(index, index+28, "lower(t.description) like :description");

        return sb;
    }
    private String composeQueryString(Set<Map.Entry<String, Object>> filters) {
        StringBuilder qs = new StringBuilder("select t from Task t");
        //If there are no filters, just return plain 'select all';
        if (filters.isEmpty())
            return qs.toString();
        // First, Convert Map to List
        List<Map.Entry<String, Object>> filterList = new ArrayList();
        for (Map.Entry<String, Object> e : filters)
            filterList.add(e);
        //First filter is always applied with 'where' clause
        qs.append(" where t.").append(filterList.get(0).getKey()).append(" = :").append(filterList.get(0).getKey().replace('.',CLASS_FIELD_QUERY_DELIMITER));
        //All subsequent filters append prev. filter with 'and' clause
        for (byte i = 1; i < filterList.size(); i += 1)
            qs.append(" and t.").append(filterList.get(i).getKey()).append(" = :").append(filterList.get(i).getKey().replace('.',CLASS_FIELD_QUERY_DELIMITER));

        qs = changeDescFilterFromEqualsToContains(qs);

        return qs.toString();
    }

    public void persist(Object o) {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(em.merge(o));

        em.getTransaction().commit();
        em.close();
    }

    public List getQueryResult(String query) {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        List result = em.createQuery(query).getResultList();

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
        Set<Map.Entry<String, Object>> filters = parseTaskQueryFilters(query);
        String queryString = composeQueryString(filters);

        em = emf.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createQuery(queryString);

        for (Map.Entry<String, Object> e : filters)
            q.setParameter(e.getKey().replace('.',CLASS_FIELD_QUERY_DELIMITER), e.getValue());

        List<Task> result = q.getResultList();

        em.getTransaction().commit();
        em.close();

        return result;
    }
}
