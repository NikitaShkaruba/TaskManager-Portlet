package elcom.jpa;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

public class DatabaseConnector {
    private static final String PERSISTENCE_UNIT_NAME = "MainPersistenceUnit";
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private EntityManager em;

    public DatabaseConnector(){}

    public void persist(Object o) {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(em.merge(o));

        em.getTransaction().commit();
        em.close();
    }
    public void delete(Object o) {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        em.remove(em.merge(o));

        em.getTransaction().commit();
        em.close();
    }

    public <T> T find(Class<T> type, long id) {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        T result = em.find(type, id);
        em.getTransaction().commit();
        em.close();
        return result;
    }

    public <T> List<T> getQueryResult(QueryBuilder.NamedQuery query) {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createNamedQuery(query.getQueryString());
        Map<String, Object> parameters = query.getParams();

        if (!parameters.isEmpty())
            for (Map.Entry<String, Object> e : parameters.entrySet())
                q.setParameter(e.getKey(), e.getValue());

        List<T> result = q.getResultList();

        em.getTransaction().commit();
        em.close();

        return result;

    }
}
