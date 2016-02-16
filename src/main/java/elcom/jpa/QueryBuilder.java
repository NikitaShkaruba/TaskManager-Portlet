package elcom.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class QueryBuilder {
    class NamedQuery {
        private String queryString;
        private Map<String, Object> params = new HashMap<>();

        private NamedQuery(){}

        public String getQueryString() {
            return queryString;
        }
        public Map<String, Object> getParams() {
            if (params == null)
                params = new HashMap<>();

            return params;
        }
    }

    private NamedQuery query;
    private SortedSet<String> filters =  new TreeSet<>();

    public QueryBuilder(Class type) {
        this(type.getSimpleName());
    }
    public QueryBuilder(String typeName) {
        query = new NamedQuery();
        query.queryString = "select from " + typeName;
    }

    public QueryBuilder addParameter(String fieldName, Object value) {
        filters.add(fieldName.toLowerCase());
        query.params.put(fieldName, value);

        return this;
    }
    public NamedQuery getQuery() {
        for (String filter : filters)
            query.queryString = query.queryString.concat(" with ").concat(filter);

        return query;
    }
}
