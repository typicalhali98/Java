package Hazi;

public class Focijegy {
    private String merkozes;
    private int jegyAr;
    private String helyszin;
    private String idopont;

    public Focijegy(String merkozes,int jegyAr, String helyszin, String idopont){
        this.merkozes = merkozes;
        this.jegyAr = jegyAr;
        this.helyszin = helyszin;
        this.idopont = idopont;
    }

    public String getMerkozes() {
        return merkozes;
    }

    public void setMerkozes(String merkozes) {
        this.merkozes = merkozes;
    }

    public int getJegyAr() {
        return jegyAr;
    }

    public void setJegyAr(int jegyAr) {
        this.jegyAr = jegyAr;
    }

    public String getHelyszin() {
        return helyszin;
    }

    public void setHelyszin(String helyszin) {
        this.helyszin = helyszin;
    }

    public String getIdopont() {
        return idopont;
    }

    public void setIdopont(String idopont) {
        this.idopont = idopont;
    }
    public String toString(){
        return getMerkozes()+" " + getJegyAr()+ " " + getHelyszin() + " " + getIdopont();
    }
}
