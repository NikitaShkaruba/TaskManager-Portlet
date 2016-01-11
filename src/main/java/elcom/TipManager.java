package elcom;

import javax.faces.bean.ManagedBean;

/**
 * Created by Nikita Shkaruba on 11.01.16.
 *
 * My Contacts:
 * Email: sh.nickita@list.ru
 * GitHub: github.com/SigmaOne
 * Vk: vk.com/wavemeaside
 */

// TODO: 11.01.16 Add class logic
@ManagedBean(name = "TipManager")
public class TipManager {
    public TipManager() {

    }

    public String getTip() {
        return "Ваш запрос был успешно выполнен.";
    }
}
