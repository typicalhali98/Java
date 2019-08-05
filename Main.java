package Hazi;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
     //   ChampionsLeaguejegy cl1 = new ChampionsLeaguejegy("Real Madrid - Ajax",59,"Madrid","21:00");
     //   ChampionsLeaguejegy cl2 = new ChampionsLeaguejegy("Bayern München - Barcelona",87,"München","21:00");
      //  ChampionsLeaguejegy cl3 = new ChampionsLeaguejegy("Sevilla - Mol Videoton",34,"Sevilla","21:00");
     //   ChampionsLeaguejegy cl4 = new ChampionsLeaguejegy("Manchester United - PSG",59,"Manchester","21:00");

     //   Bundesligajegy bu1 = new Bundesligajegy("Schalke04 - Hannover96",34,"Gelsenkirchen","15:00");
     //   Bundesligajegy bu2 = new Bundesligajegy("SC Freiburg - Bayern München",49,"Freiburg","18:00");
    //    Bundesligajegy bu3 = new Bundesligajegy("Borussia Mönchengladbach - Borussia Dortmund",72,"Mönchengladbach","20:45");
     //   Bundesligajegy bu4 = new Bundesligajegy("Rasenballsport Leipzig - Hertha BSC",34,"Leipzig","20:45");
        Gyujto gyujtoCL = olvas("championsleague.txt");
       // gyujtoCL.hozzaAdas(cl1);
     //   gyujtoCL.hozzaAdas(cl2);
      //  gyujtoCL.hozzaAdas(cl3);
     //   gyujtoCL.hozzaAdas(cl4);

        Gyujto bundes = olvas("bundesliga.txt");
     //   bundes.hozzaAdas(bu1);
     //   bundes.hozzaAdas(bu2);
     //   bundes.hozzaAdas(bu3);
      //  bundes.hozzaAdas(bu4);
        new Ablak(gyujtoCL,bundes);
    }






    public static Gyujto olvas(String file){
            Gyujto gyujtoke=new Gyujto();
        try {
            FileReader fileReader = new FileReader(new File(file));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");
                /*
                itt kell létrehoznod a jegyeket és a gyűjtőbe pakolgatni
                gyujtoCL.hozzaAdas(new ChampionsLeaguejegy(data[0],data[1],data[2],data[3]));
                 */
                if (file.equals("champions league.txt") ){
                    gyujtoke.hozzaAdas(new ChampionsLeaguejegy(data[0],Integer.parseInt(data[1]),data[2],data[3]));
                }else{
                    gyujtoke.hozzaAdas(new Bundesligajegy(data[0],Integer.parseInt(data[1]),data[2],data[3]));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return gyujtoke;
    }
}
