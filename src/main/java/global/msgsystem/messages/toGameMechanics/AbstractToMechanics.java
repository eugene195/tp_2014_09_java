package global.msgsystem.messages.toGameMechanics;

import global.Abonent;
import global.AddressService;
import global.GameMechanics;
import global.msgsystem.messages.AbstractMsg;

/**
 * Created by eugene on 11/22/14.
 */
public abstract class AbstractToMechanics extends AbstractMsg {
    public AbstractToMechanics(String addressFrom) {
        super(addressFrom, AddressService.getMechanic());
    }

    public abstract void exec(GameMechanics mechanics);

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof GameMechanics) {
            GameMechanics mechanics = (GameMechanics) abonent;
            exec(mechanics);
        }
        else {
            System.out.println("Exception during AbstractToMechanics.exec");
        }
    }
}
