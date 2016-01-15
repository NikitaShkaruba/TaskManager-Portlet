package elcom;

import jdk.internal.util.xml.impl.Pair;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.*;

/**2
 * Created by Nikita Shkaruba on 11.01.16.
 * <p>
 * My Contacts:
 * Email: sh.nickita@list.ru
 * GitHub: github.com/SigmaOne
 * Vk: vk.com/wavemeaside
 */

// TODO: 12.01.16 Rethink about general manager value, maybe 3 managers could do the job without him :\
// TODO: 12.01.16 Find a way to return List instead of map in "*Variants" methods
// TODO: 11.01.16 Read about this annotation deeply
// TODO: 11.01.16 Add class logic
@ManagedBean(name = "GeneralManager")
@SessionScoped
public class GeneralManager {
    ChoiceManager choiceManager = new ChoiceManager();
    ListManager listManager = new ListManager();
    TipManager tipManager = new TipManager();

    public GeneralManager() {

    }

    // Todo: 12.01.16 Enable popup-messages
    public String getTip() {
        String tip = tipManager.getTip();

     /* switch(tip) {
            default: FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", tip)); break;
        } */

        return tip;
    }

    public String getTaskFilter() {
        return choiceManager.getTaskStatus().name();
    }
    public String getEmployeeFilter() {
        return choiceManager.getEmployeeFilter().name();
    }
    public String getItemAmount() {
        return choiceManager.getTasksShowed() + "";
    }
    public String getCurrentPage() {
        return choiceManager.getCurrentPage() + "";
    }
    public String getMaxPage() {
        return "10";
    }

    // TODO: 12.01.16 Refactor awkward Map initialization
    public Map<String, String> getItemAmountVariants() {
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put("15", "15");
        temp.put("30", "30");
        temp.put("45", "45");

        return temp;
    }
    public Map<String, String> getPageVariants() {
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put("1", "1");
        temp.put("5", "5");
        temp.put("10", "10");

        return temp;
    }


    public List<Task> getTasks() {
        return listManager.getTasks();
    }
}
