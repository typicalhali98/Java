package Hazi;

import java.util.ArrayList;

public class Gyujto {
    private ArrayList<Focijegy> focijegyek;
    public Gyujto(){
        this.focijegyek = new ArrayList<>();
    }

    public void hozzaAdas(Focijegy focijegy){
        focijegyek.add(focijegy);
    }

    public int size(){
        return focijegyek.size();
    }

    public Focijegy getFocijegy(int i){
        return focijegyek.get(i);
    }
}
