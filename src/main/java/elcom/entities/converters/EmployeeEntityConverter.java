package elcom.entities.converters;

import elcom.ejbs.DataProvider;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@ManagedBean
@RequestScoped
public class EmployeeEntityConverter implements Converter, defaultFilterable {
    private String noFilterOption = "-- Все --";
    @EJB
    DataProvider dp;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return (s != null && !isNoFilterOption(s))? dp.getEmployeeEntityByName(s) : null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return (o != null)? o.toString() : null;
    }

    @Override
    public boolean isNoFilterOption(String stringFilter) {
         return stringFilter.equals(noFilterOption);
    }
}
