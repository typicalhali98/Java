package Hazi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public class Ablak extends JFrame {
    private Gyujto gyujto;
    private Gyujto gyujto2;
    private JTable vasarolt;
    private JTextField vegosszeg;
    private HashMap<Focijegy, Integer> kosar;
    private JTextField nevMezo;
    private JTextField iranyitoszamMezo;
    private JTextField varosMezo;
    private JTextField cimMezo;

    public Ablak(Gyujto gyujto, Gyujto gyujto2) {
        super("Focijegy Mánia applikáció");
        this.gyujto = gyujto;
        this.gyujto2 = gyujto2;
        kosar = new HashMap<>();
        init();
    }

    private void init() {
        //méretek
        setBounds(100, 100, 800, 800);
        //ablak elrendezés menedzser
        setLayout(new BorderLayout());
        JPanel elso = new JPanel();
        elso.setLayout(new BoxLayout(elso, BoxLayout.PAGE_AXIS));
        add(elso);

        elso.add(new Label("Üdvözöljük a Focijegy Mániák applikációban!", FlowLayout.CENTER));
        elso.add(new Label("Mérközések", FlowLayout.CENTER));

        JPanel masodik = new JPanel();
        elso.add(masodik);
        masodik.setLayout(new BorderLayout());
        masodik.add(new Label("Bajnokok ligája mérközések", FlowLayout.LEFT), BorderLayout.NORTH);

        String[] mezonevek = {"Mérkőzés", "Jegyár", "Helyszín", "Időpont"};
        makeTable(mezonevek, masodik, gyujto);

        JPanel harmadik = new JPanel();
        elso.add(harmadik);
        harmadik.setLayout(new BorderLayout());
        harmadik.add(new Label("Bundes liga mérközések", FlowLayout.LEFT), BorderLayout.NORTH);

        makeTable(mezonevek, harmadik, gyujto2);

        JPanel negyedik = new JPanel();
        elso.add(negyedik);
        negyedik.setLayout(new BorderLayout());
        negyedik.add(new JLabel("Kosár"), BorderLayout.NORTH);

        vasarolt = new JTable(new DefaultTableModel(null, new String[]{"Mérközés", "Db", "Ár"}));
        addScrollPane(vasarolt, negyedik);

        JButton button3 = new JButton("Töröl");
        negyedik.add(button3, BorderLayout.SOUTH);
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                torlesKezelo();
            }
        });

        JPanel otodik = new JPanel();
        elso.add(otodik);
        otodik.setLayout(new GridLayout(5, 2));
        otodik.add(new JLabel("Név:"));
        otodik.add(nevMezo = new JTextField());
        otodik.add(new JLabel("Irányítószám:"));
        otodik.add(iranyitoszamMezo = new JTextField());
        otodik.add(new JLabel("Város:"));
        otodik.add(varosMezo = new JTextField());
        otodik.add(new JLabel("Cím:"));
        otodik.add(cimMezo = new JTextField());
        JPanel hatodik = new JPanel();
        otodik.add(hatodik);
        hatodik.setLayout(new GridLayout(1, 2));
        hatodik.add(new JLabel("Fizetendő:"));
        vegosszeg = new JTextField();
        vegosszeg.setEditable(false);
        hatodik.add(vegosszeg);
        JButton button4 = new JButton("Vásárol");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vasarlas();
            }
        });
        otodik.add(button4);

        addWindowListener(new AblakBezaro());
        setVisible(true);
    }

    private void makeTable(String[] mezonevek, JPanel panel, Gyujto gyujto) {
        Object[][] adat = {};
        DefaultTableModel model = new DefaultTableModel(adat, mezonevek);
        JTable table = new JTable(model);
        for (int i = 0; i < gyujto.size(); i++) {
            model.insertRow(table.getRowCount(), new Object[]
                    {
                            gyujto.getFocijegy(i).getMerkozes(), gyujto.getFocijegy(i).getJegyAr(), gyujto.getFocijegy(
                            i).getHelyszin(), gyujto.getFocijegy(i).getIdopont()
                    });
        }
        addScrollPane(table, panel);

        JButton button = new JButton("Kosárba rak");
        panel.add(button, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hozzaadasKezelo(gyujto.getFocijegy(table.getSelectedRow()));
            }
        });
    }

    private void addScrollPane(JTable table, JPanel panel) {
        JScrollPane sp = new JScrollPane(table);
        panel.add(sp, BorderLayout.CENTER);
        table.getColumnModel().getColumn(0).setPreferredWidth(250);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table.setSelectionBackground(Color.RED);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void hozzaadasKezelo(Focijegy jegy) {
        try {
            String db = JOptionPane.showInputDialog(null, "Hány jegyet szeretnél?");
            int darab = Integer.parseInt(db);
            if (darab <= 0) {
                throw new NumberFormatException();
            }

            if (kosar.containsKey(jegy)) {
                kosar.put(jegy, kosar.get(jegy) + darab);
            } else {
                kosar.put(jegy, darab);
            }
            DefaultTableModel model = (DefaultTableModel) vasarolt.getModel();
            model.insertRow(vasarolt.getRowCount(), new Object[]{
                    jegy.getMerkozes(), db, darab * jegy.getJegyAr()
            });
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Kiléptél");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "A vásárlás nem sikerült!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "A vásárlás nem sikerült! Válasz ki egy mérkőzést!"
            );
        }
    }

    private void torlesKezelo() {
        if (vasarolt.getSelectedRow() < 0) return;
        DefaultTableModel model = (DefaultTableModel) vasarolt.getModel();
        Vector row = (Vector) model.getDataVector().get(vasarolt.getSelectedRow());
        String jegyNeve = (String) row.get(0);
        int darab = (Integer) row.get(2);
        for (Focijegy jegy : kosar.keySet()) {
            if (!jegy.getMerkozes().equals(jegyNeve)) continue;
            if (kosar.get(jegy) == darab) {
                kosar.remove(jegy);
            } else {
                kosar.put(jegy, kosar.get(jegy) - darab);
            }
            break;
        }
        model.removeRow(vasarolt.getSelectedRow());
    }

    private void vasarlas() {
        if (nevMezo.getText().isEmpty() || iranyitoszamMezo.getText().isEmpty() ||
                cimMezo.getText().isEmpty() || varosMezo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Minden mező kitöltése kötelező");
        } else {
            try {
                if (Integer.parseInt(iranyitoszamMezo.getText()) <= 0 ) {
                    throw new IllegalArgumentException();
                }
            } catch (NumberFormatException ex2) {
                JOptionPane.showMessageDialog(null, "Nem jó forma!");

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Helytelen adat!");
            } catch (Exception idk) {
                return;
            }

            DefaultTableModel model = (DefaultTableModel) vasarolt.getModel();
            long total = 0;
            for (Object row : model.getDataVector()) {
                total += (Integer) ((Vector) row).get(2);
            }
            vegosszeg.setText(total == 0 ? "" : total + " €");
            iras2();
        }
    }

        class AblakBezaro extends WindowAdapter {
            @Override
            public void windowClosing(WindowEvent e) {
                iras(gyujto,"championsleague.txt");
                iras(gyujto2,"bundesliga.txt");
                System.exit(0);
            }
        }
        public  void iras(Gyujto lista, String file){
        try {
            FileWriter fileWriter = new FileWriter(new File(file));
            //végig mész a gyűjtőn és kiíros soronként;
            for (int i = 0; i <lista.size(); i++) {
                Focijegy mc = lista.getFocijegy(i);
                fileWriter.write( mc.getMerkozes() + ";" + mc.getJegyAr() + ";" + mc.getHelyszin() + ";" + mc.getIdopont() + '\n');
                //kiüritse amit éppen ir a flush (ilyesmi :D )
                fileWriter.flush();
            }

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void iras2(){
        try {
            FileWriter fileWriter = new FileWriter(new File(nevMezo.getText()+".txt"));
            //végig mész a gyűjtőn és kiíros soronként;
            fileWriter.write(nevMezo.getText()+"\n");
            fileWriter.write(iranyitoszamMezo.getText()+" ");
            fileWriter.write(varosMezo.getText()+" ");
            fileWriter.write(cimMezo.getText()+"\n");
            fileWriter.flush();
            for (Focijegy mc : kosar.keySet()) {
                fileWriter.write(mc.toString()+" " + kosar.get(mc)+'\n');
                fileWriter.flush();
            }
            fileWriter.write(vegosszeg.getText());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

