package IHM;

import bdd.gestionTable;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MaFenetre extends Frame {
    private MonPanneau monPanneau;

    public MaFenetre() throws HeadlessException, IOException {


        monPanneau=new MonPanneau();
        setLayout(new FlowLayout());
        add(monPanneau);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Au revoir !!");
                System.exit(1);
            }
        });
        pack();
    }
}
