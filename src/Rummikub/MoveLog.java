package Rummikub;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MoveLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> moves = new ArrayList<>();

    public void register(String move){
        moves.add(move);
    }

    public List<String> getMoves(){
        return moves;
    }

    public String toString(){

        StringBuilder sb = new StringBuilder("Move log:\n");

        String logLineTemplate = " %d. %s\n";

        for (int i = 0; i < moves.size(); i++){

            String logLine = String.format(logLineTemplate, (i + 1), moves.get(i));

            sb.append(logLine);
        }

        return sb.toString();
    }
}