package elcom.entities.converters;

import elcom.ejbs.DataProvider;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("EmployeeEntityConverter")
public class EmployeeEntityConverter implements Converter {
    @EJB
    DataProvider dp;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return (s != null)? dp.getEmployeeEntityByName(s) : null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return (o != null)? o.toString() : null;
    }
}
