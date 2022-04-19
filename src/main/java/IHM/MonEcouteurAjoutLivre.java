package IHM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonEcouteurAjoutLivre implements ActionListener {
    private MonPanneau UnPanneau;

    //constucteur de la class MonEcouteurAjoutLivre
    public MonEcouteurAjoutLivre(MonPanneau unPanneau) {

        UnPanneau = unPanneau;
    }

    //lors du clique sur le bouton on affiche
    public void actionPerformed(ActionEvent e) {
        System.out.println("Vous avez ajouter un livre");

    }

}
