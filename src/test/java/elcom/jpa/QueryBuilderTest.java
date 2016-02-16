package elcom.jpa;

import elcom.entities.Employee;
import org.junit.Test;

import java.awt.*;
import java.util.Map;

import static org.junit.Assert.*;

public class QueryBuilderTest {

    //NamedQuery = queryString (String) + parameters (Dictionary)

    @Test
    public void BasicQueryTest() {
        assertEquals("select from String", new QueryBuilder(String.class).getQuery().getQueryString());
        assertEquals("select from Employee", new QueryBuilder(Employee.class).getQuery().getQueryString());
    }

    @Test
    public void BasicParametersTest() {
        QueryBuilder qb1 = new QueryBuilder(Employee.class);
        QueryBuilder qb2 = new QueryBuilder(Employee.class);
        QueryBuilder qb3 = new QueryBuilder(Employee.class);

        qb1.addParameter("name", "John Smith");
        assertEquals("select from Employee with name", qb1.getQuery().getQueryString());

        qb2.addParameter("name", null);
        assertEquals("select from Employee with name", qb2.getQuery().getQueryString());

        qb3.addParameter("id", Color.BLACK);
        assertEquals("select from Employee with id", qb3.getQuery().getQueryString());
    }

    @Test
    public void IntermediateParametersTest() {
        QueryBuilder qb = new QueryBuilder(Employee.class);
        Map<String, Object> qbParams;

        qb.addParameter("name", "Rylai Crestfall");
        qbParams = qb.getQuery().getParams();
        assertTrue(qbParams.size() == 1);
        assertTrue(qbParams.get("name").equals("Rylai Crestfall"));

        qb.addParameter("name", "Robert Paulson");
        qbParams = qb.getQuery().getParams();
        assertTrue(qbParams.size() == 1);
        assertTrue(qbParams.get("name").equals("Robert Paulson"));

        qb.addParameter("id", null);
        qbParams = qb.getQuery().getParams();
        assertTrue(qbParams.size() == 2);
        assertTrue(qbParams.get("id") == null);
    }

    @Test
    public void AdvancedParametersTest() {
        QueryBuilder qb1 = new QueryBuilder(Employee.class);
        QueryBuilder qb2 = new QueryBuilder(Employee.class);

        qb1.addParameter("name", "Artour Babaev");
        qb1.addParameter("phone", "12131415");
        assertEquals("select from Employee with name with phone", qb1.getQuery().getQueryString());

        qb2.addParameter("phone", "12131415");
        qb2.addParameter("name", "Artour Babaev");
        assertEquals("select from Employee with name with phone", qb2.getQuery().getQueryString());

    }
}