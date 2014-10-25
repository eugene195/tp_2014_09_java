package global.resources;

/**
 * Created by max on 25.10.14.
 */
public class Admin implements Resource {
    private String fullname;
    private int respect;

    public String getFullName() {
        return this.fullname;
    }

    public int getRespect() {
        return this.respect;
    }
}
