package global;

/**
 * Created by eugene on 10/19/14.
 */
public interface GameMechanics {
    public void addUser(String user);

    public void incrementScore(String userName);

    public void run() throws InterruptedException;
}
