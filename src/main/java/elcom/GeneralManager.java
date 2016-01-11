package elcom;

import javax.ejb.Singleton;

/**
 * Created by Nikita Shkaruba on 11.01.16.
 * <p>
 * My Contacts:
 * Email: sh.nickita@list.ru
 * GitHub: github.com/SigmaOne
 * Vk: vk.com/wavemeaside
 */

// TODO: 11.01.16 Read about this annotation deeply
// TODO: 11.01.16 Add class logic
@Singleton
public class GeneralManager {
    ChoiceManager choiceManager = new ChoiceManager();
    ListManager listManager = new ListManager();
    TipManager tipManager = new TipManager();

    public GeneralManager() {

    }

}
