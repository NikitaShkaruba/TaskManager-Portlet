package elcom.jpa;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class QueryBuilder {
    public class NamedQuery {
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

    public QueryBuilder addParameter(String name, Object value) {
        filters.add(name.toLowerCase());
        query.params.put(name, value);

        return this;
    }
    public NamedQuery getQuery() {
        for (String filter : filters)
            query.queryString = query.queryString.concat(" with ").concat(filter);

        return query;
    }
}
