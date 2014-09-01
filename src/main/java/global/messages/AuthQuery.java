package global.messages;

import global.DataBaseManager;

/**
 * Created by Евгений on 28.08.2014.
 */
public class AuthQuery extends AbstractMsg {
    private final String login;
    private final String password;

    public AuthQuery(String login, String password){
        super();
        this.login = login;
        this.password = password;
    }

    @Override
    public void exec(Runnable abonent){
        if(abonent instanceof DataBaseManager){
            DataBaseManager dbman = (DataBaseManager)abonent;
            dbman.checkAuth(this.login, this.password);
        }
        else{
            System.out.println("AuthQuery exception during execution");
        }
    }
}
