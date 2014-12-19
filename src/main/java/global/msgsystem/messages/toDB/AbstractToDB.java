package global.msgsystem.messages.toDB;

import global.Abonent;
import global.AddressService;
import global.DataBaseManager;
import global.database.DataBaseManagerImpl;
import global.msgsystem.messages.AbstractMsg;

/**
 * Created by max on 22.11.14.
 */
public abstract class AbstractToDB extends AbstractMsg {

    public AbstractToDB(String addressFrom) {
        super(addressFrom, AddressService.getDBManAddr());
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof DataBaseManagerImpl) {
            DataBaseManager dbman = (DataBaseManager) abonent;
            exec(dbman);
        }
        else {
            System.out.println("Exception during AbstractToDB.exec");
        }
    }

    public abstract void exec(DataBaseManager dbman);
}
