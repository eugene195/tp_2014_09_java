package global.messages;

import global.DataBaseManager;

/**
 * Created by Евгений on 28.08.2014.
 */
public class AuthMsg extends AbstractMsg {
    private String login;
    private String password;

    public AuthMsg(String login, String password){
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
            System.out.println("AuthMessage exception during execution");
        }
    }
}
