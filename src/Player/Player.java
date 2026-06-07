package Player;
import java.io.Serializable;
import java.lang.*;

public abstract class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String name;
    protected int points = 0;

    public String getName() {
        return name;
    }
}