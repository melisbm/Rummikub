package Cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Table implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Meld> melds = new ArrayList<>();

    public Table(){ }

    public void addMeld(Meld meld){
        melds.add(meld);
    }

    public void replaceMeld(int index, Meld meld){
        melds.set(index, meld);
    }

    public List<Meld> getMelds(){
        return melds;
    }

    public boolean isEmpty(){
        return melds.isEmpty();
    }

    public String toString(){

        if ( melds.isEmpty() ) return "Table is empty.";

        StringBuilder sb = new StringBuilder("Table:\n");

        for (int i = 0; i < melds.size(); i++){
            sb.append(" " + i + ": " + melds.get(i).toString() + "\n");
        }

        return sb.toString();
    }
}